package com.shun.campuswork.tools;

import android.view.View;

import com.lidroid.xutils.BitmapUtils;

/**
 * 对BitmapUtils的封装
 * Created by shun99 on 2015/12/12.
 */
public class MyBitmapUtils {

    /**
     *
     * @param container
     * @param uri
     */
    public static void display(View container, String uri){
        BitmapUtils bitmapUtils = new BitmapUtils(UiUtils.getContext());
        bitmapUtils.display(container, uri);
    }
}
