package com.shun.campuswork.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.shun.campuswork.dateprotocol.BaseProtocol;
import com.shun.campuswork.dateprotocol.NewsDateProtocol;
import com.shun.campuswork.domain.JobInfo;
import com.shun.campuswork.holder.NewsHolder;
import com.shun.campuswork.tools.ToastUtils;
import com.shun.campuswork.tools.UiUtils;
import com.shun.campuswork.view.MyListView;

import java.util.ArrayList;
import java.util.List;


/**
 * 最新招聘信息的fragment--单例模式
 * Created by shun99 on 2015/11/19.
 */
public class NewsFragment extends Fragment implements View.OnClickListener {
    public static NewsFragment instance = null;
    private NewsContentAdapter mNewsContentAdapter;
    private List<JobInfo> mJobInfoListType;
    private List<JobInfo> mJobInfoList;//保存有一份加载成功是的数据，用来方便筛选
    private StringBuffer mFlagTime = new StringBuffer("a");
    private StringBuffer mFlagType = new StringBuffer("a");


    public static NewsFragment getInstance() {
        if (instance == null) {
            Log.w("NewsFragment", "NewsFragment");
            instance = new NewsFragment();
        }
        return instance;
    }

    private NewsFragment() {
    }

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = UiUtils.inflate(R.layout.layout_news);
        ViewUtils.inject(this, view);
        initUI();
        initDate();
        initListener();
        return view;
    }

    private void initUI() {
        mJobInfoList = new ArrayList<JobInfo>();
        mNewsContentAdapter = new NewsContentAdapter(mJobInfoList, news_lv_content);
        news_lv_content.setAdapter(mNewsContentAdapter);
    }

    private void initDate() {
        NewsDateProtocol newsDateProtocol = new NewsDateProtocol(1);
        newsDateProtocol.setOnDateListener(new BaseProtocol.OnDateListener<List<JobInfo>>() {
            @Override
            public void onRefresh(List<JobInfo> jobInfoList) {
                if (jobInfoList != null) {
                    mJobInfoList = jobInfoList;
                    mNewsContentAdapter.mDateList = jobInfoList;
                    createSuccessView();
                } else {
                    createErrorView();
                }

            }
        });
    }

    private void createSuccessView() {
        new_ll_error.setVisibility(View.GONE);
        news_ll_content.setVisibility(View.VISIBLE);
        mNewsContentAdapter.notifyDataSetChanged();
    }

    private void createErrorView() {
        new_ll_error.setVisibility(View.VISIBLE);
        news_ll_content.setVisibility(View.GONE);
        mNewsContentAdapter.notifyDataSetChanged();
    }


    private void initListener() {
        news_tv_time.setOnClickListener(this);
        news_tv_type.setOnClickListener(this);
        news_tv_error.setOnClickListener(this);
        news_lv_content.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                clickItem(position);
            }
        });
    }

    public JobInfo getJonInfoForPosition(int position) {
        return mNewsContentAdapter.mDateList.get(position);
    }

    /**
     * 点击news_lv_content的条目时
     *
     * @param position
     */
    private void clickItem(int position) {
        Intent intent = new Intent(getActivity(), JobActivity.class);
        intent.putExtra("position", position);
        intent.putExtra("flag", 1);
        startActivity(intent);
    }



    class NewsContentAdapter extends BaseAdapter {
        public List<JobInfo> mDateList;
        private MyListView myListView;

        public NewsContentAdapter(List<JobInfo> mDateList, MyListView myListView) {
            this.mDateList = mDateList;
            this.myListView = myListView;
        }

        @Override
        public int getCount() {
            return mDateList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            NewsHolder newsHolder;
            if (convertView == null) {
                newsHolder = new NewsHolder();
                convertView = newsHolder.getConvertView();
            } else {
                newsHolder = (NewsHolder) convertView.getTag();
            }
            JobInfo jobInfo = mDateList.get(position);
            newsHolder.inteDate(jobInfo);
            return convertView;
        }
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
                ToastUtils.makeText("点击了错误");
                break;
        }
    }


    private String workTimeBoolean2String(boolean b) {
        if (!b)
            return "0";
        return "1";
    }


    /**
     * 根据兼职时间和类型判断
     */
    public void chooseWork( String flagTime, String flagType) {
        if (flagTime.contains("a") && flagType.contains("a")) {
            mNewsContentAdapter.mDateList = mJobInfoList;
            createSuccessView();
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
                createErrorView();
            } else {
                mNewsContentAdapter.mDateList = mJobInfoListType;
                createSuccessView();
            }
        }
    }


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
                chooseWork(mFlagTime.toString(), mFlagType.toString());
                dialer.dismiss();
            }
        });
        dialer.show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mNewsContentAdapter = null;
    }
}
