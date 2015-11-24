package com.shun.campuswork.view;

import android.widget.FrameLayout;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.shun.campuswork.R;
import com.shun.campuswork.tools.UiUtils;

/**
 * Created by shun99 on 2015/11/23.
 */
public abstract class LoadingPage extends FrameLayout {
    // 状态
    public static final int STATE_EMPTY = 0;
    public static final int STATE_SUCCESS = 1;
    public int current_state = STATE_EMPTY;

    private View loadingView;// 加载中的界面
    private View successView;// 加载成功的界面

    /**
     * 装载view的容器，也是该页面根布局,根据不同状态显示列表
     */
    public LoadingPage(Context context) {
        super(context);
        init();
    }

    public LoadingPage(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public LoadingPage(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        showViewByCurrentState();
    }

    /**
     * 请求服务器数据，根据请求结果更新当前状态，此方法只会在加载失败时，从新加载是调用
     */
    public abstract void requestServer();

    /* 创建了空的界面 */
    private View createEmptyView() {
        View view = View.inflate(UiUtils.getContext(), R.layout.layout_loading,
                null);
        return view;
    }

    /***
     * 创建成功的界面
     *
     * @return
     */
    public abstract View createSuccessView();

    /**
     * 根据当前状态，在fragmentlayout显示view
     */
    public void showViewByCurrentState() {
        removeAllViews();
        switch (current_state) {
            case STATE_SUCCESS:
                if (successView == null) {
                    successView = createSuccessView();
                }
                addView(successView, new FrameLayout.LayoutParams(
                        LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
                break;
            default:
                if (loadingView == null) {
                    loadingView = createEmptyView(); // 创建了加载中的界面
                }
                addView(loadingView, new FrameLayout.LayoutParams(
                        LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        }
    }
}
