package com.shun.campuswork.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.shun.campuswork.R;

/**
 * 注册协议
 * Created by shun99 on 2015/12/6.
 */
public class ProtocolActivity extends BaseActivity implements View.OnClickListener{

    private TextView tv_register;

    @Override
    public void init() {
        setContentView(R.layout.activity_enter);
        tv_register = (TextView) findViewById(R.id.tv_register);
        initListener();
    }

    private void initListener() {
        tv_register.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_register:
                startActivity(new Intent(ProtocolActivity.this, RegisterActivity.class));
                break;
            case R.id.v_back:
                onBackPressed();
                break;
        }
    }


}
