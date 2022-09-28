package com.dbc61.higreenlog;

/**
 * Description：日志配置文件
 * Author：xlLee
 * Date：2021-11-17 11:54
 */
public class LogConfig {

    /** 日志等级 */
    private LogLevelEnum Loglevel = LogLevelEnum.HIGH;

    /** 是否打印 */
    private boolean LogIsPrint = true;

    /** 是否 debug？ debug模式不存储 */
    private boolean isDebug = false;

    /** 最大内存 默认 10 M*/
    private long LogFileMaxSize = 10 * 1000 * 1000;

    /** 存储地址 (仅允许设置一次)*/
    private String mLogFilePath;

    /** app标识，用于日志文件名 */
    private String mAppNameTag;

    /** 日志tag (可能为空) */
    private String mLogTag;

    /** 单例 */
    private static LogConfig mInstance;

    private LogConfig() {}

    public static LogConfig getInstance(){
        if (mInstance == null) {
            synchronized (LogConfig.class) {
                if (mInstance == null) {
                    mInstance = new LogConfig();
                }
            }
        }
        return mInstance;
    }

    public LogLevelEnum getLoglevel() {
        return Loglevel;
    }

    public LogConfig setLoglevel(LogLevelEnum loglevel) {
        Loglevel = loglevel;
        return this;
    }

    public boolean isLogIsPrint() {
        return LogIsPrint;
    }

    public LogConfig setLogIsPrint(boolean logIsPrint) {
        LogIsPrint = logIsPrint;
        return this;
    }

    public boolean isDebug() {
        return isDebug;
    }

    public LogConfig setDebug(boolean debug) {
        isDebug = debug;
        return this;
    }

    public long getLogFileMaxSize() {
        return LogFileMaxSize;
    }

    public LogConfig setLogFileMaxSize(long logFileMaxSize) {
        LogFileMaxSize = logFileMaxSize;
        return this;
    }

    public String getLogFilePath() {
        return mLogFilePath;
    }


    public LogConfig setLogFilePath(String logFilePath) {
        //文件目录，仅允许设置一次
        if (mLogFilePath == null){
            //防止传空值进来，如果空值，强行赋值空
            if (logFilePath == null){
                mLogFilePath = "";
                return this;
            }
            mLogFilePath = logFilePath;
            return this;
        }else {
            throw new RuntimeException("禁止多次设置日志path");
        }
    }

    public String getAppNameTag() {
        return mAppNameTag;
    }

    public LogConfig setAppNameTag(String appNameTag) {
        mAppNameTag = appNameTag;
        return this;
    }

    /**
     * 获取日志tag
     * @return tag字符
     */
    public String getLogTag() {
        //如果没有设置，则使用 * 占位
        if (EmptyUtils.isEmpty(mLogTag)) return "***";
        return mLogTag;
    }

    public void setLogTag(String userName) {
        synchronized (LogConfig.class){
            mLogTag = userName;
        }

    }

}
