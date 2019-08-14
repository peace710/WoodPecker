package me.peace.communication;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import me.peace.utils.SharedPrefrencesUtils;

import static me.peace.communication.ProcessConstant.PARAM_TYPE_BOOLEAN;
import static me.peace.communication.ProcessConstant.PARAM_TYPE_INT;
import static me.peace.communication.ProcessConstant.PARAM_TYPE_STRING;

public class ProcessCoordinator {
    public void saveSharedPreferences(Context context, @Nullable String arg,
                                      @Nullable Bundle extras){
        if (context != null && extras != null && !TextUtils.isEmpty(arg)) {
            String spName = extras.getString(ProcessConstant.METHOD_PARAMS_1);
            String key = extras.getString(ProcessConstant.METHOD_PARAMS_2);
            if (TextUtils.equals(PARAM_TYPE_STRING, arg)) {
                String value = extras.getString(ProcessConstant.METHOD_PARAMS_3);
                SharedPrefrencesUtils.editStringValue(context,spName,key,value);
            } else if (TextUtils.equals(PARAM_TYPE_INT, arg)) {
                int value = extras.getInt(ProcessConstant.METHOD_PARAMS_3,0);
                SharedPrefrencesUtils.editIntValue(context,spName,key,value);
            } else if (TextUtils.equals(PARAM_TYPE_BOOLEAN, arg)) {
                boolean value = extras.getBoolean(ProcessConstant.METHOD_PARAMS_3,false);
                SharedPrefrencesUtils.editBooleanValue(context,spName,key,value);
            }
        }
    }

    public Bundle loadSharedPreferences(Context context,@Nullable String arg,
                                        @Nullable Bundle extras){
        if (context != null && extras != null && !TextUtils.isEmpty(arg)) {
            String spName = extras.getString(ProcessConstant.METHOD_PARAMS_1);
            String key = extras.getString(ProcessConstant.METHOD_PARAMS_2);
            if (TextUtils.equals(PARAM_TYPE_STRING, arg)) {
                String defaultValue = extras.getString(ProcessConstant
                    .METHOD_PARAMS_3);
                Bundle resultBundle = new Bundle();
                resultBundle.putString(ProcessConstant.METHOD_RESULT,
                    SharedPrefrencesUtils.getStringValue(context,spName, key, defaultValue));
                return resultBundle;
            } else if (TextUtils.equals(PARAM_TYPE_INT, arg)) {
                int defaultValue = extras.getInt(ProcessConstant
                    .METHOD_PARAMS_3);
                Bundle resultBundle = new Bundle();
                resultBundle.putInt(ProcessConstant.METHOD_RESULT,
                    SharedPrefrencesUtils.getIntValue(context,spName, key, defaultValue));
                return resultBundle;
            } else if (TextUtils.equals(PARAM_TYPE_BOOLEAN, arg)) {
                boolean defaultValue = extras.getBoolean(ProcessConstant
                    .METHOD_PARAMS_3);
                Bundle resultBundle = new Bundle();
                resultBundle.putBoolean(ProcessConstant.METHOD_RESULT,
                    SharedPrefrencesUtils.getBooleanValue(context,spName, key, defaultValue));
                return resultBundle;
            }
        }
        return null;
    }
}
