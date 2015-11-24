package com.shun.campuswork.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.shun.campuswork.R;
import com.shun.campuswork.adapter.HomeAdapter;
import com.shun.campuswork.domain.JobInfo;

import java.util.ArrayList;

/**
 * 主页的fragment--单例模式
 * Created by shun99 on 2015/11/19.
 */
public class HomeFragment extends Fragment {
    private static HomeFragment instance = null;
    private ListView lv_home;
    private ArrayList<JobInfo> mJobInfoList;
    private SwipeRefreshLayout swipeRefreshLayout;

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
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.home_srl);
        swipeRefreshLayout.setColorSchemeResources(R.color.secondary_color, R.color.primary_color, R.color.next_product_title_color, R.color.next_product_count_bg);
        lv_home = (ListView) view.findViewById(R.id.lv_home);
        lv_home.setAdapter(new HomeAdapter(true, false, lv_home, mJobInfoList));
        return view;
    }

    private void setListener() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

            }
        });
    }

    private void initDate() {
        mJobInfoList = new ArrayList<JobInfo>();
        for (int i = 0; i < 20; i++) {
            mJobInfoList.add(new JobInfo("第." + i + ".个"));
        }
    }
}
