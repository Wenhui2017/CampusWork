package com.shun.campuswork.adapter;

import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;

import com.shun.campuswork.domain.JobInfo;
import com.shun.campuswork.holder.NewsHolder;
import com.shun.campuswork.view.MyListView;

import java.util.List;


public abstract class NewsContentAdapter extends MyBaseAdapter {

    public NewsContentAdapter(List<JobInfo> mDateList, MyListView myListView, Handler handler) {
        super(mDateList, myListView, handler);
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
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        NewsHolder newsHolder;
        if (convertView == null) {
            newsHolder = new NewsHolder();
            convertView = newsHolder.getConvertView();
        } else {
            newsHolder = (NewsHolder) convertView.getTag();
        }
        JobInfo jobInfo = mDateList.get(position);
        newsHolder.inteDate(jobInfo);
        return convertView;
    }
}
