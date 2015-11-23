package com.shun.campuswork.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shun.campuswork.R;
import com.shun.campuswork.domain.JobInfo;
import com.shun.campuswork.tools.UiUtils;

import java.util.List;

/**
 * Created by shun99 on 2015/11/22.
 */
public class HomeHolder extends BaseHolder{
    TextView tv_title;
    ImageView iv_pic;
    View v_des;

    @Override
    protected void initView() {
        mConvertView = UiUtils.inflate(R.layout.item_lv_home);
        tv_title = (TextView) mConvertView.findViewById(R.id.tv_title);
        iv_pic = (ImageView) mConvertView.findViewById(R.id.iv_pic);
        v_des = mConvertView.findViewById(R.id.v_des);
        mConvertView.setTag(this);
    }

    public void initDate(List<JobInfo> jobInfoList) {
       tv_title.setText("zhehsi....");
    }


}
