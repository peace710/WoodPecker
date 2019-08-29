package me.peace.utils;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;

public class AsyncTaskDelegate {
    private static final String TAG = AsyncTaskDelegate.class.getSimpleName();
    private static ExecutorService sExecutor = Executors.newCachedThreadPool();

    @SuppressLint("NewApi")
    public static final void execute(AsyncTask<String, ?, ?> task,
                                     String... params) throws IOException {
        try {
            if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
                task.execute(params);
            } else {
                task.executeOnExecutor(sExecutor, params);
            }
        } catch (RejectedExecutionException e) {
            LogUtils.e(TAG,"task " + task + " execute failed" + e);
            throw new IOException();
        }
    }

    @SuppressLint("NewApi")
    public static final void execute(AsyncTask<Object, ?, ?> task,
                                     Object... params) throws IOException {
        try {
            if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
                task.execute(params);
            } else {
                task.executeOnExecutor(sExecutor, params);
            }
        } catch (RejectedExecutionException e) {
            LogUtils.e(TAG,"task " + task + " execute failed" + e);
            throw new IOException();
        }
    }
}
