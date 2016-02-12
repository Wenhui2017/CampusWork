package com.shun.campuswork.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.shun.campuswork.R;
import com.shun.campuswork.netdate.UserDate;
import com.shun.campuswork.tools.SharedPreferencesUtils;
import com.shun.campuswork.tools.ToastUtils;


import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;


/**
 * Created by shun99 on 2015/12/6.
 */
public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    private View v_back;
    private EditText et_phone, et_code, et_pwd;
    private TextView tv_get_code, tv_register;
    private Button btn_register;
    private String phoneString, pwdString;


    private static String APPKEY = "cb56f9c4c938";
    private static String APPSECRET = "58bbcd02cee0a2d2ff1f0566bbbf2c77";

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int event = msg.arg1;
            int result = msg.arg2;
            Object data = msg.obj;
            Log.e("event", "event=" + event);
            Log.e("result", "result=" + result);
            if (result == SMSSDK.RESULT_COMPLETE) {
                //短信注册成功后，返回MainActivity,然后提示新好友
                if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {//提交验证码成功
                    if (result == SMSSDK.RESULT_COMPLETE) {//-1
                        boolean smart = (Boolean) data;
                        if (smart) {
                            //通过智能验证
                            ToastUtils.makeText("该手机号已被注册");
                        } else {
                            //依然走短信验证
                            ToastUtils.makeText("短信已发送");
                        }
                    }
                } else if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {//校验验证码成功
                    ToastUtils.makeText("通过验证,跳转到登入界面");
                    UserDate.setUserPwd(phoneString, pwdString);//保存注册成功的信息
                    Intent intent = new Intent(RegisterActivity.this, EnterActivity.class);
                    startActivity(intent);
                }
            } else {
                ToastUtils.makeText("注册失败");
            }
        }
    };

    @Override
    public void init() {
        setContentView(R.layout.activity_register);
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_code = (EditText) findViewById(R.id.et_code);
        et_pwd = (EditText) findViewById(R.id.et_pwd);
        v_back = findViewById(R.id.v_back);
        btn_register = (Button) findViewById(R.id.btn_register);
        tv_get_code = (TextView) findViewById(R.id.tv_get_code);
        tv_register = (TextView) findViewById(R.id.tv_register);
        initListener();
        initSms();
    }


    private void initSms() {
        SMSSDK.initSDK(this, APPKEY, APPSECRET);
        EventHandler eh = new EventHandler() {
            @Override
            public void afterEvent(int event, int result, Object data) {
                Message msg = new Message();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                handler.sendMessage(msg);
            }

        };
        SMSSDK.registerEventHandler(eh);
    }

    private void initListener() {
        tv_get_code.setOnClickListener(this);
        btn_register.setOnClickListener(this);
        tv_register.setOnClickListener(this);
        v_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_get_code://
                if (!TextUtils.isEmpty(et_phone.getText().toString())) {
                    SMSSDK.getVerificationCode("86", et_phone.getText().toString());
                } else {
                    ToastUtils.makeText("电话不能为空");
                }
                break;
            case R.id.btn_register://校验验证码,注册
                if (!TextUtils.isEmpty(et_phone.getText().toString())) {
                    if (TextUtils.isEmpty(et_code.getText().toString())) {
                        ToastUtils.makeText("验证码不能为空");
                        return;
                    }
                    if (TextUtils.isEmpty(et_pwd.getText().toString())) {
                        ToastUtils.makeText("密码不能为空");
                        return;
                    }
                    pwdString = et_pwd.getText().toString();
                    phoneString = et_phone.getText().toString();
                    SMSSDK.submitVerificationCode("86", phoneString, et_code.getText().toString());
                } else {
                    ToastUtils.makeText("电话不能为空");
                }
                break;
            case R.id.v_back:
                onBackPressed();
                break;
            case R.id.tv_register://注册协议
                startActivity(new Intent(RegisterActivity.this, ProtocolActivity.class));
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterAllEventHandler();
    }

}
