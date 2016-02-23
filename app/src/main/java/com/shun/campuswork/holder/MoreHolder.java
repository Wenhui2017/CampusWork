package com.shun.campuswork.holder;

import android.view.View;
import android.widget.RelativeLayout;

import com.shun.campuswork.R;
import com.shun.campuswork.tools.UiUtils;

/**
 * Created by shun99 on 2016/2/23.
 */
public class MoreHolder extends BaseHolder{
    public static final int LOADMORE_ING = 0;// 正在加载更多
    public static final int LOADMORE_ERROE = 1;// 加载更多失败
    RelativeLayout rl_more_loading;
    RelativeLayout rl_more_error;

    @Override
    protected void initView() {
        mConvertView = UiUtils.inflate(R.layout.item_lv_more_date);
        rl_more_loading = (RelativeLayout) mConvertView
                .findViewById(R.id.rl_more_loading);
        rl_more_error = (RelativeLayout) mConvertView
                .findViewById(R.id.rl_more_error);
        mConvertView.setTag(this);
    }

    /**
     * 根据当前的状态更新显示的内容
     *
     * @param currentState
     */
    public void refreshView(int currentState) {
        if (currentState == LOADMORE_ING) {
            rl_more_loading.setVisibility(View.VISIBLE);
            rl_more_error.setVisibility(View.INVISIBLE);
        } else {
            rl_more_loading.setVisibility(View.INVISIBLE);
            rl_more_error.setVisibility(View.VISIBLE);
        }
    }
}
