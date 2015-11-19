package com.shun.campuswork.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * 最新招聘信息的fragment--单例模式
 * Created by shun99 on 2015/11/19.
 */
public class NewsFragment extends Fragment {
    private static NewsFragment instance = null;

    public static NewsFragment getInstance() {
        if (instance == null){
            instance = new NewsFragment();
        }
        return instance;
    }

    private NewsFragment(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        TextView textView = new TextView(getActivity());
        textView.setText("这是最新招聘信息");
        return textView;
    }
}
