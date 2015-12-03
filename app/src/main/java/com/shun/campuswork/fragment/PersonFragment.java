package com.shun.campuswork.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.shun.campuswork.R;
import com.shun.campuswork.global.GlobalContants;
import com.shun.campuswork.tools.UiUtils;

import java.util.HashMap;
import java.util.Random;


import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.CommonDialog;
import cn.smssdk.gui.RegisterPage;

/**
 * 个人资料的fragment--单例模式
 * Created by shun99 on 2015/11/19.
 */
public class PersonFragment extends Fragment implements View.OnClickListener ,Handler.Callback {
    private static PersonFragment instance = null;
    @ViewInject(R.id.rl_login)
    RelativeLayout rl_login;

    public static PersonFragment getInstance() {
        if (instance == null) {
            instance = new PersonFragment();
        }
        return instance;
    }

    private PersonFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(getContext(), R.layout.layout_person, null);
        ViewUtils.inject(this, view);
        rl_login.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_login:
                initRegister();
        }
    }

    private boolean ready;

    private void initRegister() {
        SMSSDK.initSDK(UiUtils.getContext(), "cb56f9c4c938", "58bbcd02cee0a2d2ff1f0566bbbf2c77", true);
        final Handler handler = new Handler(this);
        //回调
        EventHandler eventHandler = new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                Message msg = new Message();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                handler.sendMessage(msg);
            }
        };
        // 注册回调监听接口
        SMSSDK.registerEventHandler(eventHandler);
        ready = true;
        // 获取新好友个数
        showDialog();
        SMSSDK.getNewFriendsCount();
        openRegister();
    }

    private void openRegister(){
        //打开注册页面
        RegisterPage registerPage = new RegisterPage();
        registerPage.setRegisterCallback(new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                // 解析注册结果,如过成功
                if (result == SMSSDK.RESULT_COMPLETE) {
                    @SuppressWarnings("unchecked")
                    HashMap<String, Object> phoneMap = (HashMap<String, Object>) data;
                    String country = (String) phoneMap.get("country");
                    String phone = (String) phoneMap.get("phone");
                    String uid = (String) phoneMap.get("uid");
                    Log.w("输出","uid"+uid+"phone"+phone);
                    Log.w("输出","phoneMap"+phoneMap);
                    // 提交用户信息
                    registerUser(country, phone);
                    //registerUser(uid, nickName, avatar, country, phone);
                }
            }
        });
        registerPage.show(UiUtils.getContext());
    }

    private void showDialog() {
    }

    // 提交用户信息
    private void registerUser(String country, String phone) {
        Log.w("标记","....1.....");
        Random rnd = new Random();
        int id = Math.abs(rnd.nextInt());
        String uid = String.valueOf(id);
        String nickName = "SmsSDK_User_" + uid;
        String avatar = GlobalContants.getAvatars(id % 12);//头像
        SMSSDK.submitUserInfo(uid, nickName, avatar, country, phone);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (ready) {
            // 销毁回调监听接口
            SMSSDK.unregisterAllEventHandler();
        }
        super.onDestroy();
    }

    /**
     * 回调
     * @param msg
     * @return
     */
    @Override
    public boolean handleMessage(Message msg) {
        return false;
    }
}
