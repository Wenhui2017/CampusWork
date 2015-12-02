package com.shun.campuswork.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shun.campuswork.R;


/**
 * 自定义组合控件
 * Created by shun99 on 2015/12/2.
 */
public class ButtonView extends LinearLayout {
    private TextView tv_title;
    private View v_image;
    private LinearLayout ll_background;

    private String title;
    private int background, image;

    public ButtonView(Context context) {
        super(context);
        init(context);
    }


    public ButtonView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
        initAttrs(context, attrs);
        initDate();
    }

    private void init(Context context) {
        //加载视图的布局
        LayoutInflater.from(context).inflate(R.layout.view_button, this);
        ll_background = (LinearLayout) findViewById(R.id.ll_background);
        v_image = findViewById(R.id.v_image);
        tv_title = (TextView) findViewById(R.id.tv_title);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        //加载自定义的属性
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.text_image);
        title = a.getString(R.styleable.text_image_view_title);
        image = a.getResourceId(R.styleable.text_image_view_image, R.mipmap.ic_launcher);
        background =  a.getResourceId(R.styleable.text_image_ll_background, R.drawable.rectangle);
        //回收资源，这一句必须调用
        a.recycle();
    }

    private void initDate() {
        tv_title.setText(title);
        ll_background.setBackgroundResource(background);
        v_image.setBackgroundResource(image);
    }




}
