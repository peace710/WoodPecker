package me.peace.utils

import android.app.ActivityManager
import android.app.UiModeManager
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.os.BatteryManager
import android.telephony.TelephonyManager
import android.text.TextUtils
import java.io.PrintWriter
import java.io.StringWriter
import java.io.Writer
import java.util.*
import java.util.regex.Pattern
import kotlin.collections.ArrayList

object Utils {
    private val TAG = Utils::class.java.simpleName
    private const val PROCESS = ":woodpecker"
    fun isWoodpeckerRunning(context: Context): Boolean {
        val manager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val infoList = manager.runningAppProcesses
        for (info in infoList) {
            LogUtils.e(TAG, "processName = " + info.processName)
            if (info.processName.endsWith(PROCESS)) {
                LogUtils.e(TAG, "isWoodpeckerRunning true")
                return true
            }
        }
        LogUtils.e(TAG, "isWoodpeckerRunning false")
        return false
    }

    fun getStackTrace(throwable: Throwable): String {
        var throwable = throwable
        val writer: Writer = StringWriter()
        val printWriter = PrintWriter(writer)
        val cause = throwable.cause
        throwable = cause ?: throwable
        throwable.printStackTrace(printWriter)
        printWriter.close()
        return writer.toString()
    }

    fun getApplicationName(context: Context): String? {
        val packageManager = context.packageManager
        var applicationInfo: ApplicationInfo? = null
        var name: String? = null
        try {
            applicationInfo = packageManager.getApplicationInfo(
                context
                    .applicationInfo.packageName, 0
            )
            name = packageManager.getApplicationLabel(applicationInfo) as String
        } catch (e: PackageManager.NameNotFoundException) {
            val packages = context.packageName.split(".").toTypedArray()
            name = packages[packages.size - 1]
        }
        return name
    }

    fun list2String(list: ArrayList<String>?): String {
        if (list != null && list.size > 0) {
            var str = list.removeAt(0)
            for (i in list.indices) {
                str = str + "|" + list[i]
            }
            return str
        }
        return ""
    }

    fun string2List(str: String): ArrayList<String> {
        LogUtils.e(TAG, "string2List str ==> $str")
        val toTypedArray = str?.split("|").toTypedArray()
        return ArrayList(listOf(*toTypedArray))
    }

    fun isTvUiMode(context: Context?): Boolean {
        if (context != null) {
            val uiModeManager = context.getSystemService(Context.UI_MODE_SERVICE) as UiModeManager
            return if (uiModeManager.currentModeType == Configuration.UI_MODE_TYPE_TELEVISION) {
                LogUtils.e(TAG, "isTvUiMode true")
                true
            } else {
                LogUtils.e(TAG, "isTvUiMode false")
                false
            }
        }
        LogUtils.e(TAG, "isTvUiMode context null false")
        return false
    }

    fun checkTelephonyIsPhone(context: Context?): Boolean {
        if (context != null) {
            val manager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            return manager.phoneType != TelephonyManager.PHONE_TYPE_NONE
        }
        return false
    }

    fun checkBatteryIsPhone(context: Context?): Boolean {
        if (context != null) {
            val intentFilter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
            val batteryStatus = context.registerReceiver(null, intentFilter)
            val status = batteryStatus!!.getIntExtra(BatteryManager.EXTRA_STATUS, -1)
            val isCharging = status == BatteryManager.BATTERY_STATUS_FULL
            val chargePlug = batteryStatus.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1)
            val acCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_AC
            return !(isCharging && acCharge)
        }
        return false
    }

    fun isPhone(context: Context?): Boolean {
        return if (context != null) {
            checkBatteryIsPhone(context) && checkTelephonyIsPhone(
                context
            )
        } else false
    }

    operator fun contains(str: String?): Boolean {
        val regex = "^.*\\d{4}[-]([0][1-9]|(1[0-2]))[-](0[1-9]|([012]\\d)|(3[01]))([-])(" +
                "([0-1]{1}[0-9]{1})|([2]{1}[0-4]{1}))([-])(([0-5]{1}[0-9]{1}))([-])((" +
                "([0-5]{1}[0-9]{1}))).*$"
        return Pattern.matches(regex, str)
    }
}