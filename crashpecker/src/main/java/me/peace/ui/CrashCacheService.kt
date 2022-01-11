package me.peace.ui

import android.app.IntentService
import android.content.Intent
import me.peace.communication.ProcessDispatcher
import me.peace.constant.Constant
import me.peace.utils.CrashUtils
import me.peace.utils.LogUtils
import me.peace.utils.Utils

class CrashCacheService : IntentService(NAME) {
    override fun onCreate() {
        super.onCreate()
        LogUtils.e(TAG, "service onCreate")
    }

    override fun onStart(intent: Intent?, startId: Int) {
        super.onStart(intent, startId)
        LogUtils.e(TAG, "service onStart")
    }

    override fun onHandleIntent(intent: Intent?) {
        LogUtils.e(TAG, "service onHandleIntent")
        val appName = intent!!.getStringExtra(Constant.KEY_APP_NAME)
        val crashDate = intent.getStringExtra(Constant.KEY_CRASH_DATE)
        val crashRecordCount = intent.getIntExtra(
            Constant.KEY_CRASH_RECORD_COUNT,
            Constant.DEFAULT_MAX_SAVE_FILE_COUNT
        )
        val keys = intent.getStringArrayListExtra(Constant.KEY_HIGH_LIGHT)
        val throwable = intent.getSerializableExtra(Constant.KEY_THROWABLE) as Throwable?
        val dispatcher = ProcessDispatcher()
        dispatcher.saveSpString(
            this, Constant.WOOD_PECKER_SP,
            Constant.KEY_HIGH_LIGHT, Utils.list2String(keys)
        )
        dispatcher.saveSpString(this, Constant.WOOD_PECKER_SP, Constant.KEY_APP_NAME, appName)
        if (throwable != null) {
            if (crashDate != null) {
                CrashUtils.save(this, throwable, crashDate, crashRecordCount)
            }
        }
        stopSelf()
        LogUtils.e(TAG, "service onHandleIntent end")
    }

    override fun onDestroy() {
        super.onDestroy()
        LogUtils.e(TAG, "service onDestroy")
    }

    companion object {
        private val NAME = CrashCacheService::class.java.simpleName
        private val TAG = CrashCacheService::class.java.simpleName
    }
}