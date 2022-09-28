package com.dbc61.higreenlog;

import android.app.Application;
import android.util.Log;
import android.util.TimeUtils;

import java.io.File;
import java.util.List;

/**
 * Description：海吉星中台 - 日志模块
 * Author：xlLee
 * Date：2021-11-17 11:39
 */
public final class HigreenLog {
    private static final String LOG_TAG_DEBUG = "DEBUG";
    private static final String LOG_TAG_INFO = "INFO";
    private static final String LOG_TAG_WARN = "WARN";
    private static final String LOG_TAG_ERROR = "ERROR";

    /* application */
    private static Application sApplication;

    /**
     * 不允许外部实例化
     */
    private HigreenLog() {

    }

    /**
     * 初始化
     */
    public static void init(Application application){
        sApplication = application;
    }

    public static LogConfig getConfig(){
        return LogConfig.getInstance();
    }

    /**
     * 打印 HEIGHT 级别的日志记录
     * Height:积极的，打印所有日志
     */
//    @Deprecated
//    public static void high(String text){
//        if (getConfig().getLoglevel() != LogLevelEnum.HIGH) return;
//
//        String paragraph = makeChar(LOG_TAG_INFO, text);
//
//        if (getConfig().isLogIsPrint()) printLog(paragraph);
//        FileUtils.writeLogFile(sApplication,paragraph);
//    }
//
//    /**
//     * 打印 low 级别的日志记录
//     * log：消极的，只打印问题日志
//     */
//    @Deprecated
//    public static void low(String text){
//        String paragraph = makeChar(LOG_TAG_DEBUG, text);
//
//        if (getConfig().isLogIsPrint()) printLog(paragraph);
//
//        FileUtils.writeLogFile(sApplication,paragraph);
//    }

    /**
     * 记录 debug 级别的日志
     */
    public static void debug(String text){
        String paragraph = makeChar(LOG_TAG_DEBUG, text);

        if (getConfig().isLogIsPrint()) printLog(paragraph);

        FileUtils.writeLogFile(sApplication,paragraph);
    }

    /**
     * 记录 info 级别的日志
     */
    public static void info(String text){
        String paragraph = makeChar(LOG_TAG_INFO, text);

        if (getConfig().isLogIsPrint()) printLog(paragraph);

        FileUtils.writeLogFile(sApplication,paragraph);
    }

    /**
     * 记录 warn 级别的日志
     */
    public static void warn(String text){
        String paragraph = makeChar(LOG_TAG_WARN, text);

        if (getConfig().isLogIsPrint()) printLog(paragraph);

        FileUtils.writeLogFile(sApplication,paragraph);
    }

    /**
     * 记录 warn 级别的日志
     */
    public static void error(String text){
        String paragraph = makeChar(LOG_TAG_ERROR, text);

        if (getConfig().isLogIsPrint()) printLog(paragraph);

        FileUtils.writeLogFile(sApplication,paragraph);
    }

    /**
     * 获取所有日志文件
     */
    public static File[] getAllLogFile(String path){
        return FileUtils.getLogFile(sApplication, path);
    }

    /**
     * 获取用户配置目录所有日志文件
     */
    public static File[] getConfigFileAllLogFile(){

        if (EmptyUtils.isNotEmpty(LogConfig.getInstance().getLogFilePath())){
            if (FileUtils.isHaveStoragePermission(sApplication)){
                return FileUtils.getLogFile(sApplication, getConfig().getLogFilePath());
            }
        }
        return new File[0];
    }

    /**
     * 获取内部存储所有日志文件
     */
    public static File[] getFilesDirAllLogFile(){
        return FileUtils.getFilesDirLogFile(sApplication);
    }

    /**
     * 生成存储的字符
     * | VERBOSE | 1.0 | 2022-08-09 13:35:34:467 周二 | LogTestActivity.java:36.onClick | 测试打印
     */
    private static String makeChar(String levelTag,String text){

        long time = LogTimeUtils.getNowMills();
        StringBuffer logText = new StringBuffer();
        logText.append("|");
        logText.append(levelTag);
        logText.append("| ");
        logText.append(LogOtherUtils.getVersion(sApplication));
        logText.append(" | ");
        logText.append(LogTimeUtils.millis2String(time, LogTimeUtils.getMillisecondFormat()));
        logText.append(" ");
        logText.append(LogTimeUtils.getChineseWeek(time));
        logText.append(" | ");
        logText.append(Thread.currentThread().getStackTrace()[5].getFileName());
        logText.append(":");
        logText.append(Thread.currentThread().getStackTrace()[5].getLineNumber());
        logText.append(".");
        logText.append(Thread.currentThread().getStackTrace()[5].getMethodName());
        logText.append(" | ");
        logText.append(" 【");
        logText.append(LogConfig.getInstance().getLogTag());
        logText.append("】 ");

        logText.append(text);
        return logText.toString();

    }

    /**
     * 打印日志方法
     */
    private static void printLog(String text){
        Log.i("HIGREEN","——————————————————————————————————————————————————————————————————");
        Log.i("HIGREEN","| ");
        Log.i("HIGREEN",""+text);
        Log.i("HIGREEN","| ");
        Log.i("HIGREEN","——————————————————————————————————————————————————————————————————");
    }

    /**
     * 定期清理
     */
    public static void autoClearLogFile(){
        //一天清理一次
        long timeDifference = LogTimeUtils.getNowMills() - OtherData.getClearLogTime(sApplication);
        if (timeDifference >= LogTimeUtils.TimeConstants.DAY){
            //超过一天，清理一下
            clearLog();
        }
        //
    }

    /**
     * 清理日志
     * （清理五天之前的）
     */
    public static void clearLog(){
        info("【log】【delete】开始清理日志文件");
        //获取所有文件
        File[] allFiles;
        if (EmptyUtils.isNotEmpty(getConfig().getLogFilePath())){
            allFiles = FileUtils.getLogFile(sApplication, getConfig().getLogFilePath());
        }else {
            allFiles = FileUtils.getLogFile(sApplication, sApplication.getFilesDir().getPath() +"/log/");
        }


        //遍历删除文件
        for (File file: allFiles) {
            String logName = file.getName();
            if (logName.startsWith("android")
                    && logName.contains("log")){
                long fileDateMillis = LogTimeUtils.string2Millis(logName.subSequence(8,20).toString(),LogTimeUtils.getDateHourMinFormat());
                if (LogTimeUtils.getTimeSpanByToDay(fileDateMillis) > 5){
                    boolean isdelete = file.delete();
                    info("【log】【delete】删除旧日志文件 " + logName);
                }
            }
        }

        info("【log】【delete】清理结束");

    }


}
