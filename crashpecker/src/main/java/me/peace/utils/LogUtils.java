package me.peace.utils;

import android.util.Log;

public class LogUtils {

    private static final String CRASH_WOOD_PECKER = "WoodPecker";

    private static boolean isDebugMode(){
        return true;
    }
    
    public static void v(String tag,String msg){
        if (isDebugMode()) {
            Log.v(wrapTag(tag), wrapMsg(msg));
        }
    }


    public static void w(String tag,String msg){
        if (isDebugMode()){
            Log.w(wrapTag(tag),wrapMsg(msg));
        }
    }

    public static void i(String tag,String msg){
        if (isDebugMode()){
            Log.i(wrapTag(tag),wrapMsg(msg));
        }
    }


    public static void d(String tag,String msg){
        if (isDebugMode()){
            Log.d(wrapTag(tag),wrapMsg(msg));
        }
    }

    public static void e(String tag,String msg){
        if (isDebugMode()){
            Log.e(wrapTag(tag),wrapMsg(msg));
        }
    }

    private static String wrapMsg(String msg) {
        return msg + " [ThreadName --> " + Thread.currentThread().getName() + "] ";
    }

    private static String wrapTag(String tag) {
        return CRASH_WOOD_PECKER + " [" + tag + "] ";
    }
}
