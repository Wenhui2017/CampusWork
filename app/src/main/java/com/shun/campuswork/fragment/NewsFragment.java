package com.shun.campuswork.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.shun.campuswork.R;
import com.shun.campuswork.activity.JobActivity;
import com.shun.campuswork.adapter.NewsContentAdapter;
import com.shun.campuswork.dateprotocol.BaseProtocol;
import com.shun.campuswork.dateprotocol.HomeDateProtocol;
import com.shun.campuswork.dateprotocol.NewsDateProtocol;
import com.shun.campuswork.domain.JobInfo;
import com.shun.campuswork.global.GlobalContants;
import com.shun.campuswork.holder.NewsHolder;
import com.shun.campuswork.tools.ColorUtils;
import com.shun.campuswork.tools.LogUtils;
import com.shun.campuswork.tools.ToastUtils;
import com.shun.campuswork.tools.UiUtils;
import com.shun.campuswork.view.MyListView;
import com.shun.campuswork.view.MySwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;


/**
 * 最新招聘信息
 */
public class NewsFragment extends Fragment implements View.OnClickListener {
    public static NewsFragment instance = null;

    private NewsContentAdapter mNewsContentAdapter;
    private List<JobInfo> mJobInfoListType;//保存有一份加载成功是的数据，用来方便筛选
    private List<JobInfo> mJobInfoList;
    private StringBuffer mFlagTime = new StringBuffer("a");
    private StringBuffer mFlagType = new StringBuffer("a");
    private int moreDatePos = 1;
    public static Handler mHandler = new Handler();

