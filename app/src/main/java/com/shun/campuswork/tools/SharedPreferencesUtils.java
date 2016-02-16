package com.shun.campuswork.tools;
import android.content.SharedPreferences;

/**
 * Created by shun99 on 2015/12/12.
 */
public class SharedPreferencesUtils {
    public static final String SP_NAME = "config";

    private static SharedPreferences getSharedPreferences() {
        return UiUtils.getContext().getSharedPreferences(SP_NAME, 0);
    }

    public static void putBoolean(String key, boolean value) {
        SharedPreferencesUtils.getSharedPreferences().edit().putBoolean(key, value).commit();
    }

    public static void putString(String key, String value) {
        SharedPreferencesUtils.getSharedPreferences().edit().putString(key, value).commit();
    }

    public static boolean getBoolean(String key, boolean defValue) {
        return SharedPreferencesUtils.getSharedPreferences().getBoolean(key, defValue);
    }

    /**
     * 没有结果时 默认返回空
     * @param key
     * @return
     */
    public static String getString(String key) {
        return SharedPreferencesUtils.getSharedPreferences().getString(key, "");
    }


}
