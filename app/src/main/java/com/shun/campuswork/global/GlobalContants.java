package com.shun.campuswork.global;

import com.shun.campuswork.R;

/**
 * 存储项目的常量
 *
 * @author shun99
 */
public class GlobalContants {
    public static final String SERVER_URL = "http://campuswork1.sinaapp.com/";
    public static final String IMAGE_URL = SERVER_URL + "image?name=";
    public static final String NEWS = "news";
    public static final String RECOMMEND = "recommend";

    public static final String WORK_TIME[] = new String[]{"星期天", "工作日"};

    public static final String WORK_TYPE[] = new String[]{"派单", "礼仪", "促销", "家教", "模特", "演出", "其他"};

    public static String getWorkType(int pos) {
        return WORK_TYPE[pos];
    }

    public static final String SEX[] = new String[]{"不限", "男", "女"};

    public static String getSex(int pos) {
        return SEX[pos];
    }

    public static String getReleaseTime(Long relesaTime) {
        return relesaTime + "小时前";
    }

    public static String getSalary(double salary) {
        return salary + "元/小时";
    }

    public static final String[] AVATARS = {
            "http://tupian.qqjay.com/u/2011/0729/e755c434c91fed9f6f73152731788cb3.jpg",
            "http://99touxiang.com/public/upload/nvsheng/125/27-011820_433.jpg",
            "http://img1.touxiang.cn/uploads/allimg/111029/2330264224-36.png",
            "http://img1.2345.com/duoteimg/qqTxImg/2012/04/09/13339485237265.jpg",
            "http://diy.qqjay.com/u/files/2012/0523/f466c38e1c6c99ee2d6cd7746207a97a.jpg",
            "http://img1.touxiang.cn/uploads/20121224/24-054837_708.jpg",
            "http://img1.touxiang.cn/uploads/20121212/12-060125_658.jpg",
            "http://img1.touxiang.cn/uploads/20130608/08-054059_703.jpg",
            "http://diy.qqjay.com/u2/2013/0422/fadc08459b1ef5fc1ea6b5b8d22e44b4.jpg",
            "http://img1.2345.com/duoteimg/qqTxImg/2012/04/09/13339510584349.jpg",
            "http://img1.touxiang.cn/uploads/20130515/15-080722_514.jpg",
            "http://diy.qqjay.com/u2/2013/0401/4355c29b30d295b26da6f242a65bcaad.jpg"
    };

    public static String getAvatars(int id) {
        return AVATARS[id];
    }

    public static final int[] refreshColor = new int[]{R.color.secondary_color, R.color.primary_color,
            R.color.next_product_title_color, R.color.next_product_count_bg};

}
