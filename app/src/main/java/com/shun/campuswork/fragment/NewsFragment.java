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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.shun.campuswork.R;
import com.shun.campuswork.activity.JobActivity;
import com.shun.campuswork.dateprotocol.BaseProtocol;
import com.shun.campuswork.dateprotocol.NewsDateProtocol;
import com.shun.campuswork.domain.JobInfo;
import com.shun.campuswork.global.GlobalContants;
import com.shun.campuswork.tools.ColorUtils;
import com.shun.campuswork.tools.ToastUtils;
import com.shun.campuswork.tools.UiUtils;
import com.shun.campuswork.view.MyListView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * 最新招聘信息的fragment--单例模式
 * Created by shun99 on 2015/11/19.
 */
public class NewsFragment extends Fragment implements View.OnClickListener {
    private static NewsFragment instance = null;

    public static NewsFragment getInstance() {
        if (instance == null) {
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
        NewsDateProtocol newsDateProtocol = new NewsDateProtocol(1);
        newsDateProtocol.setOnDateListener(new BaseProtocol.OnDateListener<List<JobInfo>>() {
            @Override
            public void onRefresh(List<JobInfo> jobInfoList) {
                Log.w("....", "" + jobInfoList);
                if (jobInfoList != null) {
                    mJobInfoList = jobInfoList;
                    new_ll_error.setVisibility(View.GONE);
                    news_ll_content.setVisibility(View.VISIBLE);
                    createSuccessView();
                } else {
                    new_ll_error.setVisibility(View.VISIBLE);
                    news_ll_content.setVisibility(View.GONE);
                }

            }
        });
        return view;
    }

    List<JobInfo> mJobInfoList;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initListener();
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

    public JobInfo getJonInfoForPosition(int position){
        return mJobInfoList.get(position);
    }

    /**
     * 点击news_lv_content的条目时
     *
     * @param position
     */
    private void clickItem(int position) {
        ToastUtils.makeText("点击位置" + position);
        Intent intent = new Intent(getActivity(), JobActivity.class);
        intent.putExtra("position", position);
        startActivity(intent);
    }

    private void createSuccessView() {
        news_lv_content.setAdapter(new NewsContentAdapter());
    }

    class NewsContentAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mJobInfoList.size();
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
            NewsContentHolder holder;
            if (convertView == null) {
                convertView = UiUtils.inflate(R.layout.item_news_content);
                holder = new NewsContentHolder();
                holder.news_tv_area = (TextView) convertView.findViewById(R.id.news_tv_area);
                holder.news_tv_relessed_time = (TextView) convertView.findViewById(R.id.news_tv_relessed_time);
                holder.news_tv_type = (TextView) convertView.findViewById(R.id.news_tv_type);
                holder.news_tv_title = (TextView) convertView.findViewById(R.id.news_tv_title);
                holder.tv_background_text = (TextView) convertView.findViewById(R.id.tv_background_text);
                holder.rl_background_color = (RelativeLayout) convertView.findViewById(R.id.rl_background_color);
                convertView.setTag(holder);
            } else {
                holder = (NewsContentHolder) convertView.getTag();
            }
            JobInfo jobInfo = mJobInfoList.get(position);
            holder.rl_background_color.setBackgroundColor(getResources().getColor(ColorUtils.getRessourceColor(jobInfo.type)));
            holder.news_tv_title.setText(jobInfo.title);
            holder.news_tv_area.setText(jobInfo.area + "");
            holder.tv_background_text.setText(GlobalContants.getWorkType(jobInfo.type));
            holder.news_tv_relessed_time.setText(jobInfo.releaseTime + "小时前");
            holder.news_tv_type.setText(jobInfo.type + "");
            return convertView;
        }
    }

    static class NewsContentHolder {
        public RelativeLayout rl_background_color;
        public TextView tv_background_text;
        public TextView news_tv_title, news_tv_area, news_tv_relessed_time, news_tv_type;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.news_tv_time:
                ToastUtils.makeText("点击了时间");
                alertTime();
                break;
            case R.id.news_tv_type:
                alertType();
                ToastUtils.makeText("点击了类型");
                break;
            case R.id.news_tv_error:
                ToastUtils.makeText("点击了错误");
                break;
        }
    }


    public void alertTime() {
        // 创建一个 Builder 对象
        AlertDialog.Builder bulider = new AlertDialog.Builder(getContext());
        View view = UiUtils.inflate(R.layout.layout_dialog_time);
        AlertDialog dialer = bulider.create();
        dialer.setView(view, 0, 0, 0, 0);
        dialer.show();
    }

    public void alertType() {
        // 创建一个 Builder 对象
        AlertDialog.Builder bulider = new AlertDialog.Builder(getContext());
        View view = UiUtils.inflate(R.layout.layout_dialog_type);
        AlertDialog dialer = bulider.create();
        dialer.setView(view, 0, 0, 0, 0);
        dialer.show();
    }

}
