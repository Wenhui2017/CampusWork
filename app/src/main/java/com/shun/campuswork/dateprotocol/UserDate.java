package com.shun.campuswork.dateprotocol;

import android.util.Log;

import com.shun.campuswork.global.GlobalContants;
import com.shun.campuswork.tools.SharedPreferencesUtils;

/**
 * 关于用户数据的操作
 */
public class UserDate {
    //===============================正在登入的用户
    public static String getLoggingUser() {
        return SharedPreferencesUtils.getString(GlobalContants.LOGGING_USER);
    }

    public static void putLoggingUser(String user) {
        SharedPreferencesUtils.putString(GlobalContants.LOGGING_USER, user);
    }

    //===============================曾经登入的用户
    public static String getLoggedUser() {
        return SharedPreferencesUtils.getString(GlobalContants.LOGGED_USER);
    }

    public static void putLoggedUser(String user) {
        SharedPreferencesUtils.putString(GlobalContants.LOGGED_USER, user);
    }


    //===============================获取记住的密码
    public static String getUserPwd(String user) {
        return SharedPreferencesUtils.getString(user + GlobalContants.REM_PWD);
    }

    public static void putUserPwd(String user, String pwd) {
        SharedPreferencesUtils.putString(user + GlobalContants.REM_PWD, pwd);
    }


    //===============================获取记住的密码
    public static String getUserInfo(String user) {
        return SharedPreferencesUtils.getString(user + GlobalContants.INFO);
    }

    public static void putUserInfo(String user, String json) {
        SharedPreferencesUtils.putString(user + GlobalContants.INFO, json);
    }

    public static void updateUserInfo(String user, String json) {
        SharedPreferencesUtils.putString(user + GlobalContants.INFO, json);
        //.......更新网络数据的操作
    }

    //=================================报名
    public static Boolean isEnroll(String user, String signId) {
        String signIds = SharedPreferencesUtils.getString(user + GlobalContants.ENROLL);
        Log.w("signId", "" + signId);
        Log.w("signIds", "" + signIds);
        if (signIds.contains(signId)) {
            return true;
        }
        return false;
    }


    public static Boolean enroll(String user, String signId) {
        String signIds = SharedPreferencesUtils.getString(user + GlobalContants.ENROLL);
        if (!signIds.contains(signId)) {
            signIds = signIds + "_" + signId;
            SharedPreferencesUtils.putString(user + GlobalContants.ENROLL, signIds);
            return true;
        }
        return false;
    }

    public static Boolean escEnroll(String user, String signId) {
        String signIds = SharedPreferencesUtils.getString(user + GlobalContants.ENROLL);
        if (signIds.contains(signId)) {
            signIds = signIds.replace(signId, "");
            SharedPreferencesUtils.putString(user + GlobalContants.ENROLL, signIds);
            return true;
        }
        return false;
    }

    //=================================收藏
    public static Boolean isStar(String user, String signId) {
        String stars = SharedPreferencesUtils.getString(user + GlobalContants.STAR);
        if (stars.contains(signId)) {
            return true;
        }
        return false;
    }

    public static Boolean star(String user, String signId) {
        String stars = SharedPreferencesUtils.getString(user + GlobalContants.STAR);
        if (!stars.contains(signId)) {
            stars = stars + "_" + signId;
            SharedPreferencesUtils.putString(user + GlobalContants.STAR, stars);
            return true;
        }
        return false;
    }

    public static Boolean escStar(String user, String signId) {
        String stars = SharedPreferencesUtils.getString(user + GlobalContants.STAR);
        if (stars.contains(signId)) {
            stars = stars.replace(signId, "");
            SharedPreferencesUtils.putString(user + GlobalContants.STAR, stars);
            return true;
        }
        return false;
    }
}
