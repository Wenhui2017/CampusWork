package com.shun.campuswork.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.shun.campuswork.R;
import com.shun.campuswork.domain.UserInfo;
import com.shun.campuswork.global.GlobalContants;
import com.shun.campuswork.tools.SharedPreferencesUtils;

/**
 * 起始页
 */
public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initFirst();
        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                //跳转后结束当前引导页
                SplashActivity.this.finish();
            }
        }.start();
    }

    private void initFirst() {
        Boolean isFirst = SharedPreferencesUtils.getBoolean("first", true);
        if (isFirst) {
        }
        SharedPreferencesUtils.putBoolean("first", false);
    }
}
