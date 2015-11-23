package com.shun.campuswork.tools;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.WindowManager;
import com.shun.campuswork.BaseApplication;


/**
 * Created by shun99 on 2015/11/22.
 */
public class UiUtils {

    public static int getDeviceWidth(){
        WindowManager wm = (WindowManager) UiUtils.getContext().getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getWidth();//屏幕宽度
    }

    public static int getDeviceHeight(){
        WindowManager wm = (WindowManager) UiUtils.getContext().getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getHeight();//屏幕宽度
    }

    /**
     * 获取到字符数组
     *
     * @param tabNames
     *            字符数组的id
     */
    public static String[] getStringArray(int tabNames) {
        return getResource().getStringArray(tabNames);
    }

    /**
     * 获取应用的资源管理器
     *
     * @return
     */
    public static Resources getResource() {
        return BaseApplication.getApplication().getResources();
    }

    /** dip转换px */
    public static int dip2px(int dip) {
        final float scale = getResource().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }

    /** pxz转换dip */

    public static int px2dip(int px) {
        final float scale = getResource().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

    /**
     * 获取上下文对象
     *
     * @return
     */
    public static Context getContext() {
        return BaseApplication.getApplication();
    }

    /**
     * 根据resource获取View布局对象
     *
     * @param resource
     * @return
     */
    public static View inflate(int resource) {
        return View.inflate(getContext(), resource, null);
    }
}
