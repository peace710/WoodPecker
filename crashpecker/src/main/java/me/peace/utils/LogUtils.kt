package me.peace.utils

import android.util.Log

object LogUtils {
    private const val CRASH_WOOD_PECKER = "WoodPecker"
    private val isDebugMode: Boolean
        private get() = true

    fun v(tag: String, msg: String) {
        if (isDebugMode) {
            Log.v(wrapTag(tag), wrapMsg(msg))
        }
    }

    fun w(tag: String, msg: String) {
        if (isDebugMode) {
            Log.w(wrapTag(tag), wrapMsg(msg))
        }
    }

    @JvmStatic
    fun i(tag: String, msg: String) {
        if (isDebugMode) {
            Log.i(wrapTag(tag), wrapMsg(msg))
        }
    }

    fun d(tag: String, msg: String) {
        if (isDebugMode) {
            Log.d(wrapTag(tag), wrapMsg(msg))
        }
    }

    @JvmStatic
    fun e(tag: String, msg: String) {
        if (isDebugMode) {
            Log.e(wrapTag(tag), wrapMsg(msg))
        }
    }

    private fun wrapMsg(msg: String): String {
        return msg + " [ThreadName --> " + Thread.currentThread().name + "] "
    }

    private fun wrapTag(tag: String): String {
        return CRASH_WOOD_PECKER + " [" + tag + "] "
    }
}