package com.shun.campuswork.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by shun99 on 2015/11/24.
 */
public class MyHeadViewPager extends ViewPager{

    public MyHeadViewPager(Context context) {
        super(context);
        init();
    }

    public MyHeadViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        getParent().requestDisallowInterceptTouchEvent(true);
        //只需这句话，让父类不拦截触摸事件就可以了。
        return super.dispatchTouchEvent(ev);
    }
}
