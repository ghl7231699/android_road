package com.ghl.common.util;

import android.annotation.TargetApi;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;

import androidx.annotation.ColorInt;
import androidx.annotation.FloatRange;
import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.graphics.drawable.DrawableCompat;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class DrawableHelper {

    //----------------------------------------------- Drawable 着色 start -----------------------------------------------------------------

    /**
     * 对目标Drawable 进行着色
     *
     * @param drawable 目标Drawable
     * @param color    着色的颜色值
     * @return 着色处理后的Drawable
     */
    public static Drawable tintDrawable(@NonNull Drawable drawable, int color) {
        // 获取此drawable的共享状态实例
        Drawable wrappedDrawable = getCanTintDrawable(drawable);
        // 对 drawable 进行着色
        DrawableCompat.setTint(wrappedDrawable, color);
        return wrappedDrawable;
    }

    /**
     * 对目标Drawable 进行着色
     *
     * @param drawable 目标Drawable
     * @param colors   着色值
     * @return 着色处理后的Drawable
     */
    public static Drawable tintListDrawable(@NonNull Drawable drawable, ColorStateList colors) {
        Drawable wrappedDrawable = getCanTintDrawable(drawable);
        // 对 drawable 进行着色
        DrawableCompat.setTintList(wrappedDrawable, colors);
        return wrappedDrawable;
    }

    /**
     * 获取可以进行tint 的Drawable
     * <p>
     * 对原drawable进行重新实例化  newDrawable()
     * 包装  warp()
     * 可变操作 mutate()
     *
     * @param drawable 原始drawable
     * @return 可着色的drawable
     */
    @NonNull
    private static Drawable getCanTintDrawable(@NonNull Drawable drawable) {
        // 获取此drawable的共享状态实例
        Drawable.ConstantState state = drawable.getConstantState();
        // 对drawable 进行重新实例化、包装、可变操作
        return DrawableCompat.wrap(state == null ? drawable : state.newDrawable()).mutate();
    }

    //------------------------------------------------ Drawable 着色 end -----------------------------------------------------------------

    //------------------------------------------------ Drawable 圆角矩形 start ------------------------------------------------------------

    /**
     * 获得一个指定填充色，边框宽度、颜色的圆角矩形drawable。
     * Android 中 在xml中写的"shape"标签映射对象就是GradientDrawable。
     * 通过设置solidColors 和strokeColors 可实现选择器的效果
     *
     * @param solidColors  填充色
     * @param strokeColors 描边色
     * @param strokeWidth  描边线宽度
     * @param dashWidth    虚线（破折线）的长度（以像素为单位）
     * @param dashGap      虚线（破折线）间距，当dashGap=0dp时，为实线
     * @param radius       圆角半径(单位：像素)
     * @return GradientDrawable
     */
    public static Drawable getShapeDrawable(ColorStateList solidColors,
                                            ColorStateList strokeColors, int strokeWidth, float dashWidth, float dashGap,
                                            float radius) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        gradientDrawable.setCornerRadius(radius);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            gradientDrawable.setColor(solidColors);
            //显示一条虚线，破折线的宽度为dashWith，破折线之间的空隙的宽度为dashGap，当dashGap=0dp时，为实线
            gradientDrawable.setStroke(strokeWidth, strokeColors, dashWidth, dashGap);
        } else {
            gradientDrawable.setColor(solidColors.getDefaultColor());
            //显示一条虚线，破折线的宽度为dashWith，破折线之间的空隙的宽度为dashGap，当dashGap=0dp时，为实线
            gradientDrawable.setStroke(strokeWidth, strokeColors.getDefaultColor(), dashWidth, dashGap);
        }
        return gradientDrawable;
    }

    public static Drawable getLineDrawable(int color, int lineWidth, int dashWidth, int dashGap, float radius) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(GradientDrawable.LINE);
        gradientDrawable.setCornerRadius(radius);
        ColorStateList colorStateList = ColorStateList.valueOf(color);
        gradientDrawable.setColor(colorStateList.getDefaultColor());
        gradientDrawable.setStroke(lineWidth, colorStateList.getDefaultColor(), dashWidth, dashGap);
        return gradientDrawable;
    }

    /**
     * 获得一个指定填充色，指定描边色的圆角矩形drawable
     *
     * @param solidColor  填充色
     * @param strokeColor 描边色
     * @param strokeWidth 描边线宽度
     * @param dashWidth   虚线（破折线）宽度
     * @param dashGap     虚线（破折线）间距，当dashGap=0dp时，为实线
     * @param radius      圆角半径
     * @return GradientDrawable
     */
    public static Drawable getShapeDrawable(@ColorInt int solidColor,
                                            @ColorInt int strokeColor, int strokeWidth, float dashWidth, float dashGap,
                                            float radius) {
        return getShapeDrawable(ColorStateList.valueOf(solidColor),
                ColorStateList.valueOf(strokeColor), strokeWidth, dashWidth, dashGap,
                radius);
    }

    /**
     * 获得一个指定填充色，指定描边色的圆角矩形drawable
     *
     * @param solidColors  填充色
     * @param strokeColors 描边色
     * @param strokeWidth  描边线宽度
     * @param radius       圆角半径
     * @return GradientDrawable
     */
    public static Drawable getShapeDrawable(ColorStateList solidColors,
                                            ColorStateList strokeColors, int strokeWidth,
                                            float radius) {
        return getShapeDrawable(solidColors, strokeColors, strokeWidth, 0, 0, radius);
    }

    /**
     * 获得一个指定填充色，指定描边色的圆角矩形drawable
     *
     * @param solidColor  填充色
     * @param strokeColor 描边色
     * @param strokeWidth 描边线宽度
     * @param radius      圆角半径
     * @return GradientDrawable
     */
    public static Drawable getShapeDrawable(@ColorInt int solidColor,
                                            @ColorInt int strokeColor, int strokeWidth,
                                            float radius) {
        return getShapeDrawable(ColorStateList.valueOf(solidColor), ColorStateList.valueOf(strokeColor), strokeWidth, radius);
    }

    /**
     * 获得一个指定填充颜色，描边颜色的矩形drawable
     *
     * @param solidColor  填充色
     * @param strokeColor 描边色
     * @param strokeWidth 描边线宽度
     * @return GradientDrawable
     */
    public static Drawable getShapeDrawable(@ColorInt int solidColor,
                                            @ColorInt int strokeColor, int strokeWidth) {
        return getShapeDrawable(solidColor, strokeColor, strokeWidth, 0, 0, 0);
    }

    /**
     * 获得一个指定描边颜色的圆角矩形drawable
     *
     * @param strokeColor 描边色
     * @param strokeWidth 描边宽度
     * @param radius      圆角半径
     * @return GradientDrawable
     */
    public static Drawable getStrokeShapeDrawable(@ColorInt int strokeColor, int strokeWidth, float radius) {
        return getShapeDrawable(0, strokeColor, strokeWidth, radius);
    }

    /**
     * 获得一个指定描边颜色的矩形drawable
     *
     * @param strokeColor 描边色
     * @param strokeWidth 描边宽度
     * @return GradientDrawable
     */
    public static Drawable getStrokeShapeDrawable(@ColorInt int strokeColor, int strokeWidth) {
        return getShapeDrawable(0, strokeColor, strokeWidth, 0);
    }

    /**
     * 获得一个指定填充色的圆角矩形drawable
     *
     * @param solidColor 填充色
     * @param radius     圆角半径
     * @return GradientDrawable
     */
    public static Drawable getSolidShapeDrawable(@ColorInt int solidColor, float radius) {
        return getShapeDrawable(solidColor, 0, 0, radius);
    }

    public static Drawable getShapeDrawable(@ColorInt int solidColor, float topLeftRadius, float topRightRadius, float bottomRightRadius, float bottomLeftRadius) {
        float[] radius = new float[]{topLeftRadius, topLeftRadius, topRightRadius, topRightRadius, bottomRightRadius, bottomRightRadius, bottomLeftRadius, bottomLeftRadius};
        return getShapeDrawable(ColorStateList.valueOf(solidColor), ColorStateList.valueOf(0), 0, 0, 0, radius);
    }

    /**
     * 获取多边形Drawable
     */
    public static Drawable getShapeDrawable(ColorStateList solidColors,
                                            ColorStateList strokeColors, int strokeWidth, float dashWidth, float dashGap,
                                            float[] radius) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        gradientDrawable.setCornerRadii(radius);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            gradientDrawable.setColor(solidColors);
            //显示一条虚线，破折线的宽度为dashWith，破折线之间的空隙的宽度为dashGap，当dashGap=0dp时，为实线
            gradientDrawable.setStroke(strokeWidth, strokeColors, dashWidth, dashGap);
        } else {
            gradientDrawable.setColor(solidColors.getDefaultColor());
            //显示一条虚线，破折线的宽度为dashWith，破折线之间的空隙的宽度为dashGap，当dashGap=0dp时，为实线
            gradientDrawable.setStroke(strokeWidth, strokeColors.getDefaultColor(), dashWidth, dashGap);
        }
        return gradientDrawable;
    }

    //----------------------------------------------------- Drawable 圆角矩形 end ---------------------------------------------------------

    //----------------------------------------------------- Drawable 选择器 start-----------------------------------------------------------

    /**
     * 获得一个选择器Drawable.
     * Android 中 在xml中写的"selector"标签映射对象就是StateListDrawable 对象
     *
     * @param defaultDrawable 默认时显示的Drawable
     * @param pressedDrawable 按下时显示的Drawable
     * @return 选择器Drawable
     */
    public static StateListDrawable getSelectorDrawable(Drawable defaultDrawable, Drawable pressedDrawable) {
        if (defaultDrawable == null) {
            return null;
        }
        if (pressedDrawable == null) {
            pressedDrawable = defaultDrawable;
        }
        int[][] state = {{-android.R.attr.state_pressed}, {android.R.attr.state_pressed}};
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(state[0], defaultDrawable);
        stateListDrawable.addState(state[1], pressedDrawable);
        return stateListDrawable;
    }

    /**
     * 获得一个选择器Drawable.
     * Android 中 在xml中写的"selector"标签映射对象就是StateListDrawable 对象
     *
     * @param defaultColor 默认时显示的颜色
     * @param pressedColor 按下时显示的颜色
     * @param radius       圆角半径
     * @return 选择器Drawable
     */
    public static StateListDrawable getSelectorDrawable(int defaultColor, int pressedColor, float radius) {

        Drawable defaultDrawable = getSolidShapeDrawable(defaultColor, radius);
        Drawable pressedDrawable = getSolidShapeDrawable(pressedColor, radius);

        return getSelectorDrawable(defaultDrawable, pressedDrawable);
    }

    /**
     * 获得一个选择器Drawable.
     * Android 中 在xml中写的"selector"标签映射对象就是StateListDrawable 对象
     *
     * @param defaultColor       默认时显示的颜色
     * @param pressedColor       按下时显示的颜色
     * @param defaultStrokeColor 默认时显示的描边颜色
     * @param pressedStrokeColor 按下时显示的描边颜色
     * @param strokeWidth        描边线的宽度
     * @param radius             圆角半径
     * @return 选择器Drawable
     */
    public static StateListDrawable getSelectorDrawable(int defaultColor, int pressedColor,
                                                        int defaultStrokeColor, int pressedStrokeColor, int strokeWidth,
                                                        float radius) {

        Drawable defaultDrawable = getShapeDrawable(defaultColor, defaultStrokeColor, strokeWidth, radius);
        Drawable pressedDrawable = getShapeDrawable(pressedColor, pressedStrokeColor, strokeWidth, radius);

        return getSelectorDrawable(defaultDrawable, pressedDrawable);
    }
    //----------------------------------------------------- Drawable 选择器 end---------------------------------------------------------------

    @TargetApi(16)
    public static GradientDrawable getCircleGradientDrawable(@ColorInt int startColor,
                                                             @ColorInt int endColor, int radius,
                                                             @FloatRange(from = 0f, to = 1f) float centerX,
                                                             @FloatRange(from = 0f, to = 1f) float centerY) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColors(new int[]{
                startColor,
                endColor
        });
        gradientDrawable.setGradientType(GradientDrawable.RADIAL_GRADIENT);
        gradientDrawable.setGradientRadius(radius);
        gradientDrawable.setGradientCenter(centerX, centerY);
        return gradientDrawable;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static GradientDrawable getGradientDrawable(@ColorInt int startColor,
                                                       @ColorInt int endColor,
                                                       int radius,
                                                       GradientDrawable.Orientation orientation) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColors(new int[]{
                startColor,
                endColor
        });
        gradientDrawable.setGradientType(GradientDrawable.RADIAL_GRADIENT);
        gradientDrawable.setOrientation(orientation);
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        gradientDrawable.setCornerRadius(radius);
        return gradientDrawable;
    }


    @TargetApi(16)
    public static GradientDrawable getCircleGradientDrawable(@ColorInt int startColor,
                                                             @ColorInt int endColor, float topLeftRadius, float topRightRadius, float bottomRightRadius, float bottomLeftRadius,
                                                             GradientDrawable.Orientation orientation) {
        GradientDrawable gradientDrawable = new GradientDrawable(orientation, new int[]{startColor, endColor});
        gradientDrawable.setCornerRadii(new float[]{topLeftRadius, topLeftRadius, topRightRadius, topRightRadius, bottomRightRadius, bottomRightRadius, bottomLeftRadius, bottomLeftRadius});
        gradientDrawable.setGradientType(GradientDrawable.LINEAR_GRADIENT);
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        return gradientDrawable;
    }

    @TargetApi(16)
    public static GradientDrawable getCircleGradientDrawable(@ColorInt int[] colors, float topLeftRadius, float topRightRadius, float bottomRightRadius, float bottomLeftRadius,
                                                             GradientDrawable.Orientation orientation) {
        GradientDrawable gradientDrawable = new GradientDrawable(orientation, colors);
        gradientDrawable.setCornerRadii(new float[]{topLeftRadius, topLeftRadius, topRightRadius, topRightRadius, bottomRightRadius, bottomRightRadius, bottomLeftRadius, bottomLeftRadius});
        gradientDrawable.setGradientType(GradientDrawable.LINEAR_GRADIENT);
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        return gradientDrawable;
    }

    // --------------------------------------------- 构造器模式 -------------------------------------------------


    /**
     * 获取建造者
     *
     * @param shape 图形
     * @return {@link Builder}
     */
    public static Builder getBuilder(int shape) {
        return new Builder(shape);
    }


    /**
     * Drawable 建造者，链式结构，方便使用
     */
    public static class Builder {
        /**
         * Shape is a rectangle, possibly with rounded corners
         */
        public static final int RECTANGLE = GradientDrawable.RECTANGLE;

        /**
         * Shape is an ellipse
         */
        public static final int OVAL = GradientDrawable.OVAL;

        /**
         * Shape is a line
         */
        public static final int LINE = GradientDrawable.LINE;

        /**
         * Gradient is linear (default.)
         */
        public static final int LINEAR_GRADIENT = GradientDrawable.LINEAR_GRADIENT;

        /**
         * Gradient is circular.
         */
        public static final int RADIAL_GRADIENT = GradientDrawable.RADIAL_GRADIENT;

        /**
         * Gradient is a sweep.
         */
        public static final int SWEEP_GRADIENT = GradientDrawable.SWEEP_GRADIENT;


        private ColorStateList mSolidColors;
        /**
         * The color state list of the stroke
         */
        private ColorStateList mStrokeColors;
        private @ColorInt
        int[] mGradientColors;

        /**
         * @hide
         */
        @IntDef({LINEAR_GRADIENT, RADIAL_GRADIENT, SWEEP_GRADIENT})
        @Retention(RetentionPolicy.SOURCE)
        public @interface GradientType {
        }

        private @GradientType
        int mGradient = LINEAR_GRADIENT;
        private GradientDrawable.Orientation mOrientation;

        /**
         * The width in pixels of the stroke
         */
        private int mStrokeWidth = -1; // if >= 0 use stroking.
        /**
         * The length in pixels of the dashes, set to 0 to disable dashes
         */
        private float mStrokeDashWidth = 0.0f;
        /**
         * The gap in pixels between dashes
         */
        private float mStrokeDashGap = 0.0f;

        private float mRadius = 0.0f; // use this if mRadiusArray is null
        private float[] mRadiusArray = null;

        private int width;
        private int height;

        GradientDrawable mGradientDrawable;

        private Builder(int shape) {
            mGradientDrawable = new GradientDrawable();
            mGradientDrawable.setShape(shape == RECTANGLE || shape == OVAL || shape == LINE ? shape : RECTANGLE);
        }

        /**
         * Changes this drawable to use a single color instead of a gradient.
         * <p>
         * <strong>Note</strong>: changing color will affect all instances of a
         * drawable loaded from a resource. It is recommended to invoke
         * {@link # GradientDrawable.mutate()} before changing the color.
         *
         * @param argb The color used to fill the shape
         */
        public Builder setColor(@ColorInt int argb) {
            this.mSolidColors = ColorStateList.valueOf(argb);
            this.mGradientColors = null;
            return this;
        }

        /**
         * Changes this drawable to use a single color state list instead of a
         * gradient. Calling this method with a null argument will clear the color
         * and is equivalent to calling {@link #setColor(int)} with the argument
         * {@link Color#TRANSPARENT}.
         * <p>
         * <strong>Note</strong>: changing color will affect all instances of a
         * drawable loaded from a resource. It is recommended to invoke
         * {@link # GradientDrawable.mutate()()} before changing the color.</p>
         *
         * @param colorStateList The color state list used to fill the shape
         */
        public Builder setColor(@Nullable ColorStateList colorStateList) {
            this.mSolidColors = colorStateList;
            this.mGradientColors = null;
            return this;
        }

        /**
         * Sets the colors used to draw the gradient.
         * <p>
         * Each color is specified as an ARGB integer and the array must contain at
         * least 2 colors.
         * <p>
         * <strong>Note</strong>: changing colors will affect all instances of a
         * drawable loaded from a resource. It is recommended to invoke
         * {@link # GradientDrawable.mutate()} before changing the colors.
         *
         * @param colors an array containing 2 or more ARGB colors
         * @see # mutate()
         * @see #setColor(int)
         */
        public Builder setGradientColors(@Nullable int[] colors) {
            this.mGradientColors = colors;
            this.mSolidColors = null;
            return this;
        }

        /**
         * Sets the type of gradient used by this drawable.
         * <p>
         * <strong>Note</strong>: changing this property will affect all instances
         * of a drawable loaded from a resource. It is recommended to invoke
         */
        public Builder setGradientType(@GradientType int gradient) {
            this.mGradient = gradient;
            return this;
        }

        /**
         * Sets the orientation of the gradient defined in this drawable.
         * <p>
         * <strong>Note</strong>: changing orientation will affect all instances
         * of a drawable loaded from a resource. It is recommended to invoke
         */
        public Builder setOrientation(GradientDrawable.Orientation orientation) {
            this.mOrientation = orientation;
            return this;
        }

        /**
         * <p>Set the stroke width and color for the drawable. If width is zero,
         * then no stroke is drawn.</p>
         * <p><strong>Note</strong>: changing this property will affect all instances
         * of a drawable loaded from a resource. It is recommended to invoke
         * {@link # mutate()} before changing this property.</p>
         *
         * @param width The width in pixels of the stroke
         * @param color The color of the stroke
         * @see # mutate()
         * @see #setStroke(int, int, float, float)
         */
        public Builder setStroke(int width, @ColorInt int color) {
            this.mStrokeWidth = width;
            this.mStrokeColors = ColorStateList.valueOf(color);
            return this;
        }

        public Builder setSize(int width, int height) {
            this.width = width;
            this.height = height;
            return this;
        }

        /**
         * <p>Set the stroke width and color state list for the drawable. If width
         * is zero, then no stroke is drawn.</p>
         * <p><strong>Note</strong>: changing this property will affect all instances
         * of a drawable loaded from a resource. It is recommended to invoke
         * {@link # mutate()} before changing this property.</p>
         *
         * @param width          The width in pixels of the stroke
         * @param colorStateList The color state list of the stroke
         * @see #setStroke(int, ColorStateList, float, float)
         */
        public Builder setStroke(int width, ColorStateList colorStateList) {
            this.mStrokeWidth = width;
            this.mStrokeColors = colorStateList;
            return this;
        }

        /**
         * <p>Set the stroke width and color for the drawable. If width is zero,
         * then no stroke is drawn. This method can also be used to dash the stroke.</p>
         * <p><strong>Note</strong>: changing this property will affect all instances
         * of a drawable loaded from a resource. It is recommended to invoke
         * {@link # mutate()} before changing this property.</p>
         *
         * @param width     The width in pixels of the stroke
         * @param color     The color of the stroke
         * @param dashWidth The length in pixels of the dashes, set to 0 to disable dashes
         * @param dashGap   The gap in pixels between dashes
         * @see #setStroke(int, int)
         */
        public Builder setStroke(int width, @ColorInt int color, float dashWidth, float dashGap) {
            this.mStrokeWidth = width;
            this.mStrokeColors = ColorStateList.valueOf(color);
            this.mStrokeDashWidth = dashWidth;
            this.mStrokeDashGap = dashGap;
            return this;
        }

        /**
         * <p>Set the stroke width and color state list for the drawable. If width
         * is zero, then no stroke is drawn. This method can also be used to dash
         * the stroke.</p>
         * <p><strong>Note</strong>: changing this property will affect all instances
         * of a drawable loaded from a resource. It is recommended to invoke
         * {@link # mutate()} before changing this property.</p>
         *
         * @param width          The width in pixels of the stroke
         * @param colorStateList The color state list of the stroke
         * @param dashWidth      The length in pixels of the dashes, set to 0 to disable dashes
         * @param dashGap        The gap in pixels between dashes
         * @see #setStroke(int, ColorStateList)
         */
        public Builder setStroke(int width, ColorStateList colorStateList, float dashWidth, float dashGap) {
            this.mStrokeWidth = width;
            this.mStrokeColors = colorStateList;
            this.mStrokeDashWidth = dashWidth;
            this.mStrokeDashGap = dashGap;
            return this;
        }

        public Builder setCornerRadius(float radius) {
            if (radius < 0) {
                radius = 0;
            }
            this.mRadius = radius;
            this.mRadiusArray = null;
            return this;
        }

        /**
         * Specifies radii for each of the 4 corners. For each corner, the array
         * contains 2 values, <code>[X_radius, Y_radius]</code>. The corners are
         * ordered top-left, top-right, bottom-right, bottom-left. This property
         * is honored only when the shape is of type {@link #RECTANGLE}.
         * <p>
         * <strong>Note</strong>: changing this property will affect all instances
         * of a drawable loaded from a resource. It is recommended to invoke
         * {@link # mutate()} before changing this property.
         *
         * @param radii an array of length >= 8 containing 4 pairs of X and Y
         *              radius for each corner, specified in pixels
         * @see #setCornerRadius(float)
         */
        public Builder setCornerRadii(@Nullable float[] radii) {
            this.mRadiusArray = radii;
            if (radii == null) {
                this.mRadius = 0;
            }
            return this;
        }

        public Drawable create() {
            // 圆角
            if (mRadiusArray != null) {
                mGradientDrawable.setCornerRadii(mRadiusArray);

            } else if (mRadius > 0.0f) {
                mGradientDrawable.setCornerRadius(mRadius);
            }

            // 填充色
            if (mSolidColors != null) {// 纯色
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    mGradientDrawable.setColor(mSolidColors);
                } else {
                    mGradientDrawable.setColor(mSolidColors.getDefaultColor());
                }

            } else if (mGradientColors != null) {// 渐变色
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    mGradientDrawable.setColors(mGradientColors);
                    mGradientDrawable.setGradientType(mGradient);
                    mGradientDrawable.setOrientation(mOrientation);
                }
            } else {
                mGradientDrawable.setColor(Color.TRANSPARENT);
            }

            if (width > 0 && height > 0) {
                mGradientDrawable.setSize(width, height);
            }

            // 描边
            if (mStrokeColors != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    //显示一条虚线，破折线的宽度为dashWith，破折线之间的空隙的宽度为dashGap，当dashGap=0dp时，为实线
                    mGradientDrawable.setStroke(mStrokeWidth, mStrokeColors, mStrokeDashWidth, mStrokeDashGap);
                } else {
                    //显示一条虚线，破折线的宽度为dashWith，破折线之间的空隙的宽度为dashGap，当dashGap=0dp时，为实线
                    mGradientDrawable.setStroke(mStrokeWidth, mStrokeColors.getDefaultColor(), mStrokeDashWidth, mStrokeDashGap);
                }
            }

            return mGradientDrawable;
        }
    }
}