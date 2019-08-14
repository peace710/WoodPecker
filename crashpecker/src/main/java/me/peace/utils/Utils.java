package me.peace.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.Log;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
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

    public static String list2String(ArrayList<String> list){
        if (list != null && list.size() > 0){
            String str = list.remove(0);
            for (int i = 0 ; i < list.size() ;i++){
                str = str + "|" + list.get(i);
            }
            return str;
        }
        return "";
    }

    public static ArrayList<String> string2List(String str){
        LogUtils.e(TAG,"string2List str ==> " + str);
        if (!TextUtils.isEmpty(str)){
            String[] traces = str.split("\\|");
            return new ArrayList<>(Arrays.asList(traces));
        }
        return new ArrayList<>();
    }
}
