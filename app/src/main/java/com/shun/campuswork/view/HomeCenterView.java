package com.shun.campuswork.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shun.campuswork.R;


/**
 * 自定义组合控件
 * Created by shun99 on 2015/12/2.
 */
public class HomeCenterView extends LinearLayout {
    private TextView tv_title, tv_des;
    private View v_icon;

    private String title, des;
    private int icon;

    public HomeCenterView(Context context) {
        super(context);
        init(context);
    }


    public HomeCenterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
        initAttrs(context, attrs);
        initDate();
    }

    private void init(Context context) {
        //加载视图的布局
        LayoutInflater.from(context).inflate(R.layout.view_home_center, this);
        tv_title = (TextView) findViewById(R.id.tv_title);
        v_icon = findViewById(R.id.v_icon);
        tv_des = (TextView) findViewById(R.id.tv_des);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        //加载自定义的属性
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.text_image);
        title = a.getString(R.styleable.text_image_tv_title);
        icon = a.getResourceId(R.styleable.text_image_v_icon, R.mipmap.ic_launcher);
        des =  a.getString(R.styleable.text_image_tv_des);
        //回收资源，这一句必须调用
        a.recycle();
    }

    private void initDate() {
        tv_title.setText(title);
        tv_des.setText(des);
        v_icon.setBackgroundResource(icon);
    }

//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        getParent().requestDisallowInterceptTouchEvent(true);
//        return super.dispatchTouchEvent(ev);
//    }
}
