package com.shun.campuswork.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shun.campuswork.R;


/**
 * 自定义组合控件
 * 此处处理了点击事件，就不能再调用处再次处理
 * Created by shun99 on 2015/12/2.
 */
public class TextPopView extends LinearLayout {
    private LinearLayout ll_root;
    private TextView tv_title, tv_des;
    private View v_icon;
    private String title, des;
    private int arrowOn, arrowOff;
    private boolean isOpen = false;

    public TextPopView(Context context) {
        super(context);
        init(context);
    }


    public TextPopView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
        initAttrs(context, attrs);
        initDate();
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_text_pop, this);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_des = (TextView) findViewById(R.id.tv_des);
        v_icon = findViewById(R.id.v_icon);
        ll_root = (LinearLayout) findViewById(R.id.ll_root);
        ll_root.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isOpen){
                    isOpen = false;
                    v_icon.setBackgroundResource(R.mipmap.right);
                    tv_des.setVisibility(GONE);
                }else {
                    isOpen = true;
                    v_icon.setBackgroundResource(R.mipmap.down);
                    tv_des.setVisibility(VISIBLE);
                }
            }
        });
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        //加载自定义的属性
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.text_image);
        title = a.getString(R.styleable.text_image_tv_title);
        des =  a.getString(R.styleable.text_image_tv_des);
        //回收资源，这一句必须调用
        a.recycle();
    }

    private void initDate() {
        tv_title.setText(title);
        tv_des.setText(des);
        v_icon.setBackgroundResource(R.mipmap.right);
        tv_des.setVisibility(GONE);
    }



}
