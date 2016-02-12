package com.shun.campuswork.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.shun.campuswork.R;
import com.shun.campuswork.activity.SignUpActivity;
import com.shun.campuswork.activity.StarActivity;
import com.shun.campuswork.activity.UserActivity;
import com.shun.campuswork.domain.UserInfo;
import com.shun.campuswork.tools.SharedPreferencesUtils;
import com.shun.campuswork.tools.ToastUtils;
import com.shun.campuswork.tools.UiUtils;
import com.shun.campuswork.view.TextImageView;


/**
 * 个人资料的fragment--单例模式
 * Created by shun99 on 2015/11/19.
 */
public class PersonFragment extends Fragment implements View.OnClickListener {
    private static PersonFragment instance = null;

    private ImageView iv_person_head;
    private TextView tv_user;
    private TextView tv_des, tv_des_info, tv_update_pwd;
    private TextImageView tiv_collect, tiv_more, tiv_feedback, tiv_auth;
    private TextView tv_history, tv_complete, tv_sign_up, tv_follower;

    public static PersonFragment getInstance() {
        if (instance == null) {
            instance = new PersonFragment();
        }
        return instance;
    }

    private PersonFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(getContext(), R.layout.layout_person, null);
        iv_person_head = (ImageView) view.findViewById(R.id.iv_person_head);
        tv_user = (TextView) view.findViewById(R.id.tv_user);
        tv_des = (TextView) view.findViewById(R.id.tv_des);
        tv_des_info = (TextView) view.findViewById(R.id.tv_des_info);
        tv_update_pwd = (TextView) view.findViewById(R.id.tv_update_pwd);
        tiv_collect = (TextImageView) view.findViewById(R.id.tiv_collect);
        tiv_auth = (TextImageView) view.findViewById(R.id.tiv_auth);
        tiv_feedback = (TextImageView) view.findViewById(R.id.tiv_feedback);
        tiv_more = (TextImageView) view.findViewById(R.id.tiv_more);
        tv_history = (TextView) view.findViewById(R.id.tv_history);
        tv_sign_up = (TextView) view.findViewById(R.id.tv_sign_up);
        tv_complete = (TextView) view.findViewById(R.id.tv_complete);
        tv_follower = (TextView) view.findViewById(R.id.tv_follower);
        initListener();
        return view;
    }


    private void initListener() {
        tv_des_info.setOnClickListener(this);
        tv_update_pwd.setOnClickListener(this);
        tiv_collect.setOnClickListener(this);
        tiv_auth.setOnClickListener(this);
        tiv_feedback.setOnClickListener(this);
        tv_history.setOnClickListener(this);
        tv_complete.setOnClickListener(this);
        tv_sign_up.setOnClickListener(this);
        tv_follower.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_des_info:
                startActivity(new Intent(getActivity(), UserActivity.class));
                break;
            case R.id.tiv_feedback:
                startActivity(new Intent(getActivity(), UserActivity.class));
                break;
            case R.id.tv_complete:
                ToastUtils.makeText("完成");
                break;
            case R.id.tv_follower:
                ToastUtils.makeText("录用");
                break;
            case R.id.tv_history:
                ToastUtils.makeText("历史");
                break;
            case R.id.tv_sign_up:
                startActivity(new Intent(getActivity(), SignUpActivity.class));
                break;
            case R.id.tiv_collect:
                startActivity(new Intent(getActivity(), StarActivity.class));
                break;
        }

    }

    private void logout() {

    }

    @Override
    public void onStart() {
        super.onStart();
        String enterUser = SharedPreferencesUtils.getString(SharedPreferencesUtils.CURRENT_USER);
        if (!enterUser.isEmpty()) {
            String json = SharedPreferencesUtils.getString(enterUser + "_info");
            UserInfo userInfo = praseJson(json);
            initUser(userInfo);
        }
    }

    private void initUser(UserInfo userInfo) {
        BitmapUtils bitmapUtils = new BitmapUtils(UiUtils.getContext());
        bitmapUtils.display(iv_person_head, userInfo.headUrl);
        tv_user.setText(userInfo.user);
        tv_des.setText(userInfo.sign);
    }

    private UserInfo praseJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, UserInfo.class);
    }
}
