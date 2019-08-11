package me.peace.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;

public class Utils {
    private static final String TAG = Utils.class.getSimpleName();
    private static final String PROCESS = ":woodpecker";

    public static boolean isWoodpeckerRunning(Context context){
        ActivityManager manager = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> infoList = manager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo info : infoList){
            LogUtils.e(TAG,"processName = " + info.processName);
            if (info.processName.endsWith(PROCESS)){
                LogUtils.e(TAG,"isWoodpeckerRunning true");
                return true;
            }
        }
        LogUtils.e(TAG,"isWoodpeckerRunning false");
        return false;
    }

    public static String getStackTrace(Throwable throwable){
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        throwable.printStackTrace(printWriter);
        printWriter.close();
        return writer.toString();
    }

    public static String getApplicationName(Context context) {
        PackageManager packageManager = context.getPackageManager();
        ApplicationInfo applicationInfo = null;
        String name = null;
        try {
            applicationInfo = packageManager.getApplicationInfo(context
                .getApplicationInfo().packageName, 0);
            name = (String)packageManager.getApplicationLabel(applicationInfo);
        } catch (PackageManager.NameNotFoundException e) {
            String[] packages = context.getPackageName().split(".");
            name = packages[(packages.length - 1)];
        }
        return name;
    }
}
