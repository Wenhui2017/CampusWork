package com.shun.campuswork.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.shun.campuswork.R;
import com.shun.campuswork.adapter.HomeAdapter;
import com.shun.campuswork.adapter.MyBaseAdapter;
import com.shun.campuswork.domain.JobInfo;
import com.shun.campuswork.holder.BaseHolder;
import com.shun.campuswork.tools.UiUtils;
import com.shun.campuswork.view.HomeHeaderViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * 主页的fragment--单例模式
 * Created by shun99 on 2015/11/19.
 */
public class HomeFragment extends Fragment {
    private static HomeFragment instance = null;
    private ListView lv_home;
    private ArrayList<JobInfo> mJobInfoList;

    public static HomeFragment getInstance() {
        if (instance == null) {
            instance = new HomeFragment();
        }
        return instance;
    }

    private HomeFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        initDate();
        View view = View.inflate(getContext(), R.layout.layout_home, null);
        lv_home = (ListView) view.findViewById(R.id.lv_home);
        lv_home.setAdapter(new HomeAdapter(true, false, lv_home, mJobInfoList));
        return view;
    }

    private void initDate() {
        mJobInfoList = new ArrayList<JobInfo>();
        for (int i = 0; i < 20; i++) {
            mJobInfoList.add(new JobInfo("第."+i+".个"));
        }
    }
}
