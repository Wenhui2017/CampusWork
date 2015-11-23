package com.shun.campuswork.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.shun.campuswork.domain.JobInfo;
import com.shun.campuswork.holder.HomeHolder;
import com.shun.campuswork.view.HomeHeaderViewPager;

import java.util.List;

/**
 * Created by shun99 on 2015/11/22.
 */
public class HomeAdapter extends MyBaseAdapter<JobInfo> {
    /**
     * @param isHeadDemo    是否有展示头
     * @param isEndLoadMore 是否可以加载更多
     * @param listView      使用改适配器的listview
     * @param dateList
     */
    public HomeAdapter(boolean isHeadDemo, boolean isEndLoadMore, ListView listView, List<JobInfo> dateList) {
        super(isHeadDemo, isEndLoadMore, listView, dateList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        switch (getItemViewType(position)) {
            case HEADER_ITEM: {
                HomeHeaderViewPager myViewPager = new HomeHeaderViewPager();
                myViewPager.init();
                convertView = myViewPager.viewPager;
                return convertView;
            }
            default: {
                HomeHolder holder;
                if(convertView == null){
                    holder = new HomeHolder();
                    convertView = holder.getConvertView();
                }else{
                    holder = (HomeHolder) convertView.getTag();
                }
                if (holder != null) {
                    holder.initDate(mDateList.get(position-1));
                }
            }
        }

        return convertView;
    }

}





