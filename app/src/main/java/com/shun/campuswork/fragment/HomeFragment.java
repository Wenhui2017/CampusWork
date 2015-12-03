package com.shun.campuswork.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.shun.campuswork.R;
import com.shun.campuswork.adapter.HomeAdapter;
import com.shun.campuswork.dateprotocol.BaseProtocol;
import com.shun.campuswork.dateprotocol.HomeDateProtocol;
import com.shun.campuswork.domain.JobInfo;
import com.shun.campuswork.global.GlobalContants;
import com.shun.campuswork.tools.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 主页的fragment--单例模式
 * Created by shun99 on 2015/11/19.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {
    private static HomeFragment instance = null;
    public static Activity activity = null;
    private List<JobInfo> mJobInfoList;

    @ViewInject(R.id.lv_home)
    private ListView lv_home;
    @ViewInject(R.id.home_srl)
    private SwipeRefreshLayout swipeRefreshLayout;
    @ViewInject(R.id.new_ll_error)
    private LinearLayout new_ll_error;

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
        View view = View.inflate(getContext(), R.layout.layout_home, null);
        if(activity == null){
            activity = getActivity();
        }
        ViewUtils.inject(this, view);
        swipeRefreshLayout.setColorSchemeResources(GlobalContants.refreshColor);
        initDate();
        initListener();
        return view;
    }

    private void initListener() {
        lv_home.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0 || position == 1){

                }
                ToastUtils.makeText("pos"+position);
            }
        });
        new_ll_error.setOnClickListener(this);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

            }
        });
    }


    private void initDate() {
        HomeDateProtocol homeDateProtocol = new HomeDateProtocol(1);
        homeDateProtocol.setOnDateListener(new BaseProtocol.OnDateListener<List<JobInfo>>() {
            @Override
            public void onRefresh(List<JobInfo> jobInfoList) {
                if (jobInfoList == null) {
                    mJobInfoList = new ArrayList<JobInfo>();
                    new_ll_error.setVisibility(View.VISIBLE);
                    createSuccessView();
                } else {
                    mJobInfoList = jobInfoList;
                    new_ll_error.setVisibility(View.GONE);
                    createSuccessView();
                }
            }
        });
    }

    private void createSuccessView() {
        lv_home.setAdapter(new HomeAdapter(lv_home, mJobInfoList));
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.new_ll_error:
                initDate();
                break;
        }
    }
}
