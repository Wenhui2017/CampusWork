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
import com.shun.campuswork.tools.ColorUtils;
import com.shun.campuswork.tools.UiUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shun99 on 2015/11/19.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {
    private static HomeFragment instance = null;

    @ViewInject(R.id.lv_home)
    private ListView lv_home;
    @ViewInject(R.id.home_srl)
    private SwipeRefreshLayout swipeRefreshLayout;
    @ViewInject(R.id.new_ll_error)
    private LinearLayout new_ll_error;
    private HomeAdapter mHomeAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (instance == null) {
            instance = this;
        }
        View view = UiUtils.inflate(R.layout.layout_home);
        ViewUtils.inject(this, view);
        initUI();
        initDate();
        initListener();
        return view;
    }

    private void initUI() {
        List<JobInfo> mJobInfoList;mJobInfoList = new ArrayList<>();
        mHomeAdapter = new HomeAdapter(lv_home, mJobInfoList);
        swipeRefreshLayout.setColorSchemeColors(ColorUtils.refreshColors);
        lv_home.setAdapter(mHomeAdapter);
    }

    private void initDate() {
        setLoadingState();
        HomeDateProtocol homeDateProtocol = new HomeDateProtocol(1);
        homeDateProtocol.setOnDateListener(new BaseProtocol.OnDateListener<List<JobInfo>>() {
            @Override
            public void onRefresh(List<JobInfo> jobInfoList) {
                if (jobInfoList == null) {
                    setLoadFailedState();
                } else {
                    mHomeAdapter.mDateList.clear();
                    mHomeAdapter.mDateList.addAll(jobInfoList);
                    setLoadSuccessState/**/();
                }
            }
        });
    }

    private void initListener() {
        lv_home.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position > 1) {
                    clickItem(position);
                }
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initDate();
            }
        });
        new_ll_error.setOnClickListener(this);
    }

    /**
     * 点击news_lv_content的条目时
     *
     * @param position
     */
    private void clickItem(int position) {
        Intent intent = new Intent(getActivity(), JobActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("jobInfo", mHomeAdapter.mDateList.get(position - 2));
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void setLoadingState() {
        swipeRefreshLayout.setVisibility(View.VISIBLE);
        swipeRefreshLayout.setRefreshing(true);
        new_ll_error.setVisibility(View.GONE);
    }

    private void setLoadSuccessState() {
        swipeRefreshLayout.setRefreshing(false);
        mHomeAdapter.notifyDataSetChanged();
        new_ll_error.setVisibility(View.GONE);
    }

    private void setLoadFailedState() {
        swipeRefreshLayout.setRefreshing(false);
        mHomeAdapter.notifyDataSetChanged();
        new_ll_error.setVisibility(View.VISIBLE);
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
