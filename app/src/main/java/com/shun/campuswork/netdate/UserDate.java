package com.shun.campuswork.netdate;

import android.util.Log;

import com.shun.campuswork.tools.SharedPreferencesUtils;

/**
 * Created by shun99 on 2015/12/30.
 * 获取/更新  用户--密码  用户--用户信息
 */
public class UserDate {
    private static String USER_1 = "13243189659";

    public static String getPwd(String phone) {
        return SharedPreferencesUtils.getString(phone);
    }

    public static void setUserPwd(String phone, String pwd) {
        SharedPreferencesUtils.putString(phone, pwd);
    }


    public static String getUserInfo(String phone) {
        return SharedPreferencesUtils.getString("phone" + SharedPreferencesUtils.INFO);
    }

    public static void putUserInfo(String phone, String json) {
        SharedPreferencesUtils.putString(phone + SharedPreferencesUtils.INFO, json);
    }

    //=================================报名
    public static Boolean isSignUp(String phone, String signId) {
        String signIds = SharedPreferencesUtils.getString(phone + SharedPreferencesUtils.SIGNUP);
        Log.w("signIds",""+signIds);
        if (signIds.contains(signId)) {
            return true;
        }
        return false;
    }


    public static Boolean signUp(String phone, String signId) {
        String signIds = SharedPreferencesUtils.getString(phone + SharedPreferencesUtils.SIGNUP);
        if (!signIds.contains(signId)) {
            signIds = signIds + "_" + signId;
            SharedPreferencesUtils.putString(phone + SharedPreferencesUtils.SIGNUP, signIds);
            return true;
        }
        return false;
    }

    public static Boolean delSignUp(String phone, String signId) {
        String signIds = SharedPreferencesUtils.getString(phone + SharedPreferencesUtils.SIGNUP);
        if (signIds.contains(signId)) {
            signIds = signIds.replace(signId, "");
            SharedPreferencesUtils.putString(phone + SharedPreferencesUtils.SIGNUP, signIds);
            return true;
        }
        return false;
    }

    //=================================收藏
    public static Boolean isStar(String phone, String signId) {
        String stars = SharedPreferencesUtils.getString(phone + SharedPreferencesUtils.STAR);
        if (stars.contains(signId)) {
            return true;
        }
        return false;
    }

    public static Boolean star(String phone, String signId) {
        String stars = SharedPreferencesUtils.getString(phone + SharedPreferencesUtils.STAR);
        if (!stars.contains(signId)) {
            stars = stars + "_" + signId;
            SharedPreferencesUtils.putString(phone + SharedPreferencesUtils.STAR, stars);
            return true;
        }
        return false;
    }

    public static Boolean delStar(String phone, String signId) {
        String stars = SharedPreferencesUtils.getString(phone + SharedPreferencesUtils.STAR);
        if (stars.contains(signId)) {
            stars = stars.replace(signId, "");
            SharedPreferencesUtils.putString(phone + SharedPreferencesUtils.STAR, stars);
            return true;
        }
        return false;
    }
}
