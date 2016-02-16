package com.shun.campuswork.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.shun.campuswork.R;
import com.shun.campuswork.dateprotocol.UserDate;
import com.shun.campuswork.domain.JobInfo;
import com.shun.campuswork.global.GlobalContants;
import com.shun.campuswork.tools.ToastUtils;
import com.shun.campuswork.view.ButtonView;

/**
 * 工作信息详情
 */
public class JobActivity extends BaseActivity implements View.OnClickListener {

    @ViewInject(R.id.job_tv_title)
    private TextView job_tv_title;
    @ViewInject(R.id.job_tv_area)
    private TextView job_tv_area;
    @ViewInject(R.id.job_tv_time)
    private TextView job_tv_time;
    @ViewInject(R.id.job_tv_type)
    private TextView job_tv_type;
    @ViewInject(R.id.job_tv_num)
    private TextView job_tv_num;
    @ViewInject(R.id.job_tv_salary)
    private TextView job_tv_salary;
    @ViewInject(R.id.job_tv_sex)
    private TextView job_tv_sex;
    @ViewInject(R.id.job_tv_company)
    private TextView job_tv_company;
    @ViewInject(R.id.job_tv_workDay)
    private TextView job_tv_workDay;
    @ViewInject(R.id.job_tv_workTime)
    private TextView job_tv_workTime;
    @ViewInject(R.id.job_tv_address)
    private TextView job_tv_address;
    @ViewInject(R.id.job_tv_tel)
    private TextView job_tv_tel;
    @ViewInject(R.id.job_tv_name)
    private TextView job_tv_name;
    @ViewInject(R.id.btnv_star)
    private ButtonView btnv_star;
    @ViewInject(R.id.btnv_share)
    private ButtonView btnv_share;
    @ViewInject(R.id.ll_sign_up)
    private LinearLayout ll_sign_up;

    private JobInfo mJobInfo;
    private String mLoggingUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jab);
        ViewUtils.inject(this);
        initUI();
        initListener();
        mLoggingUser = UserDate.getLoggingUser();
    }

    private void initListener() {
        btnv_star.setOnClickListener(this);
        btnv_share.setOnClickListener(this);
        ll_sign_up.setOnClickListener(this);
    }


    private void initUI() {
        mJobInfo = (JobInfo) getIntent().getSerializableExtra("jobInfo");
        job_tv_title.setText(mJobInfo.title);
        job_tv_area.setText(mJobInfo.area);
        job_tv_time.setText(GlobalContants.getReleaseTime(mJobInfo.releaseTime));
        job_tv_type.setText(GlobalContants.getWorkType(mJobInfo.type));
        job_tv_num.setText(mJobInfo.num + "");
        job_tv_salary.setText(GlobalContants.getSalary(mJobInfo.salary));
        job_tv_sex.setText(GlobalContants.getSex(mJobInfo.sex));
        job_tv_company.setText(mJobInfo.company);
        job_tv_workDay.setText(mJobInfo.workDay);
        job_tv_workTime.setText(mJobInfo.workTime);
        job_tv_address.setText(mJobInfo.desAddress);
        job_tv_tel.setText(mJobInfo.tel);
        job_tv_name.setText(mJobInfo.name);
    }

    @Override
    public void onClick(View v) {
        if (TextUtils.isEmpty(mLoggingUser)) {
            ToastUtils.makeText("你还没有登入");
            return;
        }
        switch (v.getId()) {
            case R.id.ll_sign_up:
                enroll();
                break;
            case R.id.btnv_share:
                break;
            case R.id.btnv_star:
                star();
                break;
        }

    }

    private void star() {
        if (UserDate.star(mLoggingUser, mJobInfo.id)) {
            ToastUtils.makeText("收藏成功");
        } else {
            ToastUtils.makeText("取消收藏");
            UserDate.escStar(mLoggingUser, mJobInfo.id);
        }
    }

    private void enroll() {
        Log.w("id", "" + mJobInfo.id);
        if (UserDate.enroll(mLoggingUser, mJobInfo.id)) {
            ToastUtils.makeText("报名成功");
        } else {
            ToastUtils.makeText("取消报名");
            UserDate.escEnroll(mLoggingUser, mJobInfo.id);
        }
    }


}
