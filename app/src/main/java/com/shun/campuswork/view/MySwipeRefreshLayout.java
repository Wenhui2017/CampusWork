package com.shun.campuswork.view;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Created by shun99 on 2015/11/24.
 */
public class MySwipeRefreshLayout extends SwipeRefreshLayout{

    public MySwipeRefreshLayout(Context context) {
        super(context);
    }

    public MySwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.w("ls_输出", "" + ev.getRawX());
        getParent().requestDisallowInterceptTouchEvent(true);
        //只需这句话，让父类不拦截触摸事件就可以了。
        return super.dispatchTouchEvent(ev);
    }
}
