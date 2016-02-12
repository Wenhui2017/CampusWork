package com.shun.campuswork.fragment;

import android.content.Intent;
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
import com.shun.campuswork.activity.JobActivity;
import com.shun.campuswork.adapter.HomeAdapter;
import com.shun.campuswork.dateprotocol.BaseProtocol;
import com.shun.campuswork.dateprotocol.HomeDateProtocol;
import com.shun.campuswork.domain.JobInfo;
import com.shun.campuswork.global.GlobalContants;
import com.shun.campuswork.tools.ToastUtils;
import com.shun.campuswork.tools.UiUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 主页的fragment--单例模式
 * Created by shun99 on 2015/11/19.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {
    private static HomeFragment instance = null;
    private List<JobInfo> mJobInfoList;

    @ViewInject(R.id.lv_home)
    private ListView lv_home;
    @ViewInject(R.id.home_srl)
    private SwipeRefreshLayout swipeRefreshLayout;
    @ViewInject(R.id.new_ll_error)
    private LinearLayout new_ll_error;
    private HomeAdapter mHomeAdapter;

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
        View view = UiUtils.inflate(R.layout.layout_home);
        ViewUtils.inject(this, view);
        swipeRefreshLayout.setColorSchemeResources(GlobalContants.refreshColor);
        //初始化界面
        initUI();
        //加载数据，更新界面
        initDate();
        initListener();
        return view;
    }

    private void initUI() {
        mJobInfoList = new ArrayList<JobInfo>();
        mHomeAdapter = new HomeAdapter(lv_home, mJobInfoList);
        lv_home.setAdapter(mHomeAdapter);
    }

    private void initListener() {
        lv_home.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position > 1) {
                    clickItem(position-2);
                }
            }
        });
        new_ll_error.setOnClickListener(this);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

            }
        });
    }

    /**
     * 点击news_lv_content的条目时
     *
     * @param position
     */
    private void clickItem(int position) {
        Intent intent = new Intent(getActivity(), JobActivity.class);
        intent.putExtra("position", position);
        intent.putExtra("flag", 0);
        startActivity(intent);
    }


    private void initDate() {
        HomeDateProtocol homeDateProtocol = new HomeDateProtocol(1);
        homeDateProtocol.setOnDateListener(new BaseProtocol.OnDateListener<List<JobInfo>>() {
            @Override
            public void onRefresh(List<JobInfo> jobInfoList) {
                if (jobInfoList == null) {
                    createErrorView();
                } else {
                    //mJobInfoList = jobInfoList;
                    mHomeAdapter.mDateList = jobInfoList;
                    createSuccessView();
                }
            }
        });
    }

    private void createSuccessView() {
        mHomeAdapter.notifyDataSetChanged();
        new_ll_error.setVisibility(View.GONE);
    }

    private void createErrorView() {
        mHomeAdapter.notifyDataSetChanged();
        new_ll_error.setVisibility(View.VISIBLE);
    }

    public JobInfo getJonInfoForPosition(int position) {
        return  mHomeAdapter.mDateList.get(position);
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
