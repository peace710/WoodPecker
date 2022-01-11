package me.peace.communication

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import me.peace.communication.ProcessConstant.PARAM_TYPE_BOOLEAN
import me.peace.communication.ProcessConstant.PARAM_TYPE_INT
import me.peace.communication.ProcessConstant.PARAM_TYPE_STRING
import me.peace.utils.SharedPreferencesUtils

class ProcessCoordinator {
    fun saveSharedPreferences(
        context: Context?, arg: String?,
        extras: Bundle?
    ) {
        if (context != null && extras != null && !TextUtils.isEmpty(arg)) {
            val spName = extras.getString(ProcessConstant.METHOD_PARAMS_1)
            val key = extras.getString(ProcessConstant.METHOD_PARAMS_2)
            if (TextUtils.equals(PARAM_TYPE_STRING, arg)) {
                val value = extras.getString(ProcessConstant.METHOD_PARAMS_3)
                SharedPreferencesUtils.editStringValue(context, spName, key, value)
            } else if (TextUtils.equals(PARAM_TYPE_INT, arg)) {
                val value = extras.getInt(ProcessConstant.METHOD_PARAMS_3, 0)
                SharedPreferencesUtils.editIntValue(context, spName, key, value)
            } else if (TextUtils.equals(PARAM_TYPE_BOOLEAN, arg)) {
                val value = extras.getBoolean(ProcessConstant.METHOD_PARAMS_3, false)
                SharedPreferencesUtils.editBooleanValue(context, spName, key, value)
            }
        }
    }

    fun loadSharedPreferences(
        context: Context?, arg: String?,
        extras: Bundle?
    ): Bundle? {
        if (context != null && extras != null && !TextUtils.isEmpty(arg)) {
            val spName = extras.getString(ProcessConstant.METHOD_PARAMS_1)
            val key = extras.getString(ProcessConstant.METHOD_PARAMS_2)
            if (TextUtils.equals(PARAM_TYPE_STRING, arg)) {
                val defaultValue = extras.getString(ProcessConstant.METHOD_PARAMS_3)
                val resultBundle = Bundle()
                resultBundle.putString(
                    ProcessConstant.METHOD_RESULT,
                    SharedPreferencesUtils.getStringValue(context, spName, key, defaultValue)
                )
                return resultBundle
            } else if (TextUtils.equals(PARAM_TYPE_INT, arg)) {
                val defaultValue = extras.getInt(ProcessConstant.METHOD_PARAMS_3)
                val resultBundle = Bundle()
                resultBundle.putInt(
                    ProcessConstant.METHOD_RESULT,
                    SharedPreferencesUtils.getIntValue(context, spName, key, defaultValue)
                )
                return resultBundle
            } else if (TextUtils.equals(PARAM_TYPE_BOOLEAN, arg)) {
                val defaultValue = extras.getBoolean(ProcessConstant.METHOD_PARAMS_3)
                val resultBundle = Bundle()
                resultBundle.putBoolean(
                    ProcessConstant.METHOD_RESULT,
                    SharedPreferencesUtils.getBooleanValue(context, spName, key, defaultValue)
                )
                return resultBundle
            }
        }
        return null
    }
}