package me.peace.communication

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import me.peace.communication.ProcessConstant.METHOD_LOAD_SP
import me.peace.communication.ProcessConstant.METHOD_SAVE_SP
import me.peace.communication.ProcessConstant.PARAM_TYPE_BOOLEAN
import me.peace.communication.ProcessConstant.PARAM_TYPE_INT
import me.peace.communication.ProcessConstant.PARAM_TYPE_STRING

class ProcessDispatcher {
    private fun getWoodPeckerProvider(context: Context): Uri {
        val packageName = context.packageName
        val authority = "content://$packageName.provider"
        return Uri.parse(authority)
    }

    fun saveSpString(context: Context?, spName: String?, key: String?, value: String?) {
        try {
            if (context != null && !TextUtils.isEmpty(spName) && !TextUtils.isEmpty(key) && !TextUtils.isEmpty(
                    value
                )
            ) {
                val bundle = Bundle()
                bundle.putString(ProcessConstant.METHOD_PARAMS_1, spName)
                bundle.putString(ProcessConstant.METHOD_PARAMS_2, key)
                bundle.putString(ProcessConstant.METHOD_PARAMS_3, value)
                context.contentResolver.call(
                    getWoodPeckerProvider(context), METHOD_SAVE_SP,
                    PARAM_TYPE_STRING, bundle
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun loadSpString(context: Context?, spName: String?, key: String?): String {
        var ret = ""
        try {
            if (context != null && !TextUtils.isEmpty(spName) && !TextUtils.isEmpty(key)) {
                val bundle = Bundle()
                bundle.putString(ProcessConstant.METHOD_PARAMS_1, spName)
                bundle.putString(ProcessConstant.METHOD_PARAMS_2, key)
                bundle.putString(ProcessConstant.METHOD_PARAMS_3, "")
                val result = context.contentResolver.call(
                    getWoodPeckerProvider(context), METHOD_LOAD_SP, PARAM_TYPE_STRING,
                    bundle
                )
                if (result != null) {
                    ret = result.getString(ProcessConstant.METHOD_RESULT, "")
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ret
    }

    fun loadSpString(
        context: Context?,
        spName: String?,
        key: String?,
        defaultValue: String?
    ): String {
        var ret = ""
        try {
            if (context != null && !TextUtils.isEmpty(spName) && !TextUtils.isEmpty(key)) {
                val bundle = Bundle()
                bundle.putString(ProcessConstant.METHOD_PARAMS_1, spName)
                bundle.putString(ProcessConstant.METHOD_PARAMS_2, key)
                bundle.putString(ProcessConstant.METHOD_PARAMS_3, defaultValue)
                val result = context.contentResolver.call(
                    getWoodPeckerProvider(context), METHOD_LOAD_SP, PARAM_TYPE_STRING,
                    bundle
                )
                if (result != null) {
                    ret = result.getString(ProcessConstant.METHOD_RESULT, defaultValue)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ret
    }

    fun saveSpInt(context: Context?, spName: String?, key: String?, value: Int) {
        try {
            if (context != null && !TextUtils.isEmpty(spName) && !TextUtils.isEmpty(key)) {
                val bundle = Bundle()
                bundle.putString(ProcessConstant.METHOD_PARAMS_1, spName)
                bundle.putString(ProcessConstant.METHOD_PARAMS_2, key)
                bundle.putInt(ProcessConstant.METHOD_PARAMS_3, value)
                context.contentResolver.call(
                    getWoodPeckerProvider(context),
                    METHOD_SAVE_SP,
                    PARAM_TYPE_INT,
                    bundle
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun loadSpInt(context: Context?, spName: String?, key: String?): Int {
        var ret = 0
        try {
            if (context != null && !TextUtils.isEmpty(spName) && !TextUtils.isEmpty(key)) {
                val bundle = Bundle()
                bundle.putString(ProcessConstant.METHOD_PARAMS_1, spName)
                bundle.putString(ProcessConstant.METHOD_PARAMS_2, key)
                bundle.putInt(ProcessConstant.METHOD_PARAMS_3, 0)
                val result = context.contentResolver.call(
                    getWoodPeckerProvider(context), METHOD_LOAD_SP, PARAM_TYPE_INT,
                    bundle
                )
                if (result != null) {
                    ret = result.getInt(ProcessConstant.METHOD_RESULT, 0)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ret
    }

    fun loadSpInt(context: Context?, spName: String?, key: String?, defaultValue: Int): Int {
        var ret = 0
        try {
            if (context != null && !TextUtils.isEmpty(spName) && !TextUtils.isEmpty(key)) {
                val bundle = Bundle()
                bundle.putString(ProcessConstant.METHOD_PARAMS_1, spName)
                bundle.putString(ProcessConstant.METHOD_PARAMS_2, key)
                bundle.putInt(ProcessConstant.METHOD_PARAMS_3, defaultValue)
                val result = context.contentResolver.call(
                    getWoodPeckerProvider(context), METHOD_LOAD_SP, PARAM_TYPE_INT,
                    bundle
                )
                if (result != null) {
                    ret = result.getInt(ProcessConstant.METHOD_RESULT, defaultValue)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ret
    }

    fun saveSpBoolean(context: Context?, spName: String?, key: String?, value: Boolean) {
        try {
            if (context != null && !TextUtils.isEmpty(spName) && !TextUtils.isEmpty(key)) {
                val bundle = Bundle()
                bundle.putString(ProcessConstant.METHOD_PARAMS_1, spName)
                bundle.putString(ProcessConstant.METHOD_PARAMS_2, key)
                bundle.putBoolean(ProcessConstant.METHOD_PARAMS_3, value)
                context.contentResolver.call(
                    getWoodPeckerProvider(context),
                    METHOD_SAVE_SP,
                    PARAM_TYPE_BOOLEAN,
                    bundle
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun loadSpBoolean(context: Context?, spName: String?, key: String?): Boolean {
        var ret = false
        try {
            if (context != null && !TextUtils.isEmpty(spName) && !TextUtils.isEmpty(key)) {
                val bundle = Bundle()
                bundle.putString(ProcessConstant.METHOD_PARAMS_1, spName)
                bundle.putString(ProcessConstant.METHOD_PARAMS_2, key)
                bundle.putBoolean(ProcessConstant.METHOD_PARAMS_3, false)
                val result = context.contentResolver.call(
                    getWoodPeckerProvider(context), METHOD_LOAD_SP, PARAM_TYPE_BOOLEAN,
                    bundle
                )
                if (result != null) {
                    ret = result.getBoolean(ProcessConstant.METHOD_RESULT, false)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ret
    }

    fun loadSpBoolean(
        context: Context?,
        spName: String?,
        key: String?,
        defaultValue: Boolean
    ): Boolean {
        var ret = false
        try {
            if (context != null && !TextUtils.isEmpty(spName) && !TextUtils.isEmpty(key)) {
                val bundle = Bundle()
                bundle.putString(ProcessConstant.METHOD_PARAMS_1, spName)
                bundle.putString(ProcessConstant.METHOD_PARAMS_2, key)
                bundle.putBoolean(ProcessConstant.METHOD_PARAMS_3, defaultValue)
                val result = context.contentResolver.call(
                    getWoodPeckerProvider(context), METHOD_LOAD_SP, PARAM_TYPE_BOOLEAN,
                    bundle
                )
                if (result != null) {
                    ret = result.getBoolean(ProcessConstant.METHOD_RESULT, defaultValue)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ret
    }
}