    @ViewInject(R.id.news_tv_time)
    private TextView news_tv_time;
    @ViewInject(R.id.news_tv_type)
    private TextView news_tv_type;
    @ViewInject(R.id.news_tv_error)
    private TextView news_tv_error;
    @ViewInject(R.id.new_ll_error)
    LinearLayout new_ll_error;
    @ViewInject(R.id.news_ll_content)
    LinearLayout news_ll_content;
    @ViewInject(R.id.news_lv_content)
    MyListView news_lv_content;
    @ViewInject(R.id.news_srl)
    private MySwipeRefreshLayout swipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (instance == null) {
            instance = this;
        }
        View view = UiUtils.inflate(R.layout.layout_news);
        ViewUtils.inject(this, view);
        initUI();
        initDate();
        initListener();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        String tempTime = getArguments().getString(GlobalContants.FLAG_TIME, "a");
        String tempType = getArguments().getString(GlobalContants.FLAG_TYPE, "a");
        chooseWork(tempTime, tempType);
    }

    private void initUI() {
        mJobInfoList = new ArrayList<>();
        mNewsContentAdapter = new NewsContentAdapter(mJobInfoList, news_lv_content, mHandler) {
            @Override
            public void LoadMoreDate() {
                NewsFragment.this.LoadMoreDate();
            }

            @Override
            public void setLoadSuccessState() {
                NewsFragment.this.setLoadSuccessState();
            }

            @Override
            public void clickItem(int position) {
                NewsFragment.this.clickItem(position);
            }
        };

        news_lv_content.setAdapter(mNewsContentAdapter);
        swipeRefreshLayout.setColorSchemeColors(ColorUtils.refreshColors);
    }

    private void initDate() {
        setLoadingState();
        moreDatePos = 1;
        NewsDateProtocol newsDateProtocol = new NewsDateProtocol(1);
        newsDateProtocol.setOnDateListener(new BaseProtocol.OnDateListener<List<JobInfo>>() {
            @Override
            public void onRefresh(List<JobInfo> jobInfoList) {
                if (jobInfoList != null) {
                    mNewsContentAdapter.mDateList.clear();
                    mNewsContentAdapter.mDateList.addAll(jobInfoList);
                    setLoadSuccessState();
                } else {
                    setLoadFailedState();
                }
            }
        });
    }

    private void LoadMoreDate() {
        NewsDateProtocol newsDateProtocol = new NewsDateProtocol(moreDatePos);
        newsDateProtocol.setOnDateListener(new BaseProtocol.OnDateListener<List<JobInfo>>() {
            @Override
            public void onRefresh(List<JobInfo> jobInfoList) {
                mNewsContentAdapter.refreshLoadMore(jobInfoList);
                moreDatePos++;
            }
        });
    }

    private void initListener() {
        news_tv_time.setOnClickListener(this);
        news_tv_type.setOnClickListener(this);
        news_tv_error.setOnClickListener(this);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initDate();
                mNewsContentAdapter.addFooterView();
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
        Bundle bundle = new Bundle();
        bundle.putSerializable("jobInfo", mJobInfoList.get(position));
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.news_tv_time:
                alertTime();
                break;
            case R.id.news_tv_type:
                alertType();
                break;
            case R.id.news_tv_error:
                initDate();
                break;
        }
    }

    private String workTimeBoolean2String(boolean b) {
        if (!b)
            return "0";
        return "1";
    }

    /*根据兼职时间和类型判断*/
    public void chooseWork(String flagTime, String flagType) {
        if (flagTime.contains("a") && flagType.contains("a")) {
            mNewsContentAdapter.mDateList = mJobInfoList;
            setLoadSuccessState();
        } else {
            if (mJobInfoListType == null) {
                mJobInfoListType = new ArrayList<>();
            } else {
                mJobInfoListType.clear();
            }
            for (int i = 0; i < mJobInfoList.size(); i++) {
                if (flagTime.contains("a") || flagTime.contains(workTimeBoolean2String(mJobInfoList.get(i).isLongWork))) {
                    if (flagType.contains("a") || flagType.contains("" + mJobInfoList.get(i).type)) {
                        mJobInfoListType.add(mJobInfoList.get(i));
                    }
                }
            }
            if (mJobInfoListType.size() == 0) {
                setLoadFailedState();
            } else {
                mNewsContentAdapter.mDateList = mJobInfoListType;
                setLoadSuccessState();
            }
        }
    }

    /*弹窗*/
    public void alertTime() {
        // 创建一个 Builder 对象
        AlertDialog.Builder bulider = new AlertDialog.Builder(getContext());
        View view = UiUtils.inflate(R.layout.layout_dialog_time);
        final AlertDialog dialer = bulider.create();
        dialer.setView(view, 0, 0, 0, 0);
        final CheckBox cb_all = (CheckBox) view.findViewById(R.id.cb_all);
        final CheckBox cb_1 = (CheckBox) view.findViewById(R.id.cb_1);
        final CheckBox cb_2 = (CheckBox) view.findViewById(R.id.cb_2);
        Button time_btn_enter = (Button) view.findViewById(R.id.time_btn_enter);
        time_btn_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp = mFlagTime.toString();
                mFlagTime.setLength(0);
                if (cb_1.isChecked()) {
                    mFlagTime.append("0");
                }
                if (cb_2.isChecked()) {
                    mFlagTime.append("1");
                }
                if (cb_all.isChecked()) {
                    mFlagTime.append("a");
                }
                //如果什么都没选中，恢复原来的
                if (TextUtils.isEmpty(mFlagTime.toString())) {
                    mFlagTime.append(temp);
                }

                chooseWork(mFlagTime.toString(), mFlagType.toString());
                dialer.dismiss();
            }
        });
        dialer.show();
    }

    public void alertType() {
        // 创建一个 Builder 对象
        AlertDialog.Builder bulider = new AlertDialog.Builder(getContext());
        View view = UiUtils.inflate(R.layout.layout_dialog_type);
        final AlertDialog dialer = bulider.create();
        dialer.setView(view, 0, 0, 0, 0);
        final CheckBox cb_all = (CheckBox) view.findViewById(R.id.cb_all);
        final CheckBox cb_1 = (CheckBox) view.findViewById(R.id.cb_1);
        final CheckBox cb_2 = (CheckBox) view.findViewById(R.id.cb_2);
        final CheckBox cb_3 = (CheckBox) view.findViewById(R.id.cb_3);
        final CheckBox cb_4 = (CheckBox) view.findViewById(R.id.cb_4);
        final CheckBox cb_5 = (CheckBox) view.findViewById(R.id.cb_5);
        final CheckBox cb_6 = (CheckBox) view.findViewById(R.id.cb_6);
        final CheckBox cb_7 = (CheckBox) view.findViewById(R.id.cb_7);
        Button type_btn_enter = (Button) view.findViewById(R.id.type_btn_enter);
        type_btn_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp = mFlagTime.toString();
                mFlagType.setLength(0);
                if (cb_all.isChecked()) {
                    mFlagType.append("a");
                }
                if (cb_1.isChecked()) {
                    mFlagType.append("0");
                }
                if (cb_2.isChecked()) {
                    mFlagType.append("1");
                }
                if (cb_3.isChecked()) {
                    mFlagType.append("2");
                }
                if (cb_4.isChecked()) {
                    mFlagType.append("3");
                }
                if (cb_5.isChecked()) {
                    mFlagType.append("4");
                }
                if (cb_6.isChecked()) {
                    mFlagType.append("5");
                }
                if (cb_7.isChecked()) {
                    mFlagType.append("6");
                }
                if (TextUtils.isEmpty(mFlagTime.toString())) {
                    mFlagTime.append(temp);
                }
                chooseWork(mFlagTime.toString(), mFlagType.toString());
                dialer.dismiss();
            }
        });
        dialer.show();
    }

    private void setLoadingState() {
        swipeRefreshLayout.setVisibility(View.VISIBLE);
        swipeRefreshLayout.setRefreshing(true);
        new_ll_error.setVisibility(View.GONE);
    }

    private void setLoadSuccessState() {
        new_ll_error.setVisibility(View.GONE);
        news_ll_content.setVisibility(View.VISIBLE);
        mNewsContentAdapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
    }

    private void setLoadFailedState() {
        new_ll_error.setVisibility(View.VISIBLE);
        news_ll_content.setVisibility(View.GONE);
        mNewsContentAdapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
    }
}
