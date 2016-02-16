package com.shun.campuswork.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.lidroid.xutils.exception.HttpException;
import com.shun.campuswork.R;
import com.shun.campuswork.dateprotocol.BaseProtocolNoCache;
import com.shun.campuswork.dateprotocol.LoginProtocol;
import com.shun.campuswork.dateprotocol.UserDate;
import com.shun.campuswork.domain.UserInfo;
import com.shun.campuswork.tools.ToastUtils;

/**
 * 登入界面
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private Button btn_enter;
    private TextView tv_register;
    private View v_back, v_esc_account, v_esc_pwd;
    private EditText et_pwd, et_phone;
    private String user, pwd;
    private CheckBox cb_remember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViews();
        initListener();
        initUser();
    }

    private void findViews() {
        tv_register = (TextView) findViewById(R.id.tv_register);
        v_back = findViewById(R.id.v_back);
        btn_enter = (Button) findViewById(R.id.btn_enter);
        et_pwd = (EditText) findViewById(R.id.et_pwd);
        et_phone = (EditText) findViewById(R.id.et_phone);
        cb_remember = (CheckBox) findViewById(R.id.cb_remember);
        v_esc_account = findViewById(R.id.v_esc_phone);
        v_esc_pwd = findViewById(R.id.v_esc_pwd);
    }


    private void initUser() {
        user = UserDate.getLoggedUser();
        pwd = UserDate.getUserPwd(user);
        if (!TextUtils.isEmpty(user)) {
            et_phone.setText(user);
            if (!TextUtils.isEmpty(pwd)) {
                et_pwd.setText(pwd);
            }
        }
    }

    private void initListener() {
        tv_register.setOnClickListener(this);
        v_back.setOnClickListener(this);
        btn_enter.setOnClickListener(this);
        cb_remember.setOnClickListener(this);
        v_esc_account.setOnClickListener(this);
        v_esc_pwd.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_register:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;
            case R.id.btn_enter:
                login();
                break;
            case R.id.v_back:
                onBackPressed();
                break;
            case R.id.v_esc_pwd:
                et_pwd.setText("");
                break;
            case R.id.v_esc_phone:
                et_phone.setText("");
                break;
        }
    }

    private void login() {
        user = et_phone.getText().toString();
        pwd = et_pwd.getText().toString();
        LoginProtocol loginProtocol = new LoginProtocol(user, pwd);
        loginProtocol.setOnDateListener(new BaseProtocolNoCache.OnDateListener<UserInfo>() {
            @Override
            public void onSuccess(UserInfo userInfo, String json) {
                ToastUtils.makeText("登入成功");
                UserDate.putLoggedUser(user);
                UserDate.putLoggingUser(user);
                UserDate.putUserInfo(user, json);
                if (cb_remember.isChecked()) {
                    UserDate.putUserPwd(user, pwd);
                } else {
                    UserDate.putUserPwd(user, "");
                }
                onBackPressed();
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                ToastUtils.makeText("登入失败");
            }

        });

    }

}
