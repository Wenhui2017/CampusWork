package com.shun.campuswork.holder;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.shun.campuswork.R;
import com.shun.campuswork.adapter.MyPagerAdapter;
import com.shun.campuswork.global.GlobalContants;
import com.shun.campuswork.tools.ToastUtils;
import com.shun.campuswork.tools.UiUtils;
import com.shun.campuswork.view.MyHeadViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shun99 on 2015/11/22.
 */
public class HomeHeaderHolder extends BaseHolder {
    @ViewInject(R.id.headlines_view_vp)
    private MyHeadViewPager viewPager;

    private List<View> mViewList;

    @Override
    protected void initView() {
        mConvertView = UiUtils.inflate(R.layout.item_lv_home_head);
        ViewUtils.inject(this, mConvertView);
        viewPager = (MyHeadViewPager) mConvertView.findViewById(R.id.headlines_view_vp);
        //指定条目大小的方式二----必须设置布局参数
        int deviceHeight = UiUtils.getDeviceHeight();
        AbsListView.LayoutParams lp = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, deviceHeight / 5);
        mConvertView.setLayoutParams(lp);
    }

    public void initDate() {
        mViewList = new ArrayList<View>();
        Context mContext = UiUtils.getContext();
        int[] mIvR = GlobalContants.AD;
        //new int[]{R.mipmap.ad1, R.mipmap.ad2, R.mipmap.ad3, R.mipmap.pic_banner_guoqing_big};
        for (int i = 0; i < mIvR.length; i++) {
            ImageView imageView = new ImageView(mContext);
            imageView.setImageResource(mIvR[i]);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            mViewList.add(imageView);
        }

        MyPagerAdapter pagerAdapter = new MyPagerAdapter(mViewList, true);
        pagerAdapter.setOnItemClickListener(new MyPagerAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                clickImage(position);
            }
        });
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(mViewList.size() * 200);
    }

    /**
     * 处理viewpager中图片的点击事件
     *
     * @param position
     */
    public void clickImage(int position) {
        ToastUtils.makeText("position" + position);
    }

}
