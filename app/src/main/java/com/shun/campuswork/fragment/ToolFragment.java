package com.shun.campuswork.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * 实用工具的fragment--单例模式
 * Created by shun99 on 2015/11/19.
 */
public class ToolFragment extends Fragment {
    private static ToolFragment instance = null;

    public static ToolFragment getInstance() {
        if (instance == null){
            instance = new ToolFragment();
        }
        return instance;
    }

    private ToolFragment(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        TextView textView = new TextView(getActivity());
        textView.setText("这是实用工具");
        return textView;
    }
}
