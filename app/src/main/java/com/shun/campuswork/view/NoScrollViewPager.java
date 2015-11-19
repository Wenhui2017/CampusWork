package com.shun.campuswork.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 不处理滚动事件
 * Created by shun99 on 2015/11/19.
 */
public class NoScrollViewPager extends ViewPager {
    public NoScrollViewPager(Context context) {
        super(context);
    }

    public NoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**不处理滚动事件*/
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }
}
