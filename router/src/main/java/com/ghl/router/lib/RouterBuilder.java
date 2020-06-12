package com.ghl.router.lib;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 内部处理就应跳转数据的保存
 * 当前已实现的功能
 * 1 正常代替startActivity 与 startActivityForResult
 * 2 多个页面跳转
 */
public class RouterBuilder {

    /**
     * 上下文
     */
    private final Context mContext;

    /**
     * 目标页面
     */
    private Class<?> mTargetPage;

    /**
     * 携带数据
     *
     * @see Intent#putExtras(Bundle)
     */
    private Bundle mTargetData;

    /**
     * {@link Activity#startActivityForResult(Intent, int)} )}
     */
    private int mRequestCode;

    /**
     * 动画处理类
     * {@link ActivityCompat#startActivity(Context, Intent, Bundle)}
     */
    private Bundle mAnimBundle;

    /**
     * intent action
     */
    private String mAction;

    /**
     * @see #addFlags(int)
     */
    private ArrayList<Integer> mFlags;

    /**
     * @see #setFlags(int)
     */
    private Integer mSetFlag;

    /**
     * 之前的页面
     */
    private ArrayList<Intent> mBeforeIntent;


    RouterBuilder(Context context) {
        this.mContext = context;
    }

    /**
     * @param page 目标位置
     */
    public RouterBuilder target(String page) {
        this.mTargetPage = RouterUtils.getPageClass(page);
        return this;
    }

    public RouterBuilder anim(int enterAnim, int exitAnim) {
        mAnimBundle = ActivityOptionsCompat.makeCustomAnimation(mContext, enterAnim, exitAnim).toBundle();
        return this;
    }


    /**
     * {@link Activity#startActivityForResult(Intent, int)}
     */
    public RouterBuilder requestCode(int code) {
        this.mRequestCode = code;
        return this;
    }

    /**
     * {@link Intent#setAction(String)}
     */
    public RouterBuilder setAction(String action) {
        this.mAction = action;
        return this;
    }

    /**
     * {@link Intent#addFlags(int)}
     */
    public RouterBuilder addFlags(int flags) {
        if (mFlags == null) {
            mFlags = new ArrayList<>();
        }
        mFlags.add(flags);
        return this;
    }

    /**
     * {@link Intent#setFlags(int)} }
     */
    public RouterBuilder setFlags(int flag) {
        this.mSetFlag = flag;
        if (mFlags != null) {
            mFlags.clear();
        }
        return this;
    }

    /**
     * @see Intent#putExtra(String, String)
     * @see Bundle#putString(String, String)
     */
    public RouterBuilder putData(String key, String data) {
        if (mTargetData == null) {
            mTargetData = new Bundle();
        }
        mTargetData.putString(key, data);
        return this;
    }

    /**
     * @see Intent#putExtra(String, int)
     * @see Bundle#putInt(String, int)
     */
    public RouterBuilder putData(String key, int data) {
        checkTargetData();
        mTargetData.putInt(key, data);
        return this;
    }

    /**
     * @see Intent#putExtra(String, boolean)
     * @see Bundle#putBoolean(String, boolean)
     */
    public RouterBuilder putData(String key, boolean data) {
        checkTargetData();
        mTargetData.putBoolean(key, data);
        return this;
    }

    /**
     * @see Intent#putExtra(String, Serializable)
     * @see Bundle#putSerializable(String, Serializable)
     */
    public RouterBuilder putData(String key, Serializable serializable) {
        checkTargetData();
        mTargetData.putSerializable(key, serializable);
        return this;
    }

    /**
     * @see Intent#putExtra(String, Serializable)
     * @see Bundle#putSerializable(String, Serializable)
     */
    public RouterBuilder putData(@Nullable String key,
                                 @Nullable ArrayList<? extends Parcelable> value) {
        checkTargetData();
        mTargetData.putParcelableArrayList(key, value);
        return this;
    }

    /**
     * @see Intent#putExtras(Bundle)
     * @see Bundle#putAll(Bundle)
     */
    public RouterBuilder putAll(@NonNull Bundle value) {
        checkTargetData();
        mTargetData.putAll(value);
        return this;
    }

    /**
     * @see Intent#putExtra(String, Serializable)
     * @see Bundle#putParcelable(String, Parcelable)
     */
    public RouterBuilder putData(String key, Parcelable data) {
        checkTargetData();
        mTargetData.putParcelable(key, data);
        return this;
    }

    /**
     * 非空校验
     */
    private void checkTargetData() {
        if (mTargetData == null) {
            mTargetData = new Bundle();
        }
    }

    /**
     * 支持多个页面
     */
    public RouterBuilder nextPage() {
        Intent intent = getCurrentIntent();
        if (intent == null) {
            throw new RouterException("current cant null");
        }
        if (mBeforeIntent == null) {
            mBeforeIntent = new ArrayList<>();
        }
        mBeforeIntent.add(intent);
        return new RouterBuilder(mContext).next(mBeforeIntent);
    }


    /**
     * 最后调用
     * 打开界面
     */
    public void start() {
        if (mContext == null) {
            throw new RouterException("current context cant find ");
        }
        if (mRequestCode != 0 && !(mContext instanceof Activity)) {
            throw new RouterException("not find activity when request is not 0");
        }
        Intent intent = getCurrentIntent();
        if (intent == null) {
            Toast.makeText(mContext, "next page not find", Toast.LENGTH_SHORT).show();
            return;
        }
        if (mTargetData != null) {
            intent.putExtras(mTargetData);
        }
        if (mSetFlag != null) {
            intent.setFlags(mSetFlag);
        }
        if (mFlags != null && mFlags.size() > 0) {
            for (Integer flag : mFlags) {
                intent.addFlags(flag);
            }
        }
        if (mBeforeIntent != null && mBeforeIntent.size() > 0) {
            final int beforeSize = mBeforeIntent.size();
            Intent[] allIntents = new Intent[beforeSize + 1];
            mBeforeIntent.toArray(allIntents);
            allIntents[beforeSize] = intent;
            ActivityCompat.startActivities(mContext, allIntents);
        } else if (mRequestCode != 0) {
            ActivityCompat.startActivityForResult((Activity) mContext, intent, mRequestCode, mAnimBundle);
        } else {
            ActivityCompat.startActivity(mContext, intent, mAnimBundle);
        }
    }

    /**
     * 打开多个页面操作
     *
     * @param pages 之前的界面
     */
    private RouterBuilder next(ArrayList<Intent> pages) {
        if (pages != null && pages.size() > 0) {
            if (mBeforeIntent == null) {
                mBeforeIntent = new ArrayList<>();
            }
            mBeforeIntent.addAll(pages);
        }
        return this;
    }

    @Nullable
    private Intent getCurrentIntent() {
        Intent intent = null;
        if (mTargetPage != null) {
            intent = new Intent(mContext, mTargetPage);
        } else if (!TextUtils.isEmpty(mAction)) {
            intent = new Intent(mAction);
        }
        return intent;
    }

}
