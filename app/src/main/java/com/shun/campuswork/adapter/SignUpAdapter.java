package com.shun.campuswork.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.shun.campuswork.domain.JobInfo;
import com.shun.campuswork.holder.HomeCenterHolder;
import com.shun.campuswork.holder.HomeHeaderHolder;
import com.shun.campuswork.holder.NewsHolder;

import java.util.List;

/**
 * Created by shun99 on 2015/11/22.
 */
public class SignUpAdapter extends BaseAdapter {
    public ListView mListView;
    public List<JobInfo> mDateList;

    public SignUpAdapter(ListView mListView, List<JobInfo> mDateList) {
        this.mListView = mListView;
        this.mDateList = mDateList;
    }

    @Override
    public int getCount() {
        return mDateList.size();
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
        NewsHolder holder;
        if (convertView == null) {
            holder = new NewsHolder();
            convertView = holder.getConvertView();
        } else {
            holder = (NewsHolder) convertView.getTag();
        }
        holder.inteDate(mDateList.get(position));
        return convertView;
    }
}





