package com.ghl.common.widgets.views;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

import com.ghl.common.R;


public class CustomerView extends View {
    // 刻度尺高度
    private static final int DIVIDING_RULE_HEIGHT = 70;
    // 距离左右间
    private static final int DIVIDING_RULE_MARGIN_LEFT_RIGHT = 10;

    // 第一条线距离边框距离
    private static final int FIRST_LINE_MARGIN = 5;
    // 打算绘制的厘米数
    private static final int DEFAULT_COUNT = 9;
    private Paint mPaint;
    private Bitmap mBitmap;
    Resources mResources;
    private int mDividRuleHeight;
    private int mHalfRuleHeight;
    private int mDividRuleLeftMargin;
    private int mFirstLineMargin;

    private int mWidth;
    private int mHeight;
    private Rect mRect;
    private Context mContext;

    public CustomerView(Context context) {
        this(context, null);
    }

    public CustomerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initMetrics(context);
        initData();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void initData() {
        mPaint = new Paint();
        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        mWidth = mDividRuleHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                DIVIDING_RULE_HEIGHT, mResources.getDisplayMetrics());
        mHalfRuleHeight = mDividRuleHeight / 2;
        mDividRuleLeftMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                DIVIDING_RULE_MARGIN_LEFT_RIGHT, mResources.getDisplayMetrics());
        mFirstLineMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                FIRST_LINE_MARGIN, mResources.getDisplayMetrics());
    }

    private void initMetrics(Context context) {
        mContext = context;
        DisplayMetrics metric = getResources().getDisplayMetrics();
        mWidth = metric.widthPixels;// 屏幕宽度（像素）
        mHeight = metric.heightPixels;// 屏幕高度（像素）
        float density = metric.density;// 屏幕密度（0.75 / 1.0 / 1.5）
        int densityDpi = metric.densityDpi;
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        canvas.drawLine(100, 200, 110, 300, mPaint);
//        canvas.save();
//        canvas.clipRect(100, 400, 150, 450);
//        canvas.translate(200, 0);
//        canvas.rotate(45);
//        canvas.scale(1.3f, 1.3f, 400, 400);
//        canvas.skew(1, 0);
//        canvas.drawBitmap(mBitmap, 400, 400, mPaint);
//        canvas.restore();
        super.onDraw(canvas);
        //绘制外框
        drawOutLine(canvas);
        drawLines(canvas);
    }

    private void drawOutLine(Canvas canvas) {
        mRect = new Rect(mDividRuleLeftMargin, 10, mWidth - mDividRuleLeftMargin, mHalfRuleHeight + 10);
//        mLineInterval = (mWidth - 2 * mDividRuleLeftMargin - 2 * mFirstLineMargin)
//                / (DEFAULT_COUNT * 10 - 1);
        canvas.drawRect(mRect, mPaint);
    }

    private void drawLines(Canvas canvas) {

    }
}
