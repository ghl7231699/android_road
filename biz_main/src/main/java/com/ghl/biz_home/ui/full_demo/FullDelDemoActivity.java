package com.ghl.biz_home.ui.full_demo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ghl.biz_home.R;
import com.ghl.router_annotation.Route;

import java.util.ArrayList;
import java.util.List;

import static com.ghl.lib_dirty.constants.main.HomeConstantsKt.ACTIVITY_FULL_DEL_DEMO;

/**
 * 介绍：完整的删除Demo
 * 作者：zhangxutong
 * 邮箱：zhangxutong@imcoming.com
 * 时间： 2016/9/12.
 */
@Route(ACTIVITY_FULL_DEL_DEMO)
public class FullDelDemoActivity extends Activity {
    private RecyclerView mRv;
    private FullDelDemoAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private List<SwipeBean> mDatas;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_del_demo);
        mRv = findViewById(R.id.rv);

        initDatas();
        mAdapter = new FullDelDemoAdapter(this, mDatas);
        mAdapter.setOnDelListener(new FullDelDemoAdapter.onSwipeListener() {
            @Override
            public void onDel(int pos) {
                if (pos >= 0 && pos < mDatas.size()) {
                    Toast.makeText(FullDelDemoActivity.this, "删除:" + pos, Toast.LENGTH_SHORT).show();
                    mDatas.remove(pos);
                    mAdapter.notifyItemRemoved(pos);//推荐用这个
                    //如果删除时，不使用mAdapter.notifyItemRemoved(pos)，则删除没有动画效果，
                    //且如果想让侧滑菜单同时关闭，需要同时调用 ((SwipeMenuLayout) holder.itemView).quickClose();
                    //mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onTop(int pos) {
                if (pos > 0 && pos < mDatas.size()) {
                    SwipeBean swipeBean = mDatas.get(pos);
                    mDatas.remove(swipeBean);
                    mAdapter.notifyItemInserted(0);
                    mDatas.add(0, swipeBean);
                    mAdapter.notifyItemRemoved(pos + 1);
                    if (mLayoutManager.findFirstVisibleItemPosition() == 0) {
                        mRv.scrollToPosition(0);
                    }
                    //notifyItemRangeChanged(0,holder.getAdapterPosition()+1);
                }
            }
        });
        mRv.setAdapter(mAdapter);
//        mRv.setLayoutManager(mLayoutManager = new GridLayoutManager(this, 2));
        mRv.setLayoutManager(mLayoutManager = new LinearLayoutManager(this));

        // 可以用在：当点击外部空白处时，关闭正在展开的侧滑菜单。我个人觉得意义不大，
        mRv.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                SwipeMenuLayout viewCache = SwipeMenuLayout.Companion.getViewCache();
                if (null != viewCache) {
                    viewCache.smoothClose();
                }
            }
            return false;
        });
    }

    private void initDatas() {
        mDatas = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            mDatas.add(new SwipeBean("" + i));
        }
    }
}
