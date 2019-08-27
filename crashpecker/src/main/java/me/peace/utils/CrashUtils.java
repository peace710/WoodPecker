package me.peace.utils;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import me.peace.constant.Constant;

public class CrashUtils {
    public static boolean save(Context context, Throwable throwable,int crashRecordCount){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        String fileName = "Crash-" + format.format(new Date()) + ".log";
        String crashDir = context.getCacheDir().getPath() + Constant.CRASH_DIR;
        String crashPath = crashDir + fileName;

        File file = new File(crashPath);
        if (file.exists()){
            file.delete();
        }else{
            if (!createFile(file,crashRecordCount)){
                return false;
            }
        }
        return write(file,throwable);
    }

    private static boolean createFile(File file,int crashRecordCount){
        if (file != null){
            try {
                File dir = new File(file.getParent());
                dir.mkdirs();
                File[] childFiles = dir.listFiles();
                if (childFiles != null && childFiles.length == crashRecordCount){
                    File firstModifiedFile = findFirstModifiedFile(dir);
                    if (firstModifiedFile != null){
                        firstModifiedFile.delete();
                    }
                }
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    private static boolean write(File file,Throwable throwable){
        PrintWriter writer;
        try {
            writer = new PrintWriter(file);
            Throwable cause = throwable.getCause();
            throwable = cause == null ? throwable : cause;
            throwable.printStackTrace(writer);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private static File findLastModifiedFile(File file){
        File lastModifiedFlie = null;
        long timestamp = -1;
        for (File f : file.listFiles()){
            long lastModified = f.lastModified();
            if (lastModified > timestamp){
                timestamp = lastModified;
                lastModifiedFlie = f;
            }
        }
        return lastModifiedFlie;
    }

    private static File findFirstModifiedFile(File file){
        File firstModifiedFlie = null;
        long timestamp = Long.MAX_VALUE;
        for (File f : file.listFiles()){
            long lastModified = f.lastModified();
            if (lastModified < timestamp){
                timestamp = lastModified;
                firstModifiedFlie = f;
            }
        }
        return firstModifiedFlie;
    }

    public static String read(Context context){
        String crashDir = context.getCacheDir().getPath() + Constant.CRASH_DIR;
        File file = new File(crashDir);
        if (file.exists()){
            File lastModifiedFile = findLastModifiedFile(file);
            if (lastModifiedFile != null) {
                StringBuffer sb = new StringBuffer();
                try {
                    FileReader reader = new FileReader(lastModifiedFile);
                    BufferedReader bufferedReader = new BufferedReader(reader);
                    String str;
                    while ((str = bufferedReader.readLine()) != null) {
                        sb.append(str.trim()).append("\n");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return "";
                }
                return sb.toString();
            }
        }
        return "";
    }

}
