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
public class TextImageView extends LinearLayout {
    private TextView mTextView;
    private View v_image, v_line, v_arrow;

    private String title , line_visibility, arrow_visibility;
    private int image;

    public TextImageView(Context context) {
        super(context);
        init(context);
    }


    public TextImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
        initAttrs(context, attrs);
        initDate();
    }


    private void init(Context context) {
        //加载视图的布局
        LayoutInflater.from(context).inflate(R.layout.view_text_image, this);
        v_image = findViewById(R.id.v_image);
        v_line = findViewById(R.id.v_line);
        v_arrow = findViewById(R.id.v_arrow);
        mTextView = (TextView) findViewById(R.id.tv_title);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        //加载自定义的属性
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.text_image);
        title = a.getString(R.styleable.text_image_view_title);
        image = a.getResourceId(R.styleable.text_image_view_image, R.mipmap.ic_launcher);
//        line_visibility = a.getString(R.styleable.text_image_line_visibility);
//        arrow_visibility = a.getString(R.styleable.text_image_arrow_visibility);
        //回收资源，这一句必须调用
        a.recycle();
    }

    private void initDate() {
        mTextView.setText(title);
        v_image.setBackgroundResource(image);
//        setViewVisibility(v_line, line_visibility);
//        setViewVisibility(v_arrow, arrow_visibility);
    }


    public void setViewVisibility(View view, String state) {
        if(state.equals("gone")){
            view.setVisibility(GONE);
        }else if(state.equals("invisible")){
            view.setVisibility(INVISIBLE);
        }else{
            view.setVisibility(VISIBLE);
        }
    }
}
