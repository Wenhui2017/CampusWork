package com.shun.campuswork.activity;

import android.os.Bundle;

import com.lidroid.xutils.ViewUtils;
import com.shun.campuswork.R;

/**
 * 防骗指南
 */
public class GuideActivity extends BaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        ViewUtils.inject(this);
    }
}
