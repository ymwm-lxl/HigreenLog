package com.dbc61.higreenlog.utils;

import android.content.Context;

import com.dbc61.higreenlog.config.LogConfig;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

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
    private static String makeFileName(){
        return "higreenlog_" + LogTimeUtils.getWeeOfToday()+".txt";
    }

    /**
     * 向文件写入日志
     * @param context
     * @param text
     */
    public static void writeLogFile(Context context,String text) {
        synchronized (obj){

            String path = context.getFilesDir().getPath() +"/log/";
            if (LogConfig.getInstance().getLogFilePath() != null
                    &&!LogConfig.getInstance().getLogFilePath().isEmpty()){
                path = LogConfig.getInstance().getLogFilePath();
            }

            File tmepfile = new File(path);

            if (!tmepfile.exists()) {
                tmepfile.mkdir();
            }
            File file = new File(tmepfile,makeFileName());
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

    /**
     * 读取日志文件
     */
    public static File[] getLogFile(Context context){
        String path = context.getFilesDir().getPath() +"/log/";
        if (LogConfig.getInstance().getLogFilePath() != null
                &&!LogConfig.getInstance().getLogFilePath().isEmpty()){
            path = LogConfig.getInstance().getLogFilePath();
        }
        File file = new File(path);
        return file.listFiles();
    }

}
