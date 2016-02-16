package com.shun.campuswork.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.shun.campuswork.domain.JobInfo;
import com.shun.campuswork.holder.NewsHolder;
import com.shun.campuswork.view.MyListView;

import java.util.List;

/**
 * Created by shun99 on 2016/2/14.
 */
public class NewsContentAdapter extends BaseAdapter{
    public List<JobInfo> mDateList;
    private MyListView myListView;

    public NewsContentAdapter(List<JobInfo> mDateList, MyListView myListView) {
        this.mDateList = mDateList;
        this.myListView = myListView;
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
