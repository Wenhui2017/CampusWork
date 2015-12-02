package com.shun.campuswork.holder;

import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.shun.campuswork.R;
import com.shun.campuswork.domain.JobInfo;
import com.shun.campuswork.tools.UiUtils;


/**
 * Created by shun99 on 2015/11/22.
 */
public class HomeContentHolder extends BaseHolder {
    @ViewInject(R.id.home_iv_icon)
    private ImageView home_iv_icon;
    @ViewInject(R.id.home_tv_des)
    private TextView home_tv_des;
    @ViewInject(R.id.home_tv_title)
    private TextView home_tv_title;

    @Override
    protected void initView() {
        mConvertView = UiUtils.inflate(R.layout.item_lv_home_content);
        ViewUtils.inject(this, mConvertView);
        mConvertView.setTag(this);
    }

    public void initDate(JobInfo jobInfo) {
    }


}
