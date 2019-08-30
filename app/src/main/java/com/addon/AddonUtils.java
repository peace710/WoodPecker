package com.addon;

import me.peace.utils.LogUtils;

public class AddonUtils {
    public static double div(double a,double b){
        return a / b;
    }

    public static double div(int a,int b){
        return a / b;
    }

    public static void stack(){
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        for (StackTraceElement element : stackTraceElements){
            LogUtils.e("stack",
                element.getClassName() + "," + element.getMethodName() + "," + element.getLineNumber() + "," + element.getFileName());
        }
    }
}
