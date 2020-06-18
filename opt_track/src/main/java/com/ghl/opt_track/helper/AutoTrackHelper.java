package com.ghl.opt_track.helper;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.Choreographer;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.ghl.lib_dirty.util.ReflectUtil;
import com.ghl.opt_track.core.AutoTrackUtil;
import com.ghl.opt_track.performance.FPSFrameCallBack;
import com.ghl.opt_track.timer.TimeExecutor;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONObject;

import java.util.Locale;

/**
 * 类描述：埋点的入口类
 * 创建人：ghl
 * 创建时间：2019/4/2
 *
 * @version v1.0
 */
public class AutoTrackHelper {

    private static SparseArray<Long> eventTimestamp = new SparseArray<>();

    /**
     * 防抖动，超过一定时间才算数
     */
    private static boolean isDeBounceTrack(Object object) {

        boolean isDeBounceTrack = false;
        long currentOnClickTimestamp = System.currentTimeMillis();
        Object targetObject = eventTimestamp.get(object.hashCode());
        if (targetObject != null) {
            long lastOnClickTimestamp = (long) targetObject;
            if ((currentOnClickTimestamp - lastOnClickTimestamp) < 500) {
                isDeBounceTrack = true;
            }
        }

        eventTimestamp.put(object.hashCode(), currentOnClickTimestamp);
        return isDeBounceTrack;
    }

