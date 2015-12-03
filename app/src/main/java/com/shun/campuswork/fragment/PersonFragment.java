package com.shun.campuswork.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;


import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.shun.campuswork.R;


/**
 * 个人资料的fragment--单例模式
 * Created by shun99 on 2015/11/19.
 */
public class PersonFragment extends Fragment{
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(getContext(), R.layout.layout_person, null);
        ViewUtils.inject(this, view);
        return view;
    }
}
