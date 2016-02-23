package com.shun.campuswork.adapter;

import android.os.Handler;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

import com.shun.campuswork.R;
import com.shun.campuswork.domain.JobInfo;
import com.shun.campuswork.tools.ToastUtils;
import com.shun.campuswork.tools.UiUtils;
import com.shun.campuswork.view.MyListView;

import java.util.List;

/**
 * 对BaseAdapter的封装
 * 本类已经处理了滚动监听，条目点击监听，加载更多
 */
public abstract class MyBaseAdapter extends BaseAdapter implements AbsListView.OnScrollListener, AdapterView.OnItemClickListener {
    public List<JobInfo> mDateList;
    public MyListView myListView;
    private View mFootView;
    private Handler mHandler;

    public MyBaseAdapter(List<JobInfo> mDateList, MyListView myListView, Handler handler) {
        this.mDateList = mDateList;
        this.myListView = myListView;
        mHandler = handler;
        //添加加载更多
        mFootView = UiUtils.inflate(R.layout.item_lv_more_date);
        addFooterView();
        myListView.setOnScrollListener(this);
        myListView.setOnItemClickListener(this);
    }

    //===============对外暴露

    /**
     * 添加加载更多
     */
    public void addFooterView() {
        if (myListView.getFooterViewsCount() <= 0) {
            myListView.addFooterView(mFootView);
        }
    }

    /**
     * 刷新加载更多
     *
     * @param t
     */
    public void refreshLoadMore(List<JobInfo> t) {
        if (t == null) {
            myListView.removeFooterView(mFootView);
            ToastUtils.makeText("没有更多");
        } else {
            mDateList.addAll(t);
            setLoadSuccessState();
        }
    }

    public abstract void LoadMoreDate();

    public abstract void setLoadSuccessState();

    public abstract void clickItem(int position);

    //======================事件监听
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        clickItem(position);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
            if (view.getLastVisiblePosition() == view.getCount() - 1) {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        LoadMoreDate();
                    }
                }, 1000);
            }
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }


}
