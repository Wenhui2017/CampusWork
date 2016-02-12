package com.shun.campuswork.domain;

/**
 * Created by shun99 on 2015/12/12.
 */
public class UserInfo {
    public String phone;//唯一标识
    public String user = "O_O";//用户名
    public String name = "";//真是姓名
    public String headUrl = "";//头像
    public String sex = "男";//性别
    public String emil = "";//邮件
    public String school = "";
    public String id = "";//身份证
    public String experice = "";//个人
    public String sign = "只要付出,一切随缘.";

    @Override
    public String toString() {
        return "UserInfo{" +
                "phone='" + phone + '\'' +
                ", user='" + user + '\'' +
                ", headUrl='" + headUrl + '\'' +
                ", emil='" + emil + '\'' +
                '}';
    }
}
