package me.peace.communication;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;

import static me.peace.communication.ProcessConstant.METHOD_LOAD_SP;
import static me.peace.communication.ProcessConstant.METHOD_SAVE_SP;
import static me.peace.communication.ProcessConstant.PARAM_TYPE_BOOLEAN;
import static me.peace.communication.ProcessConstant.PARAM_TYPE_INT;
import static me.peace.communication.ProcessConstant.PARAM_TYPE_STRING;

public class ProcessDispatcher {
    private Uri getWoodPeckerProvider(Context context){
        String packageName = context.getPackageName();
        String authority = "content://" + packageName + ".provider";
        return Uri.parse(authority);
    }

    public  void saveSpString(Context context, String spName, String key, String value) {
        try {
            if (context != null && !TextUtils.isEmpty(spName) && !TextUtils.isEmpty(key) && !TextUtils.isEmpty(value)) {
                Bundle bundle = new Bundle();
                bundle.putString(ProcessConstant.METHOD_PARAMS_1, spName);
                bundle.putString(ProcessConstant.METHOD_PARAMS_2, key);
                bundle.putString(ProcessConstant.METHOD_PARAMS_3, value);
                context.getContentResolver().call(getWoodPeckerProvider(context), METHOD_SAVE_SP,
                    PARAM_TYPE_STRING, bundle);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public  String loadSpString(Context context, String spName,String key) {
        String ret = "";
        try {
            if (context != null && !TextUtils.isEmpty(spName) && !TextUtils.isEmpty(key)) {
                Bundle bundle = new Bundle();
                bundle.putString(ProcessConstant.METHOD_PARAMS_1, spName);
                bundle.putString(ProcessConstant.METHOD_PARAMS_2, key);
                bundle.putString(ProcessConstant.METHOD_PARAMS_3, "");
                Bundle result = context.getContentResolver().call(getWoodPeckerProvider(context), METHOD_LOAD_SP, PARAM_TYPE_STRING,
                        bundle);
                if (result != null) {
                    ret = result.getString(ProcessConstant.METHOD_RESULT, "");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    public  String loadSpString(Context context, String spName,String key,String defaultValue) {
        String ret = "";
        try {
            if (context != null && !TextUtils.isEmpty(spName) && !TextUtils.isEmpty(key)) {
                Bundle bundle = new Bundle();
                bundle.putString(ProcessConstant.METHOD_PARAMS_1, spName);
                bundle.putString(ProcessConstant.METHOD_PARAMS_2, key);
                bundle.putString(ProcessConstant.METHOD_PARAMS_3, defaultValue);
                Bundle result = context.getContentResolver().call(getWoodPeckerProvider(context), METHOD_LOAD_SP, PARAM_TYPE_STRING,
                        bundle);
                if (result != null) {
                    ret = result.getString(ProcessConstant.METHOD_RESULT, defaultValue);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    public  void saveSpInt(Context context,  String spName, String key,  int value) {
        try {
            if (context != null && !TextUtils.isEmpty(spName) && !TextUtils.isEmpty(key)) {
                Bundle bundle = new Bundle();
                bundle.putString(ProcessConstant.METHOD_PARAMS_1, spName);
                bundle.putString(ProcessConstant.METHOD_PARAMS_2, key);
                bundle.putInt(ProcessConstant.METHOD_PARAMS_3, value);
                context.getContentResolver().call(getWoodPeckerProvider(context), METHOD_SAVE_SP, PARAM_TYPE_INT, bundle);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public  int loadSpInt(Context context, String spName,String key) {
        int ret = 0;
        try {
            if (context != null && !TextUtils.isEmpty(spName) && !TextUtils.isEmpty(key)) {
                Bundle bundle = new Bundle();
                bundle.putString(ProcessConstant.METHOD_PARAMS_1, spName);
                bundle.putString(ProcessConstant.METHOD_PARAMS_2, key);
                bundle.putInt(ProcessConstant.METHOD_PARAMS_3, 0);
                Bundle result = context.getContentResolver().call(getWoodPeckerProvider(context), METHOD_LOAD_SP, PARAM_TYPE_INT,
                        bundle);
                if (result != null) {
                    ret = result.getInt(ProcessConstant.METHOD_RESULT, 0);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    public  int loadSpInt(Context context, String spName,String key,int defaultValue) {
        int ret = 0;
        try {
            if (context != null && !TextUtils.isEmpty(spName) && !TextUtils.isEmpty(key)) {
                Bundle bundle = new Bundle();
                bundle.putString(ProcessConstant.METHOD_PARAMS_1, spName);
                bundle.putString(ProcessConstant.METHOD_PARAMS_2, key);
                bundle.putInt(ProcessConstant.METHOD_PARAMS_3, defaultValue);
                Bundle result = context.getContentResolver().call(getWoodPeckerProvider(context), METHOD_LOAD_SP, PARAM_TYPE_INT,
                        bundle);
                if (result != null) {
                    ret = result.getInt(ProcessConstant.METHOD_RESULT, defaultValue);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    public  void saveSpBoolean(Context context,  String spName, String key,  boolean value) {
        try {
            if (context != null && !TextUtils.isEmpty(spName) && !TextUtils.isEmpty(key)) {
                Bundle bundle = new Bundle();
                bundle.putString(ProcessConstant.METHOD_PARAMS_1, spName);
                bundle.putString(ProcessConstant.METHOD_PARAMS_2, key);
                bundle.putBoolean(ProcessConstant.METHOD_PARAMS_3, value);
                context.getContentResolver().call(getWoodPeckerProvider(context), METHOD_SAVE_SP, PARAM_TYPE_BOOLEAN, bundle);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public  boolean loadSpBoolean(Context context, String spName,String key) {
        boolean ret = false;
        try {
            if (context != null && !TextUtils.isEmpty(spName) && !TextUtils.isEmpty(key)) {
                Bundle bundle = new Bundle();
                bundle.putString(ProcessConstant.METHOD_PARAMS_1, spName);
                bundle.putString(ProcessConstant.METHOD_PARAMS_2, key);
                bundle.putBoolean(ProcessConstant.METHOD_PARAMS_3, false);
                Bundle result = context.getContentResolver().call(getWoodPeckerProvider(context), METHOD_LOAD_SP, PARAM_TYPE_BOOLEAN,
                        bundle);
                if (result != null) {
                    ret = result.getBoolean(ProcessConstant.METHOD_RESULT, false);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    public  boolean loadSpBoolean(Context context, String spName,String key,boolean defaultValue) {
        boolean ret = false;
        try {
            if (context != null && !TextUtils.isEmpty(spName) && !TextUtils.isEmpty(key)) {
                Bundle bundle = new Bundle();
                bundle.putString(ProcessConstant.METHOD_PARAMS_1, spName);
                bundle.putString(ProcessConstant.METHOD_PARAMS_2, key);
                bundle.putBoolean(ProcessConstant.METHOD_PARAMS_3, defaultValue);
                Bundle result = context.getContentResolver().call(getWoodPeckerProvider(context), METHOD_LOAD_SP, PARAM_TYPE_BOOLEAN,
                        bundle);
                if (result != null) {
                    ret = result.getBoolean(ProcessConstant.METHOD_RESULT, defaultValue);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }
}
