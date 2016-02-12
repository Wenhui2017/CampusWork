package com.shun.campuswork.activity;

import android.util.Log;
import android.widget.ListView;

import com.shun.campuswork.R;
import com.shun.campuswork.adapter.HomeAdapter;
import com.shun.campuswork.adapter.SignUpAdapter;
import com.shun.campuswork.dateprotocol.BaseProtocol;
import com.shun.campuswork.dateprotocol.HomeDateProtocol;
import com.shun.campuswork.dateprotocol.NewsDateProtocol;
import com.shun.campuswork.domain.JobInfo;
import com.shun.campuswork.netdate.UserDate;
import com.shun.campuswork.tools.SharedPreferencesUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shun99 on 2015/11/29.
 */
public class SignUpActivity extends BaseActivity {
    private ListView lv_sign_up;
    private List<JobInfo> mJobInfoList;
    private SignUpAdapter mHomeAdapter;
    private String currentUser;

    @Override
    public void init() {
        setContentView(R.layout.activity_sign_up);
        currentUser = SharedPreferencesUtils.getString(SharedPreferencesUtils.CURRENT_USER);
        lv_sign_up = (ListView) findViewById(R.id.lv_sign_up);
        mJobInfoList = new ArrayList<JobInfo>();
        mHomeAdapter = new SignUpAdapter(lv_sign_up, mJobInfoList);
        lv_sign_up.setAdapter(mHomeAdapter);
        initDate();
    }

    private void initDate() {
        NewsDateProtocol newsDateProtocol = new NewsDateProtocol(1);
        newsDateProtocol.setOnDateListener(new BaseProtocol.OnDateListener<List<JobInfo>>() {
            @Override
            public void onRefresh(List<JobInfo> jobInfoList) {
                if (jobInfoList != null && !currentUser.isEmpty()) {
                    for (int i = 0; i < jobInfoList.size(); i++) {
                        String id = jobInfoList.get(i).id;
                        if (UserDate.isSignUp(currentUser, id)) {
                            mHomeAdapter.mDateList.add(jobInfoList.get(i));
                        }
                    }
                    Log.w("size", "" + mHomeAdapter.mDateList.size());
                    mHomeAdapter.notifyDataSetChanged();
                }
            }
        });
    }

}
