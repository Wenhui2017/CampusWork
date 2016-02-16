package com.shun.campuswork.activity;

import android.os.Bundle;
import android.widget.ListView;

import com.shun.campuswork.R;
import com.shun.campuswork.adapter.SignUpAdapter;
import com.shun.campuswork.dateprotocol.BaseProtocol;
import com.shun.campuswork.dateprotocol.NewsDateProtocol;
import com.shun.campuswork.domain.JobInfo;
import com.shun.campuswork.global.GlobalContants;
import com.shun.campuswork.dateprotocol.UserDate;
import com.shun.campuswork.tools.SharedPreferencesUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 已报名页面
 * 从新获取网络数据（缓存中）,从中选择已报名的
 */
public class EnrolledActivity extends BaseActivity {
    private ListView lv_sign_up;
    private List<JobInfo> mJobInfoList;
    private SignUpAdapter mHomeAdapter;
    private String mLoggingUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enroll);
        initUI();
        initDate();
    }

    private void initUI() {
        mLoggingUser = SharedPreferencesUtils.getString(GlobalContants.LOGGING_USER);
        lv_sign_up = (ListView) findViewById(R.id.lv_sign_up);
        mJobInfoList = new ArrayList<>();
        mHomeAdapter = new SignUpAdapter(lv_sign_up, mJobInfoList);
        lv_sign_up.setAdapter(mHomeAdapter);
    }

    private void initDate() {
        NewsDateProtocol newsDateProtocol = new NewsDateProtocol(1);
        newsDateProtocol.setOnDateListener(new BaseProtocol.OnDateListener<List<JobInfo>>() {
            @Override
            public void onRefresh(List<JobInfo> jobInfoList) {
                if (jobInfoList != null && !mLoggingUser.isEmpty()) {
                    for (int i = 0; i < jobInfoList.size(); i++) {
                        String id = jobInfoList.get(i).id;
                        if (UserDate.isEnroll(mLoggingUser, id)) {
                            mHomeAdapter.mDateList.add(jobInfoList.get(i));
                        }
                    }
                    mHomeAdapter.notifyDataSetChanged();
                }
            }
        });
    }

}
