package com.shun.campuswork.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Created by shun99 on 2015/11/23.
 */
public class RationLayout extends FrameLayout {
    public float ratio = 2.43f; // 比例值

    public RationLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

    }

    /**
     * xml中设置布局，会调用这个构造方法
     * @param context
     * @param attrs
     */
    public RationLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        ratio = attrs.getAttributeFloatValue(
                "http://schemas.android.com/apk/res/com.shun.googleplay",
                "ratio", 2.43f);
    }

    public RationLayout(Context context) {
        super(context);
    }

    /**
     * 制定测量规则
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int width = widthSize - getPaddingLeft() - getPaddingRight();// 去掉左右两边的padding

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int height = heightSize - getPaddingTop() - getPaddingBottom();

        if (widthMode == MeasureSpec.EXACTLY
                && heightMode != MeasureSpec.EXACTLY) {
            // 修正一下 高度的值 让高度=宽度/比例
            height = (int) (width / ratio + 0.5f); // 保证4舍五入
        } else if (widthMode != MeasureSpec.EXACTLY
                && heightMode == MeasureSpec.EXACTLY) {
            // 由于高度是精确的值 ,宽度随着高度的变化而变化
            width = (int) ((height * ratio) + 0.5f);
        }
        // 重新制作了新的规则
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(width + getPaddingLeft()
                + getPaddingRight(), MeasureSpec.EXACTLY);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height
                + getPaddingBottom() + getPaddingTop(), MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
