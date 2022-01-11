package me.peace.engine

import android.content.Context
import android.content.Intent
import android.text.TextUtils
import me.peace.constant.Constant
import me.peace.engine.WoodPecker
import me.peace.ui.CrashCacheService
import me.peace.ui.WoodPeckerActivity
import me.peace.utils.LogUtils
import me.peace.utils.Utils
import java.text.SimpleDateFormat
import java.util.*

class WoodPecker private constructor() : Thread.UncaughtExceptionHandler {
    private var defaultUncaughtExceptionHandler: Thread.UncaughtExceptionHandler? = null
    private var crashing = false
    private var applicationContext: Context? = null
    private val keys: ArrayList<String> = ArrayList()
    private var jump = true
    private var crashRecordCount: Int

    private object WoodPeckerHolder {
        val instance = WoodPecker()
    }

    fun fly(context: Context) {
        applicationContext = context.applicationContext
        if (!Utils.isWoodpeckerRunning(context)) {
            LogUtils.e(TAG, "WoodPecker start to fly")
            addDefaultKey()
            turnOnHandler()
        }
    }

    fun with(keys: ArrayList<String>?): WoodPecker {
        if (keys != null) {
            this.keys.addAll(keys)
        }
        return this
    }

    fun jump(jump: Boolean): WoodPecker {
        this.jump = jump
        return this
    }

    fun count(count: Int): WoodPecker {
        crashRecordCount = if (count > 0) count else Constant.DEFAULT_MAX_SAVE_FILE_COUNT
        return this
    }

    private fun addDefaultKey() {
        keys.add(applicationContext!!.packageName)
    }

    private fun turnOnHandler() {
        val defaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler()
        if (this !== defaultUncaughtExceptionHandler) {
            this.defaultUncaughtExceptionHandler = defaultUncaughtExceptionHandler
            Thread.setDefaultUncaughtExceptionHandler(this)
        }
    }

    override fun uncaughtException(thread: Thread, throwable: Throwable) {
        if (crashing) {
            return
        }
        crashing = true
        handleException(thread, throwable)
        if (defaultUncaughtExceptionHandler != null) {
            defaultUncaughtExceptionHandler!!.uncaughtException(thread, throwable)
        }
    }

    private fun handleException(thread: Thread, throwable: Throwable) {
        val traces = trace(throwable)
        val appName = applicationContext?.let { Utils.getApplicationName(it) }
        val crashDate = Date()
        val crashTime = crashTime(crashDate)
        if (appName != null) {
            startCrashCacheService(throwable, crashDate, appName)
        }
        if (jump) {
            if (appName != null) {
                startWoodPecker(traces, appName, crashTime)
            }
        }
    }

    private fun startWoodPecker(traces: ArrayList<String>, appName: String, crashTime: String) {
        val intent = Intent(applicationContext, WoodPeckerActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.putExtra(Constant.KEY_TRACES, traces)
        intent.putExtra(Constant.KEY_CRASH_TIME, crashTime)
        intent.putExtra(Constant.KEY_HIGH_LIGHT, keys)
        intent.putExtra(Constant.KEY_WITH_TRACE, true)
        intent.putExtra(Constant.KEY_APP_NAME, appName)
        applicationContext!!.startActivity(intent)
    }

    private fun trace(throwable: Throwable): ArrayList<String> {
        val trace = Utils.getStackTrace(throwable)
        if (!TextUtils.isEmpty(trace)) {
            val traces = trace.split("\n").toTypedArray()
            return ArrayList(Arrays.asList(*traces))
        }
        return ArrayList()
    }

    private fun crashTime(crashDate: Date): String {
        val format = SimpleDateFormat("yyyy.MM.dd HH:mm:ss")
        return format.format(crashDate)
    }

    private fun startCrashCacheService(throwable: Throwable, crashDate: Date, appName: String) {
        val format = SimpleDateFormat("yyyy-MM-dd-HH-mm-ss")
        val date = format.format(crashDate)
        val intent = Intent(applicationContext, CrashCacheService::class.java)
        intent.putExtra(Constant.KEY_APP_NAME, appName)
        intent.putExtra(Constant.KEY_CRASH_DATE, date)
        intent.putExtra(Constant.KEY_HIGH_LIGHT, keys)
        intent.putExtra(Constant.KEY_CRASH_RECORD_COUNT, crashRecordCount)
        intent.putExtra(Constant.KEY_THROWABLE, throwable)
        intent.setPackage(applicationContext!!.packageName)
        applicationContext!!.startService(intent)
    }

    companion object {
        private val TAG = WoodPecker::class.java.simpleName
        @JvmStatic
        fun instance(): WoodPecker {
            return WoodPeckerHolder.instance
        }
    }

    init {
        crashRecordCount = Constant.DEFAULT_MAX_SAVE_FILE_COUNT
    }
}