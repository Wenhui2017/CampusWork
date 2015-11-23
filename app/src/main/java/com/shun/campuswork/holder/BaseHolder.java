package com.shun.campuswork.holder;

import android.view.View;

/**
 * Created by shun99 on 2015/11/22.
 */
public abstract class BaseHolder {
    //public BitmapUtils mBitmapUtils;
    public View mConvertView;// holder要返回的布局

    public BaseHolder() {
        //mBitmapUtils = BitmapHelp.getBitmapUtils(UiUtils.getContext());
        initView();
    }

    protected abstract void initView();

    /**
     * 获取根视图
     * @return
     */
    public View getConvertView() {
        return mConvertView;
    }
}
