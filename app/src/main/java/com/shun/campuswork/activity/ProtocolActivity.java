package com.shun.campuswork.activity;

import android.os.Bundle;
import android.view.View;

import com.shun.campuswork.R;

/**
 * 注册协议
 * Created by shun99 on 2015/12/6.
 */
public class ProtocolActivity extends BaseActivity implements View.OnClickListener{

    private View view;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_protocol);
        view = findViewById(R.id.v_back);
        view.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.v_back){
            onBackPressed();
        }
    }
}
