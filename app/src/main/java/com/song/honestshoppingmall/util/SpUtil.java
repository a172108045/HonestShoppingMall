package com.song.honestshoppingmall.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Judy on 2016/12/27.
 */

public class SpUtil {
    public static final String SP_NAME = "config";
    private static SharedPreferences mSp;

    public static void saveBoolean(Context context, String key, boolean value) {
        if(mSp == null) {
            mSp = context.getSharedPreferences("SP_NAME", context.MODE_PRIVATE);
        }
        mSp.edit().putBoolean(key, value).apply();
    }

    public static Boolean getBoolean(Context context, String key, boolean defValue) {
        if(mSp == null) {
            mSp = context.getSharedPreferences("SP_NAME", context.MODE_PRIVATE);
        }
        return mSp.getBoolean(key, defValue);

    }

    public static void saveInt(Context context, String key, int value) {
        if(mSp == null) {
            mSp = context.getSharedPreferences("SP_NAME", context.MODE_PRIVATE);
        }
        mSp.edit().putInt(key, value).apply();
    }

    public static int getInt(Context context, String key, int defValue) {
        if(mSp == null) {
            mSp = context.getSharedPreferences("SP_NAME", context.MODE_PRIVATE);
        }
        return mSp.getInt(key, defValue);

    }

    public static void saveString(Context context, String key, String value) {
        if(mSp == null) {
            mSp = context.getSharedPreferences("SP_NAME", context.MODE_PRIVATE);
        }
        mSp.edit().putString(key, value).apply();
    }

    public static String getString(Context context, String key, String defValue) {
        if(mSp == null) {
            mSp = context.getSharedPreferences("SP_NAME", context.MODE_PRIVATE);
        }
        return mSp.getString(key, defValue);

    }
}
