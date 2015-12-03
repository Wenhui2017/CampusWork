package com.shun.campuswork.holder;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shun.campuswork.R;
import com.shun.campuswork.domain.JobInfo;
import com.shun.campuswork.global.GlobalContants;
import com.shun.campuswork.holder.BaseHolder;
import com.shun.campuswork.tools.ColorUtils;
import com.shun.campuswork.tools.UiUtils;

/**
 * Created by shun99 on 2015/12/3.
 */
public class NewsHolder extends BaseHolder {
    public RelativeLayout rl_background_color;
    public TextView tv_background_text;
    public TextView news_tv_title, news_tv_area, news_tv_relessed_time, news_tv_type;

    @Override
    protected void initView() {
        mConvertView = UiUtils.inflate(R.layout.item_news_content);
        news_tv_area = (TextView) mConvertView.findViewById(R.id.news_tv_area);
        news_tv_relessed_time = (TextView) mConvertView.findViewById(R.id.news_tv_relessed_time);
        news_tv_type = (TextView) mConvertView.findViewById(R.id.news_tv_type);
        news_tv_title = (TextView) mConvertView.findViewById(R.id.news_tv_title);
        tv_background_text = (TextView) mConvertView.findViewById(R.id.tv_background_text);
        rl_background_color = (RelativeLayout) mConvertView.findViewById(R.id.rl_background_color);
        mConvertView.setTag(this);
    }

    public void inteDate(JobInfo jobInfo){
        rl_background_color.setBackgroundColor(UiUtils.getResource().getColor(ColorUtils.getRessourceColor(jobInfo.type)));
        news_tv_title.setText(jobInfo.title);
        news_tv_area.setText(jobInfo.area + "");
        tv_background_text.setText(GlobalContants.getWorkType(jobInfo.type));
        news_tv_relessed_time.setText(GlobalContants.getReleaseTime(jobInfo.releaseTime));
        news_tv_type.setText(GlobalContants.getWorkType(jobInfo.type));
    }
}
