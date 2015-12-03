package com.shun.campuswork.activity;

import android.view.View;

import com.shun.campuswork.R;
import com.shun.campuswork.tools.ToastUtils;
import com.shun.campuswork.view.TextPopView;

/**
 * Created by shun99 on 2015/12/3.
 */
public class QuestionActivity extends BaseActivity implements View.OnClickListener{
    @Override
    public void init() {
        setContentView(R.layout.activity_question);
        TextPopView tpv_1 = (TextPopView) findViewById(R.id.tpv_1);
        tpv_1.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        ToastUtils.makeText("...");
    }
}
