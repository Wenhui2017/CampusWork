package com.shun.campuswork.activity;

import android.os.Bundle;

import com.lidroid.xutils.ViewUtils;
import com.shun.campuswork.R;

/**
 * 工作常遇见问题
 * Created by shun99 on 2015/12/3.
 */
public class QuestionActivity extends BaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        ViewUtils.inject(this);
    }
}
