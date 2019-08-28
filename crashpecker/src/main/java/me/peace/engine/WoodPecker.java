package me.peace.engine;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import me.peace.communication.ProcessDispatcher;
import me.peace.constant.Constant;
import me.peace.crashpecker.R;
import me.peace.ui.WoodPeckerActivity;
import me.peace.utils.CrashUtils;
import me.peace.utils.LogUtils;
import me.peace.utils.Utils;

public class WoodPecker implements Thread.UncaughtExceptionHandler {
    private static final String TAG = WoodPecker.class.getSimpleName();

    private Thread.UncaughtExceptionHandler defaultUncaughtExceptionHandler;
    private boolean crashing = false;
    private Context applicationContext;
    private ArrayList<String> keys;
    private boolean jump = true;
    private int crashRecordCount;
    private ProcessDispatcher dispatcher;

    private WoodPecker() {
        keys = new ArrayList<>();
        dispatcher = new ProcessDispatcher();
        crashRecordCount = Constant.DEFAULT_MAX_SAVE_FILE_COUNT;
    }

    private static class WoodPeckerHolder {
        private static WoodPecker instance = new WoodPecker();
    }

    public static WoodPecker instance() {
        return WoodPeckerHolder.instance;
    }

    public void fly(Context context){
        this.applicationContext = context.getApplicationContext();
        if (!Utils.isWoodpeckerRunning(context)){
            LogUtils.e(TAG,"WoodPecker start to fly");
            addDefaultKey();
            turnOnHandler();
        }
    }

    public WoodPecker with(String[] keys){
        if (keys != null) {
            this.keys.addAll(Arrays.asList(keys));
        }
        return this;
    }

    public WoodPecker jump(boolean jump){
        this.jump = jump;
        return this;
    }

    public WoodPecker count(int count){
        this.crashRecordCount = count > 0 ? count : Constant.DEFAULT_MAX_SAVE_FILE_COUNT;
        return this;
    }

    private void addDefaultKey(){
        keys.add(applicationContext.getPackageName());
    }

    private void turnOnHandler(){
        Thread.UncaughtExceptionHandler defaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        if (this != defaultUncaughtExceptionHandler){
            this.defaultUncaughtExceptionHandler = defaultUncaughtExceptionHandler;
            Thread.setDefaultUncaughtExceptionHandler(this);
        }
    }

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        if (crashing){
            return;
        }
        crashing = true;
        handleException(thread,throwable);
        if (this.defaultUncaughtExceptionHandler != null){
            this.defaultUncaughtExceptionHandler.uncaughtException(thread,throwable);
        }
    }

    private void handleException(Thread thread, Throwable throwable) {
        ArrayList<String> traces = trace(throwable);
        Date crashDate = new Date();
        String crashTime = crashTime(crashDate);
        if (jump) {
            startWoodPecker(traces,crashTime);
        }
        dispatcher.saveSpString(applicationContext,Constant.WOOD_PECKER_SP,
            Constant.KEY_HIGH_LIGHT,Utils.list2String(keys));
        dispatcher.saveSpString(applicationContext,Constant.WOOD_PECKER_SP,Constant.KEY_APP_NAME,
            Utils.getApplicationName(applicationContext));
        CrashUtils.save(applicationContext,throwable,crashDate,crashRecordCount);

    }

    private void startWoodPecker(ArrayList<String> traces,String crashTime){
        Intent intent = new Intent(applicationContext, WoodPeckerActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(Constant.KEY_TRACES, traces);
        intent.putExtra(Constant.KEY_CRASH_TIME, crashTime);
        intent.putExtra(Constant.KEY_HIGH_LIGHT, keys);
        intent.putExtra(Constant.KEY_WITH_TRACE, true);
        applicationContext.startActivity(intent);
    }

    private ArrayList<String> trace(Throwable throwable){
        String trace = Utils.getStackTrace(throwable);
        if (!TextUtils.isEmpty(trace)) {
            String[] traces = trace.split("\n");
            return new ArrayList<>(Arrays.asList(traces));
        }
        return new ArrayList<>();
    }

    private String crashTime(Date crashDate){
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        return format.format(crashDate);
    }
}
