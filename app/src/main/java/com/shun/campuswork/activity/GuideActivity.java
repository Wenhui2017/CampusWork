package com.shun.campuswork.activity;

import android.view.View;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.shun.campuswork.R;

/**
 * Created by shun99 on 2015/12/3.
 */
public class GuideActivity extends BaseActivity{

    @Override
    public void init() {
        setContentView(R.layout.activity_guide);
        ViewUtils.inject(this);
    }
}
