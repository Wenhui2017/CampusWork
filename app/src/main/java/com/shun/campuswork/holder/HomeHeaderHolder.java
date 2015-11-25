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
import com.shun.campuswork.tools.UiUtils;
import com.shun.campuswork.view.MyHeadViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shun99 on 2015/11/22.
 */
public class HomeHeaderHolder extends BaseHolder{
    @ViewInject(R.id.headlines_view_vp)
    private MyHeadViewPager viewPager;
    @ViewInject(R.id.headlines_view_title_tv)
    private TextView headlines_view_title_tv;
    @ViewInject(R.id.headlines_view_number_tv)
    private TextView headlines_view_number_tv;

    private List<View> mViewlist;

    @Override
    protected void initView() {
        mConvertView = UiUtils.inflate(R.layout.item_lv_home_head);
        ViewUtils.inject(this, mConvertView);
        viewPager = (MyHeadViewPager) mConvertView.findViewById(R.id.headlines_view_vp);
        //指定条目大小的方式二----必须设置布局参数
        int deviceHeight = UiUtils.getDeviceHeight();
        AbsListView.LayoutParams lp = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, deviceHeight / 4);
        mConvertView.setLayoutParams(lp);
    }

    public void initDate(){
        mViewlist = new ArrayList<View>();
        Context mContext = UiUtils.getContext();
        int[] mIvR = new int[]{R.mipmap.pic_banner_guoqing, R.mipmap.pic_banner_guoqing_big, R.mipmap.pic_miao_banner};
        for (int i = 0; i < mIvR.length; i++) {
            ImageView imageView = new ImageView(mContext);
            imageView.setImageResource(mIvR[i]);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            mViewlist.add(imageView);
        }
        viewPager.setAdapter(new MyPageAdapter());

        headlines_view_title_tv.setText("这是什么");
        headlines_view_number_tv.setText("1 / 4");

    }

    class MyPageAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mViewlist.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mViewlist.get(position));
            return mViewlist.get(position);

        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mViewlist.get(position));
        }

    }
}