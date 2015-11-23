package com.shun.campuswork.view;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.shun.campuswork.R;
import com.shun.campuswork.tools.UiUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shun99 on 2015/11/22.
 */
public class HomeHeaderViewPager {
    public ViewPager viewPager;
    private List<View> list = new ArrayList<View>();
    private Context mContext;
    private int[] mIvR;


    public void init() {
        mContext = UiUtils.getContext();
        mIvR = new int[]{R.mipmap.pic_banner_guoqing, R.mipmap.pic_banner_guoqing_big, R.mipmap.pic_miao_banner};
        for (int i = 0; i < 3; i++) {
            ImageView imageView = new ImageView(mContext);
            imageView.setImageResource(mIvR[i]);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            list.add(imageView);
        }
        viewPager = (ViewPager) View.inflate(mContext, R.layout.item_lv_home_head, null);
        int deviceHeight = UiUtils.getDeviceHeight();

        /**
         * 必须设置LayoutParams
         */
        AbsListView.LayoutParams lp = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, deviceHeight / 5);
        viewPager.setLayoutParams(lp);
        viewPager.setAdapter(new MyPageAdapter());
    }

    class MyPageAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(list.get(position));
            return list.get(position);

        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(list.get(position));
        }

    }
}
