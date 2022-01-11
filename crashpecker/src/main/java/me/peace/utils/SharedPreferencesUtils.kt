package me.peace.utils

import android.content.Context
import android.content.SharedPreferences
import android.text.TextUtils

object SharedPreferencesUtils {
    fun getSharedPreferences(context: Context?, name: String?): SharedPreferences? {
        if (context == null) {
            return null
        }
        return if (TextUtils.isEmpty(name)) {
            null
        } else context.getSharedPreferences(
            name,
            Context.MODE_PRIVATE
        )
    }

    fun editIntValue(context: Context?, spName: String?, keyName: String?, value: Int) {
        if (TextUtils.isEmpty(spName) || TextUtils.isEmpty(keyName)) {
            return
        }
        val sp = getSharedPreferences(context, spName)
        if (sp != null) {
            val editor = sp.edit()
            editor.putInt(keyName, value)
            editor.apply()
        }
    }

    fun getIntValue(context: Context?, spName: String?, keyName: String?, defaultValue: Int): Int {
        if (TextUtils.isEmpty(spName) || TextUtils.isEmpty(keyName)) {
            return defaultValue
        }
        val sp = getSharedPreferences(context, spName) ?: return defaultValue
        return sp.getInt(keyName, defaultValue)
    }

    fun editBooleanValue(context: Context?, spName: String?, keyName: String?, value: Boolean) {
        if (TextUtils.isEmpty(spName) || TextUtils.isEmpty(keyName)) {
            return
        }
        val sp = getSharedPreferences(context, spName)
        if (sp != null) {
            val editor = sp.edit()
            editor.putBoolean(keyName, value)
            editor.apply()
        }
    }

    fun getBooleanValue(
        context: Context?,
        spName: String?,
        keyName: String?,
        defaultValue: Boolean
    ): Boolean {
        if (TextUtils.isEmpty(spName) || TextUtils.isEmpty(keyName)) {
            return defaultValue
        }
        val sp = getSharedPreferences(context, spName) ?: return defaultValue
        return sp.getBoolean(keyName, defaultValue)
    }

    fun editStringValue(context: Context?, spName: String?, keyName: String?, value: String?) {
        if (TextUtils.isEmpty(spName) || TextUtils.isEmpty(keyName)) {
            return
        }
        val sp = getSharedPreferences(context, spName)
        if (sp != null) {
            val editor = sp.edit()
            editor.putString(keyName, value)
            editor.apply()
        }
    }

    fun getStringValue(
        context: Context?,
        spName: String?,
        keyName: String?,
        defaultValue: String?
    ): String? {
        if (TextUtils.isEmpty(spName) || TextUtils.isEmpty(keyName)) {
            return defaultValue
        }
        val sp = getSharedPreferences(context, spName) ?: return defaultValue
        return sp.getString(keyName, defaultValue)
    }
}