package com.shun.campuswork.adapter;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.List;

/**
 * Created by shun99 on 2015/11/22.
 */
public abstract class MyBaseAdapter<T> extends BaseAdapter{
    public static final int MORE_ITEM = 2;// listview加载更多
    public static final int HEADER_ITEM = 1;// listview头部
    public static final int CONTENT_ITEM = 0;//listview内容条目
    public int mLondingCurrentState;// 当前加载更多的状态
    public ListView mListView;

    public List<T> mDateList;
    public int addItemType = 0;
    public boolean isHeadDemo = false;
    public boolean isEndLoadMore = false;

    /**
     *
     * @param isHeadDemo
     *            是否有展示头
     * @param isEndLoadMore
     *            是否可以加载更多
     * @param listView
     *            使用改适配器的listview
     * @param dateList
     *            数据
     */
    public MyBaseAdapter(boolean isHeadDemo, boolean isEndLoadMore,
                         ListView listView, List<T> dateList) {
        this.mDateList = dateList;
        if (isHeadDemo) {
            this.isHeadDemo = true;
            addItemType++;
        }
        if (isEndLoadMore) {
            this.isEndLoadMore = true;
            addItemType++;
        }
        mListView = listView;
    }

    @Override
    public int getCount() {
        return mDateList.size() + addItemType;
    }

    /** 根据位置 判断当前条目是什么类型 */
    @Override
    public int getItemViewType(int position) {
        if (isHeadDemo && isEndLoadMore) {// 有头有尾
            if (position == mDateList.size() + 1) {// 最后一项返回类型MORE_ITEM
                return MORE_ITEM;
            } else if (position == 0) {
                return HEADER_ITEM;
            }
            return super.getItemViewType(position + 1);
        } else if (!isHeadDemo && isEndLoadMore) {// 无头有尾
            if (position == mDateList.size()) {// 最后一项返回类型MORE_ITEM
                return MORE_ITEM;
            }
            return super.getItemViewType(position);
        }else if(isHeadDemo && !isEndLoadMore){// 有头无尾
            if (position == 0) {
                return HEADER_ITEM;
            }
            return CONTENT_ITEM;
        }
        return CONTENT_ITEM;
    }

    /** 当前ListView 有几种不同的条目类型 */
    @Override
    public int getViewTypeCount() {
        return 1 + addItemType;
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
    public abstract View getView(int position, View convertView, ViewGroup parent);
}
