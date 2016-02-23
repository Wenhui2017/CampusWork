package com.shun.campuswork.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.shun.campuswork.R;
import com.shun.campuswork.domain.JobInfo;
import com.shun.campuswork.holder.BaseHolder;
import com.shun.campuswork.holder.HomeCenterHolder;
import com.shun.campuswork.holder.HomeContentHolder;
import com.shun.campuswork.holder.HomeHeaderHolder;
import com.shun.campuswork.holder.MoreHolder;
import com.shun.campuswork.holder.NewsHolder;
import com.shun.campuswork.tools.UiUtils;

import java.util.List;

/**
 * 该适配器里有3中item
 */
public class HomeAdapter extends BaseAdapter {
    public ListView mListView;
    public List<JobInfo> mDateList;
//    private int mAddType = 0;
//    private final int LOAD_MORE = 100;


    public HomeAdapter(ListView mListView, List<JobInfo> mDateList) {
        this.mListView = mListView;
        this.mDateList = mDateList;
    }

    @Override
    public int getViewTypeCount() {
        return super.getViewTypeCount() + 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return 1;
        } else if (position == 1) {
            return 2;
        }
        return 0;
    }

    @Override
    public int getCount() {
        return mDateList.size() + 2;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        switch (getItemViewType(position)) {
            case 1:
                HomeHeaderHolder homeHeaderHolder = new HomeHeaderHolder();
                convertView = homeHeaderHolder.getConvertView();
                homeHeaderHolder.initDate();
                return convertView;
            case 2:
                HomeCenterHolder homeCenterHolder = new HomeCenterHolder();
                convertView = homeCenterHolder.getConvertView();
                return convertView;
            case 0:
                NewsHolder holder;
                if (convertView == null) {
                    holder = new NewsHolder();
                    convertView = holder.getConvertView();
                } else {
                    holder = (NewsHolder) convertView.getTag();
                }
                holder.inteDate(mDateList.get(position - 2));

                return convertView;
        }
        return convertView;
    }
}





