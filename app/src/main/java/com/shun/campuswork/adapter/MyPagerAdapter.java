package com.shun.campuswork.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * PagerAdapter的封装
 * 可设置是否 循环滚动
 */
public class MyPagerAdapter extends PagerAdapter {
    private List<View> mViewList;
    private Boolean mIsLoop;

    /**
     * @param viewList 数据
     * @param isLoop   循环滚动
     */
    public MyPagerAdapter(List<View> viewList, Boolean isLoop) {
        mViewList = viewList;
        mIsLoop = isLoop;

    }

    private View getView(int position) {
        View view;
        if (mIsLoop) {
            view = mViewList.get(position % mViewList.size());
        } else {
            view = mViewList.get(position);
        }
        return view;
    }


    @Override
    public int getCount() {
        if (mIsLoop) {
            return Integer.MAX_VALUE;
        }
        return mViewList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if (mViewList.size() > 3) {
            container.removeView(getView(position));
        }
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = getView(position);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = position;
                if (mIsLoop) {
                    pos = pos % mViewList.size();
                }
                mListener.onClick(pos);
            }
        });
        try {
            container.addView(view);
        } catch (Exception e) {
        }
        return view;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onClick(int position);
    }
}
