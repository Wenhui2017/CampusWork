package com.shun.campuswork.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;


import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.shun.campuswork.R;
import com.shun.campuswork.activity.EnterActivity;


/**
 * 个人资料的fragment--单例模式
 * Created by shun99 on 2015/11/19.
 */
public class PersonFragment extends Fragment implements View.OnClickListener{
    private static PersonFragment instance = null;

    @ViewInject(R.id.rl_login)
    RelativeLayout rl_login;

    public static PersonFragment getInstance() {
        if (instance == null) {
            instance = new PersonFragment();
        }
        return instance;
    }

    private PersonFragment() {    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(getContext(), R.layout.layout_person, null);
        ViewUtils.inject(this, view);
        initListener();
        return view;
    }

    private void initListener() {
        rl_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_login:
                startActivity(new Intent(getActivity(), EnterActivity.class));
                break;
        }

    }
}
