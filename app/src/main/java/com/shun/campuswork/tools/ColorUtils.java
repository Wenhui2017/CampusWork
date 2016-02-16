package com.shun.campuswork.tools;

import com.shun.campuswork.R;

/**
 * Created by shun99 on 2015/11/27.
 */
public class ColorUtils {
    /**
     * 招聘信息类型
     */
    public static int[] colors = new int[]{R.color.type0, R.color.type1, R.color.type2, R.color.type3, R.color.type4, R.color.type5, R.color.type6};

    /**
     * 下拉刷新
     */
    public static int[] refreshColors = new int[]{R.color.secondary_color, R.color.primary_color,
            R.color.next_product_title_color, R.color.next_product_count_bg};

    public static int getRessourceColor(int i) {
        return colors[i];
    }
}
