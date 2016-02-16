package com.shun.campuswork.holder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.shun.campuswork.R;
import com.shun.campuswork.activity.GuideActivity;
import com.shun.campuswork.activity.MainActivity;
import com.shun.campuswork.activity.QuestionActivity;
import com.shun.campuswork.fragment.HomeFragment;
import com.shun.campuswork.fragment.NewsFragment;
import com.shun.campuswork.global.GlobalContants;
import com.shun.campuswork.tools.LogUtils;
import com.shun.campuswork.tools.ToastUtils;
import com.shun.campuswork.tools.UiUtils;
import com.shun.campuswork.view.HomeCenterView;

/**
 * Created by shun99 on 2015/12/3.
 */
public class HomeCenterHolder extends BaseHolder implements View.OnClickListener {
    @ViewInject(R.id.hcv_1)
    private HomeCenterView hcv_1;
    @ViewInject(R.id.hcv_2)
    private HomeCenterView hcv_2;
    @ViewInject(R.id.hcv_3)
    private HomeCenterView hcv_3;
    @ViewInject(R.id.hcv_4)
    private HomeCenterView hcv_4;

    @Override
    protected void initView() {
        mConvertView = UiUtils.inflate(R.layout.item_lv_home_center);
        ViewUtils.inject(this, mConvertView);
        initListener();
    }

    private void initListener() {
        hcv_1.setOnClickListener(this);
        hcv_2.setOnClickListener(this);
        hcv_3.setOnClickListener(this);
        hcv_4.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.hcv_1:
                jumpNewsFragment("0", "a");
                break;
            case R.id.hcv_2:
                jumpNewsFragment("1", "a");
                break;
            case R.id.hcv_3:
                MainActivity.instance.startActivity(new Intent(UiUtils.getContext(), QuestionActivity.class));
                break;
            case R.id.hcv_4:
                MainActivity.instance.startActivity(new Intent(UiUtils.getContext(), GuideActivity.class));
                break;
        }
    }

    private void jumpNewsFragment(String s, String s1) {
        if(NewsFragment.instance == null) {
            Bundle bundle = new Bundle();
            bundle.putString(GlobalContants.FLAG_TIME, s);
            bundle.putString(GlobalContants.FLAG_TYPE, s1);
            MainActivity.instance.selectItem(1, bundle);
        }else{
            MainActivity.instance.selectItem(1);
            NewsFragment.instance.chooseWork(s, s1);
        }
        MainActivity.instance.setNavItemState(1, true);
    }
}
