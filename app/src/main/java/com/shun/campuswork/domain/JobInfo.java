package com.shun.campuswork.domain;

import java.util.Date;

/**
 * Created by shun99 on 2015/11/23.
 */
public class JobInfo {
    public Long id = System.currentTimeMillis();//标识
    public boolean isRecommend = false;//是否是推荐的

    public int type;//类型
    public boolean isLongWork;//长期兼职

    public String title;//标题
    public String city;//城市
    public Long releaseTime;//发布时间
    public double salary;//工资
    public int star;//评价

    public String area;//地区
    public String workDay = "面议";//工作日期
    public String workTime = "不限";//工作时段
    public int num;//工作人数
    public int sex;// 0 . 1 . 2

    public String company;
    public String desAddress;
    public String name;//联系人
    public String tel;//电话

    public JobInfo() {
    }

    @Override
    public String toString() {
        return "JobInfo{" +
                "type=" + type +
                ", isLongWork=" + isLongWork +
                ", title='" + title + '\'' +
                ", city='" + city + '\'' +
                ", releaseTime='" + releaseTime + '\'' +
                ", salary=" + salary +
                ", star=" + star +
                ", area='" + area + '\'' +
                ", workDay=" + workDay +
                ", workTime=" + workTime +
                ", num=" + num +
                ", sex=" + sex +
                ", company='" + company + '\'' +
                ", desAddress='" + desAddress + '\'' +
                ", name='" + name + '\'' +
                ", tel='" + tel + '\'' +
                '}';
    }
}
