package com.shun.campuswork.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ListView;

/**
 * Created by shun99 on 2015/11/23.
 */
public class MyListView extends ListView {

    public MyListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyListView(Context context) {
        super(context);
    }

    private float startX = 0;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
//        if (startX < 50) {
//            startX = ev.getRawX();
//        }
//        if (ev.getRawX() > 500 && startX != 0) {
//            Log.w(".....",".......0000");
//            return true;
//        }
        getParent().requestDisallowInterceptTouchEvent(true);
        //只需这句话，让父类不拦截触摸事件就可以了。
        return super.dispatchTouchEvent(ev);
    }

}
