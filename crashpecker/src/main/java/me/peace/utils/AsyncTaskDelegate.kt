package me.peace.utils

import android.annotation.SuppressLint
import android.os.AsyncTask
import android.os.Build
import java.io.IOException
import java.util.concurrent.Executors
import java.util.concurrent.RejectedExecutionException

object AsyncTaskDelegate {
    private val TAG = AsyncTaskDelegate::class.java.simpleName
    private val sExecutor = Executors.newCachedThreadPool()
    @SuppressLint("NewApi")
    @Throws(IOException::class)
    fun execute(
        task: AsyncTask<String?, *, *>,
        vararg params: String?
    ) {
        try {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
                task.execute(*params)
            } else {
                task.executeOnExecutor(sExecutor, *params)
            }
        } catch (e: RejectedExecutionException) {
            LogUtils.e(TAG, "task $task execute failed$e")
            throw IOException()
        }
    }

    @SuppressLint("NewApi")
    @Throws(IOException::class)
    fun execute(
        task: AsyncTask<Any?, *, *>,
        vararg params: Any?
    ) {
        try {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
                task.execute(*params)
            } else {
                task.executeOnExecutor(sExecutor, *params)
            }
        } catch (e: RejectedExecutionException) {
            LogUtils.e(TAG, "task $task execute failed$e")
            throw IOException()
        }
    }
}