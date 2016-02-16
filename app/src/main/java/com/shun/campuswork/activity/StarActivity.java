package com.shun.campuswork.activity;

import android.os.Bundle;
import android.widget.ListView;

import com.shun.campuswork.R;
import com.shun.campuswork.adapter.SignUpAdapter;
import com.shun.campuswork.dateprotocol.BaseProtocol;
import com.shun.campuswork.dateprotocol.NewsDateProtocol;
import com.shun.campuswork.dateprotocol.UserDate;
import com.shun.campuswork.domain.JobInfo;
import com.shun.campuswork.global.GlobalContants;
import com.shun.campuswork.tools.SharedPreferencesUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的收藏页面
 */
public class StarActivity extends BaseActivity {
    private ListView lv_star;
    private List<JobInfo> mJobInfoList;
    private SignUpAdapter mHomeAdapter;
    private String mLoggingUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_star);
        initUI();
        initDate();
    }

    private void initUI() {
        mLoggingUser = SharedPreferencesUtils.getString(GlobalContants.LOGGING_USER);
        lv_star = (ListView) findViewById(R.id.lv_star);
        mJobInfoList = new ArrayList<>();
        mHomeAdapter = new SignUpAdapter(lv_star, mJobInfoList);
        lv_star.setAdapter(mHomeAdapter);
    }


    private void initDate() {
        NewsDateProtocol newsDateProtocol = new NewsDateProtocol(1);
        newsDateProtocol.setOnDateListener(new BaseProtocol.OnDateListener<List<JobInfo>>() {
            @Override
            public void onRefresh(List<JobInfo> jobInfoList) {
                if (jobInfoList != null && !mLoggingUser.isEmpty()) {
                    for (int i = 0; i < jobInfoList.size(); i++) {
                        String id = jobInfoList.get(i).id;
                        if (UserDate.isStar(mLoggingUser, id)) {
                            mHomeAdapter.mDateList.add(jobInfoList.get(i));
                        }
                    }
                    mHomeAdapter.notifyDataSetChanged();
                }
            }
        });
    }

}
