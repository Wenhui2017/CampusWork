package com.shun.campuswork.activity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.shun.campuswork.R;
import com.shun.campuswork.domain.UserInfo;
import com.shun.campuswork.netdate.UserDate;
import com.shun.campuswork.tools.SharedPreferencesUtils;

/**
 * Created by shun99 on 2015/12/6.
 * 个人简历界面
 */
public class UserActivity extends BaseActivity implements View.OnClickListener {

    private View v_back;
    EditText et_name, et_user, et_id, et_sex, et_school, et_expe;
    TextView tv_phone;
    Button btn_enter;
    private String enterUser;

    @Override
    public void init() {
        setContentView(R.layout.activity_user);
        initView();
        initListener();
        initDate();
    }

    private void initView() {
        v_back = findViewById(R.id.v_back);
        et_name = (EditText) findViewById(R.id.et_name);
        et_user = (EditText) findViewById(R.id.et_user);
        tv_phone = (TextView) findViewById(R.id.tv_phone);
        et_id = (EditText) findViewById(R.id.et_id);
        et_sex = (EditText) findViewById(R.id.et_sex);
        et_school = (EditText) findViewById(R.id.et_school);
        et_expe = (EditText) findViewById(R.id.et_expe);
        btn_enter = (Button) findViewById(R.id.btn_enter);
    }


    private void initListener() {
        v_back.setOnClickListener(this);
        btn_enter.setOnClickListener(this);
    }


    private void initDate() {
        enterUser = SharedPreferencesUtils.getString(SharedPreferencesUtils.CURRENT_USER);
        String json = SharedPreferencesUtils.getString(enterUser + SharedPreferencesUtils.INFO);
        UserInfo userInfo = praseJson(json);
        if (userInfo != null) {
            et_name.setText(userInfo.name);
            et_user.setText(userInfo.user);
            tv_phone.setText(userInfo.phone);
            et_id.setText(userInfo.id);
            et_expe.setText(userInfo.experice);
            et_sex.setText(userInfo.sex);
            et_school.setText(userInfo.school);
        }
    }

    private void saveDate(){
        UserInfo userInfo = new UserInfo();
        userInfo.name = et_name.getText().toString();
        userInfo.user = et_user.getText().toString();
        userInfo.id = et_id.getText().toString();
        userInfo.experice = et_expe.getText().toString();
        userInfo.sex = et_sex.getText().toString();
        userInfo.phone = tv_phone.getText().toString();
        userInfo.school = et_school.getText().toString();
        userInfo.headUrl = "http://campuswork1.sinaapp.com/head.jpg";
        Gson gson = new Gson();
        String json = gson.toJson(userInfo);
        Log.w("userInfo", "" + json);
        UserDate.putUserInfo(enterUser, json);//更新网络数据
    }

    private UserInfo praseJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, UserInfo.class);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.v_back:
                onBackPressed();
                break;
            case R.id.btn_enter:
                saveDate();
                onBackPressed();
                break;
        }
    }
}
