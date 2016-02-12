package com.shun.campuswork.activity;

import android.content.Intent;

import com.google.gson.Gson;
import com.shun.campuswork.R;
import com.shun.campuswork.domain.UserInfo;
import com.shun.campuswork.tools.SharedPreferencesUtils;

/**
 * Created by shun99 on 2015/12/30.
 */
public class SplashActivity extends BaseActivity {
    @Override
    public void init() {
        setContentView(R.layout.activity_splash);
        initFirst();
        new Thread(){
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
            }
        }.start();

    }

    private void initFirst() {
        Boolean isFirst = SharedPreferencesUtils.getBoolean("first", true);
        if (isFirst) {
            SharedPreferencesUtils.putString("13243189659", "123456");
            //初始化SHUN的信息
            UserInfo userInfo = new UserInfo();
            userInfo.headUrl = "http://campuswork1.sinaapp.com/head.jpg";
            userInfo.user = "SHUN";
            userInfo.sex = "男";
            userInfo.phone = "13243189659";
            userInfo.emil = "929182889@qq.com";
            Gson gson = new Gson();
            String json = gson.toJson(userInfo);
            SharedPreferencesUtils.putString("13243189659" + SharedPreferencesUtils.INFO, json);
            SharedPreferencesUtils.putString("13243189659" + SharedPreferencesUtils.SIGNUP, "");
            SharedPreferencesUtils.putString("13243189659" + SharedPreferencesUtils.STAR, "");
        }
        SharedPreferencesUtils.putBoolean("first", false);
    }
}
