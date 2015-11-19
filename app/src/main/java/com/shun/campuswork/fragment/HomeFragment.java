package com.shun.campuswork.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * 主页的fragment--单例模式
 * Created by shun99 on 2015/11/19.
 */
public class HomeFragment extends Fragment {
    private static HomeFragment instance = null;

    public static HomeFragment getInstance() {
        if (instance == null){
            instance = new HomeFragment();
        }
        return instance;
    }

    private HomeFragment(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        TextView textView = new TextView(getActivity());
        textView.setText("这是主页");
        return textView;
    }
}
