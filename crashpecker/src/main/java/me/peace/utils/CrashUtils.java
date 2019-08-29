package me.peace.utils;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import me.peace.constant.Constant;

public class CrashUtils {
    public static boolean save(Context context, Throwable throwable,
                              String date,int crashRecordCount){
        String fileName = "Crash-" + date + ".log";
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

    private static boolean createFile(final File file, int crashRecordCount){
        if (file != null){
            try {
                File dir = new File(file.getParent());
                dir.mkdirs();
                File[] childFiles = listFiles(dir);
                if (childFiles != null){
                    int count = childFiles.length - crashRecordCount;
                    for (int i = 0 ; i <= count ; i++) {
                        File firstModifiedFile = findFirstModifiedFile(dir);
                        if (firstModifiedFile != null) {
                            firstModifiedFile.delete();
                        }
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
        File lastModifiedFile = null;
        long timestamp = -1;
        for (File f : listFiles(file)){
            long lastModified = f.lastModified();
            if (lastModified > timestamp){
                timestamp = lastModified;
                lastModifiedFile = f;
            }
        }
        return lastModifiedFile;
    }

    private static File findFirstModifiedFile(File file){
        File firstModifiedFile = null;
        long timestamp = Long.MAX_VALUE;
        for (File f : listFiles(file)){
            long lastModified = f.lastModified();
            if (lastModified < timestamp){
                timestamp = lastModified;
                firstModifiedFile = f;
            }
        }
        return firstModifiedFile;
    }

    public static File getCrashFile(Context context){
        String crashDir = context.getCacheDir().getPath() + Constant.CRASH_DIR;
        File file = new File(crashDir);
        if (file.exists()){
            File lastModifiedFile = findLastModifiedFile(file);
            if (lastModifiedFile != null) {
               return lastModifiedFile;
            }
        }
        return null;
    }

    public static String read(File file){
        if (file == null){
            return "";
        }
        StringBuffer sb = new StringBuffer();
        try {
            FileReader reader = new FileReader(file);
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

    private static File[] listFiles(File file){
        if (file == null){
            return new File[0];
        }
        if (!file.isDirectory()){
            return new File[0];
        }
        return file.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return Utils.contains(name);
            }
        });
    }

}
