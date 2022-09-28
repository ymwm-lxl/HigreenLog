package com.dbc61.higreenlog;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Description：文件操作工具类
 * Author：xlLee
 * Date：2021-11-17 14:42
 */
public class FileUtils {

    private static Object obj = new Object();

    /**
     * 制作文件名
     * @return
     */
    private static String makeFileName(Context context){
        StringBuffer logName = new StringBuffer();
        logName.append("android");
        logName.append("-");
        logName.append(LogTimeUtils.millis2String(LogTimeUtils.getWholeTime(),LogTimeUtils.getDateHourMinFormat()));
        logName.append("-");
        logName.append(LogIpUtils.getIpAddress(context));
        logName.append("-log");
        return logName.toString();
    }

    /**
     * 向文件写入日志
     * @param context
     * @param text
     */
    public static void writeLogFile(Context context,String text) {

        //线程池管理子线程
        LogThreadPoolManager.getInstance().execute(new Runnable() {
            @Override
            public void run() {

                synchronized (obj){
                    String path = context.getFilesDir().getPath() +"/log/";

                    //先判断是否有权限
                    if (isHaveStoragePermission(context)){
                        //再判断是否指定了文件目录
                        if (LogConfig.getInstance().getLogFilePath() != null
                                &&!LogConfig.getInstance().getLogFilePath().isEmpty()){
                            path = LogConfig.getInstance().getLogFilePath();
                        }
                    }

//                  Log.e("lxl", "存储path = "+path);
                    File tmepfile = new File(path);

                    if (!tmepfile.exists()) {
                        tmepfile.mkdir();
                    }
                    File file = new File(tmepfile,makeFileName(context));
                    FileOutputStream fileOutputStream = null;
                    try {
                        fileOutputStream = new FileOutputStream(file,true);
                        fileOutputStream.write(text.getBytes());
                        fileOutputStream.write("\r\n".getBytes());
                    }catch (Exception e){
                        e.printStackTrace();
                    } finally {
                        if (fileOutputStream != null) {
                            try {
                                fileOutputStream.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }


            }
        });
    }

    /**
     * 读取内部目录日志文件
     */
    public static File[] getFilesDirLogFile(Context context){
        String path = context.getFilesDir().getPath() +"/log/";
//        if (isHaveStoragePermission(context)){
//            if (LogConfig.getInstance().getLogFilePath() != null
//                    &&!LogConfig.getInstance().getLogFilePath().isEmpty()){
//                path = LogConfig.getInstance().getLogFilePath();
//            }
//        }
        return getLogFile(context, path);
    }

    /**
     * 读取日志文件
     */
    public static File[] getLogFile(Context context, String path){

        File file = new File(path);
        return file.listFiles();
    }


    /**
     * 判断是否有存储权限
     */
    public static boolean isHaveStoragePermission(Context context){
        return ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }



}