    /**
     * 对应的埋点方法{@link Fragment#onViewCreated(View, Bundle)}
     *
     * @param object   Fragment对象
     * @param rootView 对应View对象
     * @param bundle   对应Bundle对象
     */
    public static void onFragmentViewCreated(Object object, View rootView, Bundle bundle) {
        try {
            System.out.println("测试:onFragmentViewCreated");
            if (!(object instanceof Fragment)) {
                return;
            }

            Fragment fragment = (Fragment) object;
            //Fragment名称
            String fragmentName = fragment.getClass().getName();

            if (rootView instanceof ViewGroup) {
                AutoTrackUtil.traverseViewForFragment(fragmentName, (ViewGroup) rootView);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 对应的埋点方法{@link Fragment#onResume()}
     *
     * @param object Fragment对象
     */
    public static void trackFragmentResume(Object object) {
        try {
            if (!(object instanceof Fragment)) {
                return;
            }
            Fragment fragment = (Fragment) object;
            Fragment parentFragment = fragment.getParentFragment();
            if (parentFragment == null) {
                if (!fragment.isHidden() && fragment.getUserVisibleHint()) {
                    trackFragmentAppViewScreen(fragment);
                }
            } else {
                if (!fragment.isHidden() && fragment.getUserVisibleHint() && !parentFragment.isHidden() && parentFragment.getUserVisibleHint()) {
                    trackFragmentAppViewScreen(fragment);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 对应的埋点方法{@link Fragment#setUserVisibleHint(boolean)}
     *
     * @param object          Fragment对象
     * @param isVisibleToUser 对应boolean值
     */
    public static void trackFragmentSetUserVisibleHint(Object object, boolean isVisibleToUser) {
        System.out.println("测试:trackFragmentSetUserVisibleHint");

        if (!(object instanceof Fragment)) {
            return;
        }

        Fragment fragment = (Fragment) object;

        Fragment parentFragment = fragment.getParentFragment();
        if (parentFragment == null) {
            if (isVisibleToUser) {
                if (fragment.isResumed()) {
                    if (!fragment.isHidden()) {
                        trackFragmentAppViewScreen(fragment);
                    }
                }
            }
        } else {
            if (isVisibleToUser && parentFragment.getUserVisibleHint()) {
                if (fragment.isResumed() && parentFragment.isResumed()) {
                    if (!fragment.isHidden() && !parentFragment.isHidden()) {
                        trackFragmentAppViewScreen(fragment);
                    }
                }
            }
        }
    }

    /**
     * 对应的埋点方法{@link Fragment#onHiddenChanged(boolean)}
     *
     * @param object Fragment对象
     * @param hidden 对应boolean值
     */
    public static void trackOnHiddenChanged(Object object, boolean hidden) {

        if (!(object instanceof Fragment)) {
            return;
        }

        Fragment fragment = (Fragment) object;
        Fragment parentFragment = fragment.getParentFragment();
        if (parentFragment == null) {
            if (!hidden) {
                if (fragment.isResumed()) {
                    if (fragment.getUserVisibleHint()) {
                        trackFragmentAppViewScreen(fragment);
                    }
                }
            }
        } else {
            if (!hidden && !parentFragment.isHidden()) {
                if (fragment.isResumed() && parentFragment.isResumed()) {
                    if (fragment.getUserVisibleHint() && parentFragment.getUserVisibleHint()) {
                        trackFragmentAppViewScreen(fragment);
                    }
                }
            }
        }
    }


    /**
     * Fragment日志处理
     */
    private static void trackFragmentAppViewScreen(Fragment fragment) {
        try {

            String fragmentName = fragment.getClass().getCanonicalName();
            Activity activity = fragment.getActivity();

            JSONObject properties = new JSONObject();

            if (activity != null) {
                String activityTitle = AutoTrackUtil.getActivityTitle(activity);
                if (!TextUtils.isEmpty(activityTitle)) {
                    properties.put(LogConstants.Autotrack.EVENT_SCAN_PAGE_TITLE, activityTitle);
                }
                String screenName = String.format(Locale.CHINA, "%s|%s", activity.getClass().getCanonicalName(), fragmentName);
                properties.put(LogConstants.Autotrack.SCREEN_NAME, screenName);
            } else {
                properties.put(LogConstants.Autotrack.SCREEN_NAME, fragmentName);
            }
            System.out.println("自动埋点:trackFragmentAppViewScreen:" + properties.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 对应实现接口的埋点方法{@link ExpandableListView.OnGroupClickListener#onGroupClick(ExpandableListView, View, int, long)}
     *
     * @param expandableListView 参数对应ExpandableListView
     * @param view               参数对应View
     * @param groupPosition      参数对应int
     */
    public static void trackExpandableListViewOnGroupClick(ExpandableListView expandableListView, View view,
                                                           int groupPosition) {
        try {
            //获取Activity
            Activity activity = AutoTrackUtil.getActivityFromView(expandableListView);

            JSONObject properties = new JSONObject();

//            // 1、获取当前点击控件的全路径
//            String viewPath = AutoTrackUtil.getViewPath(view);
//            if (!TextUtils.isEmpty(viewPath)) {
//                properties.put(LogConstants.Autotrack.ELEMENT_VIEWPATH, viewPath);
//            }

            // 2、获取Activity的标题名
            if (activity != null) {
                String activityTitle = AutoTrackUtil.getActivityTitle(activity);
                if (!TextUtils.isEmpty(activityTitle)) {
                    properties.put(LogConstants.Autotrack.EVENT_SCAN_PAGE_TITLE, activityTitle);
                }
            }
            // 3、获取当前页面
            String screen_name = AutoTrackUtil.getScreenNameFromView(activity, expandableListView);
            if (!TextUtils.isEmpty(screen_name)) {
                properties.put(LogConstants.Autotrack.SCREEN_NAME, screen_name);
            }

            // 4、获取ExpandableListView的控件名:ViewId
            String idString = AutoTrackUtil.getViewId(expandableListView);
            if (!TextUtils.isEmpty(idString)) {
                properties.put(LogConstants.Autotrack.ELEMENT_ID, idString);
            }

            // 5、获取当前点击控件的索引位置
            properties.put(LogConstants.Autotrack.ELEMENT_POSITION, String.format(Locale.CHINA, "%d", groupPosition));
            properties.put(LogConstants.Autotrack.ELEMENT_TYPE, "ExpandableListView");

            // 6、获取当前控件内容
            try {
                String viewText;
                if (view instanceof ViewGroup) {
                    StringBuilder stringBuilder = new StringBuilder();
                    viewText = AutoTrackUtil.traverseView(stringBuilder, (ViewGroup) view);
                    if (!TextUtils.isEmpty(viewText)) {
                        viewText = viewText.substring(0, viewText.length() - 1);
                        properties.put(LogConstants.Autotrack.ELEMENT_CONTENT, viewText);
                    }
                } else {
                    CharSequence viewTextOnly = AutoTrackUtil.traverseViewOnly(view);
                    if (!TextUtils.isEmpty(viewTextOnly)) {
                        properties.put(LogConstants.Autotrack.ELEMENT_CONTENT, viewTextOnly.toString());
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }


            System.out.println("自动埋点:OnGroupClick:" + properties.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 对应实现接口的埋点方法{@link ExpandableListView.OnChildClickListener#onChildClick(ExpandableListView, View, int, int, long)}
     *
     * @param expandableListView 参数对应ExpandableListView
     * @param view               参数对应View
     * @param groupPosition      参数对应groupPosition
     * @param childPosition      参数对应childPosition
     */
    public static void trackExpandableListViewOnChildClick(ExpandableListView expandableListView, View view,
                                                           int groupPosition, int childPosition) {
        try {

            //获取Activity
            Activity activity = AutoTrackUtil.getActivityFromView(expandableListView);

            JSONObject properties = new JSONObject();

            // 1、获取当前点击控件的全路径
//            String viewPath = AutoTrackUtil.getViewPath(view);
//            if (!TextUtils.isEmpty(viewPath)) {
//                properties.put(LogConstants.Autotrack.ELEMENT_VIEWPATH, viewPath);
//            }

            // 2、获取Activity的标题名
            if (activity != null) {
                String activityTitle = AutoTrackUtil.getActivityTitle(activity);
                if (!TextUtils.isEmpty(activityTitle)) {
                    properties.put(LogConstants.Autotrack.EVENT_SCAN_PAGE_TITLE, activityTitle);
                }
            }
            // 3、获取当前页面
            String screen_name = AutoTrackUtil.getScreenNameFromView(activity, expandableListView);
            if (!TextUtils.isEmpty(screen_name)) {
                properties.put(LogConstants.Autotrack.SCREEN_NAME, screen_name);
            }

            // 4、获取ExpandableListView的控件名:ViewId
            String idString = AutoTrackUtil.getViewId(expandableListView);
            if (!TextUtils.isEmpty(idString)) {
                properties.put(LogConstants.Autotrack.ELEMENT_ID, idString);
            }

            // 5、获取当前点击控件的索引位置
            properties.put(LogConstants.Autotrack.ELEMENT_POSITION, String.format(Locale.CHINA, "%d:%d", groupPosition, childPosition));

            // 6、控件的类型
            properties.put(LogConstants.Autotrack.ELEMENT_TYPE, "ExpandableListView");

            // 7、获取当前控件内容
            try {
                String viewText;
                if (view instanceof ViewGroup) {
                    StringBuilder stringBuilder = new StringBuilder();
                    viewText = AutoTrackUtil.traverseView(stringBuilder, (ViewGroup) view);
                    if (!TextUtils.isEmpty(viewText)) {
                        viewText = viewText.substring(0, viewText.length() - 1);
                        properties.put(LogConstants.Autotrack.ELEMENT_CONTENT, viewText);
                    }
                } else {
                    CharSequence viewTextOnly = AutoTrackUtil.traverseViewOnly(view);
                    if (!TextUtils.isEmpty(viewTextOnly)) {
                        properties.put(LogConstants.Autotrack.ELEMENT_CONTENT, viewTextOnly.toString());
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }


            System.out.println("自动埋点:OnChildClick:" + properties.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 对应实现接口的埋点方法{@link AdapterView.OnItemSelectedListener#onItemSelected(AdapterView, View, int, long)}
     * 和{@link AdapterView.OnItemClickListener#onItemClick(AdapterView, View, int, long)}
     *
     * @param adapterView 参数对应AdapterView
     * @param view        参数对应View
     * @param position    参数对应int
     */
    public static void trackListView(AdapterView<?> adapterView, View view, int position) {
        try {
            //获取Activity
            Activity activity = AutoTrackUtil.getActivityFromView(view);

            JSONObject properties = new JSONObject();

            // 1、获取当前点击控件的全路径
//            String viewPath = AutoTrackUtil.getViewPath(view);
//            if (!TextUtils.isEmpty(viewPath)) {
//                properties.put(LogConstants.Autotrack.ELEMENT_VIEWPATH, viewPath);
//            }

            // 2、获取Activity的标题名
            if (activity != null) {
                String activityTitle = AutoTrackUtil.getActivityTitle(activity);
                if (!TextUtils.isEmpty(activityTitle)) {
                    properties.put(LogConstants.Autotrack.EVENT_SCAN_PAGE_TITLE, activityTitle);
                }
            }

            // 3、获取当前页面
            String screen_name = AutoTrackUtil.getScreenNameFromView(activity, adapterView);
            if (!TextUtils.isEmpty(screen_name)) {
                properties.put(LogConstants.Autotrack.SCREEN_NAME, screen_name);
            }

            // 4、获取ExpandableListView的控件名:ViewId
            String idString = AutoTrackUtil.getViewId(adapterView);
            if (!TextUtils.isEmpty(idString)) {
                properties.put(LogConstants.Autotrack.ELEMENT_ID, idString);
            }

            // 5、获取当前点击控件的索引位置
            properties.put(LogConstants.Autotrack.ELEMENT_POSITION, String.valueOf(position));

            // 6、控件的类型
            properties.put(LogConstants.Autotrack.ELEMENT_TYPE, adapterView.getClass().getSimpleName());


            // 7、获取当前控件内容
            try {
                String viewText;
                if (view instanceof ViewGroup) {
                    StringBuilder stringBuilder = new StringBuilder();
                    viewText = AutoTrackUtil.traverseView(stringBuilder, (ViewGroup) view);
                    if (!TextUtils.isEmpty(viewText)) {
                        viewText = viewText.substring(0, viewText.length() - 1);
                        properties.put(LogConstants.Autotrack.ELEMENT_CONTENT, viewText);
                    }
                } else {
                    CharSequence viewTextOnly = AutoTrackUtil.traverseViewOnly(view);
                    if (!TextUtils.isEmpty(viewTextOnly)) {
                        properties.put(LogConstants.Autotrack.ELEMENT_CONTENT, viewTextOnly.toString());
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            System.out.println("自动埋点:onItemClick:" + properties.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 对应实现接口的埋点方法{@link android.widget.TabHost.OnTabChangeListener#onTabChanged(String)}
     *
     * @param tabName 参数对应String
     */
    public static void trackTabHost(String tabName) {
        try {
            JSONObject properties = new JSONObject();

            properties.put(LogConstants.Autotrack.ELEMENT_CONTENT, tabName);
            properties.put(LogConstants.Autotrack.ELEMENT_TYPE, "TabHost");

            System.out.println("自动埋点:onTabChanged:" + properties.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 对应实现接口的埋点方法{@link TabLayout.OnTabSelectedListener#onTabSelected(TabLayout.Tab)}
     *
     * @param object this对象
     * @param tab    TabLayout.Tab对象
     */
    public static void trackTabLayoutSelected(Object object, Object tab) {
        try {
            if (isDeBounceTrack(tab)) {
                return;
            }

            JSONObject properties = new JSONObject();
            Context context = null;

            if (!(tab instanceof TabLayout.Tab)) {
                return;
            }

            // 1、获取当前控件内容
            TabLayout.Tab tabObject = (TabLayout.Tab) tab;
            Object text = tabObject.getText();
            if (text != null) {
                properties.put(LogConstants.Autotrack.ELEMENT_CONTENT, text);
            } else {
                // 获取自定义view文本内容
//                Object customViewObject = ReflectUtil.getMethodValue(tab, "getCustomView");
                View customView = tabObject.getCustomView();
                if (customView != null) {
                    try {
                        String viewText;
                        if (customView instanceof ViewGroup) {
                            StringBuilder stringBuilder = new StringBuilder();
                            viewText = AutoTrackUtil.traverseView(stringBuilder, (ViewGroup) customView);
                            if (!TextUtils.isEmpty(viewText)) {
                                viewText = viewText.substring(0, viewText.length() - 1);
                                properties.put(LogConstants.Autotrack.ELEMENT_CONTENT, viewText);
                            }
                        } else {
                            CharSequence viewTextOnly = AutoTrackUtil.traverseViewOnly(customView);
                            if (!TextUtils.isEmpty(viewTextOnly)) {
                                properties.put(LogConstants.Autotrack.ELEMENT_CONTENT, viewTextOnly.toString());
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if (!(object instanceof Context)) {
                        context = customView.getContext();
                    }
                }


            }

            // 2、获取Activity
            if (object instanceof Context) {
                context = (Context) object;
            } else {
                try {
                    // 反射获取TabLayout.Tab的mParent对象
                    Object mParent = ReflectUtil.getFieldValue(tab, "mParent");
                    TabLayout tabLayout = (TabLayout) mParent;
                    context = tabLayout.getContext();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            Activity activity = AutoTrackUtil.getActivityFromContext(context);

            // 3、获取当前页面信息，不一定获取得到
            if (activity != null) {
                properties.put(LogConstants.Autotrack.SCREEN_NAME, activity.getClass().getCanonicalName());
                String activityTitle = AutoTrackUtil.getActivityTitle(activity);
                if (!TextUtils.isEmpty(activityTitle)) {
                    properties.put(LogConstants.Autotrack.EVENT_SCAN_PAGE_TITLE, activityTitle);
                }
            }

            // 4、控件的类型
            properties.put(LogConstants.Autotrack.ELEMENT_TYPE, "TabLayout");

            System.out.println("自动埋点:onTabSelected:" + properties.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 对应实现接口的埋点方法{@link NavigationView.OnNavigationItemSelectedListener#onNavigationItemSelected(MenuItem)}
     *
     * @param object   this对象
     * @param menuItem 参数对应MenuItem
     */
    public static void trackMenuItem(Object object, MenuItem menuItem) {
        try {

            if (isDeBounceTrack(menuItem)) {
                return;
            }

            // 获取Activity
            Context context = null;
            if (object instanceof Context) {
                context = (Context) object;
            }
            Activity activity = AutoTrackUtil.getActivityFromContext(context);


            JSONObject properties = new JSONObject();


            if (activity != null) {
                // 1、获取当前页面
                properties.put(LogConstants.Autotrack.SCREEN_NAME, activity.getClass().getCanonicalName());
                String activityTitle = AutoTrackUtil.getActivityTitle(activity);
                if (!TextUtils.isEmpty(activityTitle)) {
                    // 2、获取Activity的标题名
                    properties.put(LogConstants.Autotrack.EVENT_SCAN_PAGE_TITLE, activityTitle);
                }
            }

            // 3、获取MenuItem的控件名
            try {
                if (context != null) {
                    String idString = context.getResources().getResourceEntryName(menuItem.getItemId());
                    if (!TextUtils.isEmpty(idString)) {
                        properties.put(LogConstants.Autotrack.ELEMENT_ID, idString);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


            // 4、获取当前控件内容
            if (!TextUtils.isEmpty(menuItem.getTitle())) {
                properties.put(LogConstants.Autotrack.ELEMENT_CONTENT, menuItem.getTitle());
            }

            // 5、控件的类型
            properties.put(LogConstants.Autotrack.ELEMENT_TYPE, "MenuItem");

            System.out.println("自动埋点:onNavigationItemSelected:" + properties.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 对应实现接口的埋点方法{@link RadioGroup.OnCheckedChangeListener#onCheckedChanged(RadioGroup, int)}
     *
     * @param view      RadioGroup对象
     * @param checkedId 参数对应int
     */
    public static void trackRadioGroup(RadioGroup view, int checkedId) {
        try {

            //获取Activity
            Activity activity = AutoTrackUtil.getActivityFromView(view);

            JSONObject properties = new JSONObject();

            // 1、获取Activity的标题名
            if (activity != null) {
                String activityTitle = AutoTrackUtil.getActivityTitle(activity);
                if (!TextUtils.isEmpty(activityTitle)) {
                    properties.put(LogConstants.Autotrack.EVENT_SCAN_PAGE_TITLE, activityTitle);
                }
            }

            // 2、获取当前页面
            String screen_name = AutoTrackUtil.getScreenNameFromView(activity, view);
            if (!TextUtils.isEmpty(screen_name)) {
                properties.put(LogConstants.Autotrack.SCREEN_NAME, screen_name);
            }

            // 3、获取RadioGroup的控件名
            String idString = AutoTrackUtil.getViewId(view);
            if (!TextUtils.isEmpty(idString)) {
                properties.put(LogConstants.Autotrack.ELEMENT_ID, idString);
            }

            // 4、控件的类型
            properties.put(LogConstants.Autotrack.ELEMENT_TYPE, "RadioButton");

            int checkedRadioButtonId = view.getCheckedRadioButtonId();
            if (activity != null) {
                try {
                    RadioButton radioButton = activity.findViewById(checkedRadioButtonId);
                    if (radioButton != null) {
                        if (!TextUtils.isEmpty(radioButton.getText())) {
                            String viewText = radioButton.getText().toString();
                            if (!TextUtils.isEmpty(viewText)) {
                                // 5、获取当前控件内容
                                properties.put(LogConstants.Autotrack.ELEMENT_CONTENT, viewText);
                            }
                        }
                        // 6、获取当前点击控件的全路径
                        String viewPath = AutoTrackUtil.getViewPath(radioButton);
                        if (!TextUtils.isEmpty(viewPath)) {
                            properties.put(LogConstants.Autotrack.ELEMENT_VIEWPATH, viewPath);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            System.out.println("自动埋点:onNavigationItemSelected:" + properties.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 对应实现接口的埋点方法{@link DialogInterface.OnClickListener#onClick(DialogInterface, int)}
     *
     * @param dialogInterface DialogInterface对象
     * @param whichButton     参数对应int
     */
    public static void trackDialog(DialogInterface dialogInterface, int whichButton) {
        try {
            //获取所在的Context
            Dialog dialog = null;
            if (dialogInterface instanceof Dialog) {
                dialog = (Dialog) dialogInterface;
            }

            if (dialog == null) {
                return;
            }

            if (isDeBounceTrack(dialog)) {
                return;
            }

            Context context = dialog.getContext();

            //将Context转成Activity
            Activity activity = AutoTrackUtil.getActivityFromContext(context);

            if (activity == null) {
                activity = dialog.getOwnerActivity();
            }

            JSONObject properties = new JSONObject();

            if (activity != null) {
                // 1、获取当前页面
                properties.put(LogConstants.Autotrack.SCREEN_NAME, activity.getClass().getCanonicalName());
                String activityTitle = AutoTrackUtil.getActivityTitle(activity);
                if (!TextUtils.isEmpty(activityTitle)) {
                    // 2、获取Activity的标题名
                    properties.put(LogConstants.Autotrack.EVENT_SCAN_PAGE_TITLE, activityTitle);
                }
            }

            properties.put(LogConstants.Autotrack.ELEMENT_TYPE, "Dialog");

            if (dialog instanceof android.app.AlertDialog) {
                android.app.AlertDialog alertDialog = (android.app.AlertDialog) dialog;
                Button button = alertDialog.getButton(whichButton);
                if (button != null) {
                    if (!TextUtils.isEmpty(button.getText())) {
                        properties.put(LogConstants.Autotrack.ELEMENT_CONTENT, button.getText());
                    }
                } else {
                    ListView listView = alertDialog.getListView();
                    if (listView != null) {
                        ListAdapter listAdapter = listView.getAdapter();
                        Object object = listAdapter.getItem(whichButton);
                        if (object != null) {
                            if (object instanceof String) {
                                properties.put(LogConstants.Autotrack.ELEMENT_CONTENT, object);
                            }
                        }
                    }
                }

            } else if (dialog instanceof androidx.appcompat.app.AlertDialog) {
                androidx.appcompat.app.AlertDialog alertDialog = (androidx.appcompat.app.AlertDialog) dialog;
                Button button = alertDialog.getButton(whichButton);
                if (button != null) {
                    if (!TextUtils.isEmpty(button.getText())) {
                        properties.put(LogConstants.Autotrack.ELEMENT_CONTENT, button.getText());
                    }
                } else {
                    ListView listView = alertDialog.getListView();
                    if (listView != null) {
                        ListAdapter listAdapter = listView.getAdapter();
                        Object object = listAdapter.getItem(whichButton);
                        if (object != null) {
                            if (object instanceof String) {
                                properties.put(LogConstants.Autotrack.ELEMENT_CONTENT, object);
                            }
                        }
                    }
                }
            }

            System.out.println("自动埋点:trackDialog:" + properties.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 监听 void onDrawerOpened(View)方法
     *
     * @param view 方法中的view参数
     */
    public static void trackDrawerOpened(View view) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(LogConstants.Autotrack.ELEMENT_CONTENT, "Open");
            trackViewOnClick(view);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 监听 void onDrawerClosed(View)方法—
     *
     * @param view 方法中的view参数
     */
    public static void trackDrawerClosed(View view) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(LogConstants.Autotrack.ELEMENT_CONTENT, "Close");
            trackViewOnClick(view);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 监听点击事件
     *
     * @param view view
     */
    public static void trackViewOnClick(View view) {
        try {
            //获取Activity
            Activity activity = AutoTrackUtil.getActivityFromView(view);

            JSONObject properties = new JSONObject();

//            // 1、获取当前点击控件的全路径
//            String viewPath = AutoTrackUtil.getViewPath(view);
//            if (!TextUtils.isEmpty(viewPath)) {
//                properties.put(LogConstants.Autotrack.ELEMENT_VIEWPATH, viewPath);
//            }

            // 2、获取Activity的标题名
            if (activity != null) {
                String activityTitle = AutoTrackUtil.getActivityTitle(activity);
                if (!TextUtils.isEmpty(activityTitle)) {
                    properties.put(LogConstants.Autotrack.EVENT_SCAN_PAGE_TITLE, activityTitle);
                }
            }
            // 3、获取当前页面
            String screen_name = AutoTrackUtil.getScreenNameFromView(activity, view);
            if (!TextUtils.isEmpty(screen_name)) {
                properties.put(LogConstants.Autotrack.SCREEN_NAME, screen_name);
            }

            // 4、获取ExpandableListView的控件名:ViewId
            String idString = AutoTrackUtil.getViewId(view);
            if (!TextUtils.isEmpty(idString)) {
                properties.put(LogConstants.Autotrack.ELEMENT_ID, idString);
            }

            // 6、控件的类型
            properties.put(LogConstants.Autotrack.ELEMENT_TYPE, view.getClass().getSimpleName());

            // 7、获取当前控件内容
            try {
                String viewText;
                if (view instanceof ViewGroup) {
                    StringBuilder stringBuilder = new StringBuilder();
                    viewText = AutoTrackUtil.traverseView(stringBuilder, (ViewGroup) view);
                    if (!TextUtils.isEmpty(viewText)) {
                        viewText = viewText.substring(0, viewText.length() - 1);
                        properties.put(LogConstants.Autotrack.ELEMENT_CONTENT, viewText);
                    }
                } else {
                    CharSequence viewTextOnly = AutoTrackUtil.traverseViewOnly(view);
                    if (!TextUtils.isEmpty(viewTextOnly)) {
                        properties.put(LogConstants.Autotrack.ELEMENT_CONTENT, viewTextOnly.toString());
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            System.out.println("自动埋点:trackViewOnClick:" + properties.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 方法开始执行
     *
     * @param method key
     */
    public static void startMethod(Object method) {
        TimeExecutor.setStartTime(method.getClass().getName(), System.currentTimeMillis());
    }

    /**
     * 方法执行结束
     *
     * @param method key
     */
    public static void endMethod(Object method) {
        TimeExecutor.setEndTime(method.getClass().getName(), System.currentTimeMillis());
        String costTime = TimeExecutor.getCostTime(method.getClass().getName());
        System.out.println(costTime);
    }

    /**
     * activity resume
     *
     * @param method key
     */
    public static void resume(Object method) {
        TimeExecutor.setResumeTime(method.getClass().getName(), System.currentTimeMillis());
    }

    /**
     * activity restart
     *
     * @param method key
     */
    public static void restart(Object method) {
        TimeExecutor.setRestartTime(method.getClass().getName(), System.currentTimeMillis());
    }

    /**
     * app启动
     *
     * @param obj key
     */
    public static void startApp(Object obj) {
        TimeExecutor.setAppTime(obj.getClass().getName(), System.currentTimeMillis());
    }

    /**
     * app冷启动完成
     *
     * @param obj key
     */
    public static void completeApp(Object obj, boolean b) {
        if (b) {
            try {
                TimeExecutor.setAppCompleteTime(obj.getClass().getName(), System.currentTimeMillis());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 帧率检测
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static void frameDetection() {
        Choreographer.getInstance().postFrameCallback(new FPSFrameCallBack(System.nanoTime()));
    }

    /**
     * 监听主线程Handler方式检测帧率
     */
    public static void frameDetection2() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        for (StackTraceElement e :
                stackTrace) {
            String className = e.getClassName();
            String methodName = e.getMethodName();
            String fileName = e.getFileName();
            int lineNumber = e.getLineNumber();
            System.out.println("fileName="
                    + fileName + "\n----------className=" + className + "\n----------methodName="
                    + methodName + "\n----------lineNumber=" + lineNumber);
        }
        Looper.getMainLooper().setMessageLogging(new FramePrinter());
    }
}
