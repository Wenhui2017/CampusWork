package com.shun.campuswork.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.shun.campuswork.R;
import com.shun.campuswork.tools.SharedPreferencesUtils;
import com.shun.campuswork.tools.ToastUtils;

import cn.smssdk.SMSSDK;

/**
 * Created by shun99 on 2015/12/6.
 */
public class EnterActivity extends BaseActivity implements View.OnClickListener {

    private Button btn_enter;
    private TextView tv_register;
    private View v_back;
    private EditText et_pwd, et_phone;
    private String phString, pwdString;
    private CheckBox cb_remember;

    @Override
    public void init() {
        setContentView(R.layout.activity_enter);
        tv_register = (TextView) findViewById(R.id.tv_register);
        v_back = findViewById(R.id.v_back);
        btn_enter = (Button) findViewById(R.id.btn_enter);
        et_pwd = (EditText) findViewById(R.id.et_pwd);
        et_phone = (EditText) findViewById(R.id.et_phone);
        cb_remember = (CheckBox) findViewById(R.id.cb_remember);
        initListener();
        initUser();
    }

    private void initUser() {
        phString = SharedPreferencesUtils.getString(SharedPreferencesUtils.USED_USER);
        pwdString = SharedPreferencesUtils.getString(SharedPreferencesUtils.USED_USER + phString);
        if (!TextUtils.isEmpty(phString)) {
            et_phone.setText(phString);
            if (!TextUtils.isEmpty(pwdString)) {
                et_pwd.setText(pwdString);
            }
        }
    }

    private void initListener() {
        tv_register.setOnClickListener(this);
        v_back.setOnClickListener(this);
        btn_enter.setOnClickListener(this);
        cb_remember.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_register:
                startActivity(new Intent(EnterActivity.this, RegisterActivity.class));
                break;
            case R.id.btn_enter:
                phString = et_phone.getText().toString();
                pwdString = et_pwd.getText().toString();
                String pwd = SharedPreferencesUtils.getString(phString);
                if (!TextUtils.isEmpty(phString)) {
                    if (!TextUtils.isEmpty(pwdString)) {
                        if (pwd.isEmpty()) {
                            ToastUtils.makeText("该用户未注册");
                        } else if (!pwd.equals(pwdString)) {
                            ToastUtils.makeText("密码错误");
                        } else {
                            SharedPreferencesUtils.putString(SharedPreferencesUtils.CURRENT_USER, phString);//登入帐号
                            SharedPreferencesUtils.putString(SharedPreferencesUtils.USED_USER, phString);//使用过的帐号
                            if(cb_remember.isChecked()) {
                                SharedPreferencesUtils.putString(SharedPreferencesUtils.USED_USER + phString, pwdString);//保存登入帐号的密码
                            }else {
                                SharedPreferencesUtils.putString(SharedPreferencesUtils.USED_USER + phString, "");//不保存登入帐号的密码
                            }
                            onBackPressed();
                        }
                    } else {
                        ToastUtils.makeText("密码不能为空");
                    }
                } else {
                    ToastUtils.makeText("用户不能为空");
                }
                break;
            case R.id.v_back:
                onBackPressed();
                break;
        }
    }
}
