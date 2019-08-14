package me.peace.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

public class SharedPrefrencesUtils {
    public static SharedPreferences getSharedPreferences(Context context,String name) {
        if (context == null){
            return null;
        }

        if (TextUtils.isEmpty(name)) {
            return null;
        }

        return context.getSharedPreferences(name, Context
            .MODE_PRIVATE);
    }

    public static void editIntValue(Context context,String spName, String keyName, int value) {
        if (TextUtils.isEmpty(spName) || TextUtils.isEmpty(keyName)) {
            return;
        }
        SharedPreferences sp = getSharedPreferences(context,spName);
        if (sp != null) {
            SharedPreferences.Editor editor = sp.edit();
            editor.putInt(keyName, value);
            editor.apply();
        }
    }

    public static int getIntValue(Context context,String spName, String keyName, int defaultValue) {
        if (TextUtils.isEmpty(spName) || TextUtils.isEmpty(keyName)) {
            return defaultValue;
        }
        SharedPreferences sp = getSharedPreferences(context,spName);
        if (sp == null) {
            return defaultValue;
        }
        return sp.getInt(keyName, defaultValue);
    }

    public static void editBooleanValue(Context context,String spName, String keyName, boolean value) {
        if (TextUtils.isEmpty(spName) || TextUtils.isEmpty(keyName)) {
            return;
        }
        SharedPreferences sp = getSharedPreferences(context,spName);
        if (sp != null) {
            SharedPreferences.Editor editor = sp.edit();
            editor.putBoolean(keyName, value);
            editor.apply();
        }
    }

    public static boolean getBooleanValue(Context context,String spName, String keyName, boolean defaultValue) {
        if (TextUtils.isEmpty(spName) || TextUtils.isEmpty(keyName)) {
            return defaultValue;
        }
        SharedPreferences sp = getSharedPreferences(context,spName);
        if (sp == null) {
            return defaultValue;
        }
        return sp.getBoolean(keyName, defaultValue);
    }

    public static void editStringValue(Context context,String spName, String keyName, String value) {
        if (TextUtils.isEmpty(spName) || TextUtils.isEmpty(keyName)) {
            return;
        }
        SharedPreferences sp = getSharedPreferences(context,spName);
        if (sp != null) {
            SharedPreferences.Editor editor = sp.edit();
            editor.putString(keyName, value);
            editor.apply();
        }
    }

    public static String getStringValue(Context context,String spName, String keyName, String defaultValue) {
        if (TextUtils.isEmpty(spName) || TextUtils.isEmpty(keyName)) {
            return defaultValue;
        }
        SharedPreferences sp = getSharedPreferences(context,spName);
        if (sp == null) {
            return defaultValue;
        }
        return sp.getString(keyName, defaultValue);
    }
}
