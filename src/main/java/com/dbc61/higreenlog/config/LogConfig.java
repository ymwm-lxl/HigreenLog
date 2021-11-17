package com.dbc61.higreenlog.config;

/**
 * Description：日志配置文件
 * Author：xlLee
 * Date：2021-11-17 11:54
 */
public class LogConfig {

    /* 日志等级 */
    private LogLevelEnum Loglevel = LogLevelEnum.HIGHT;

    /* 是否打印 */
    private boolean LogIsPrint = true;

    /* 是否 debug？ debug模式不存储 */
    private boolean isDebug = false;

    /* 最大内存 默认 10 M*/
    private long LogFileMaxSize = 10 * 1000 * 1000;

    /* 存储地址 (仅允许设置一次)*/
    private String LogFilePath;

    /* 单例 */
    private static LogConfig mInstance;

    private LogConfig() {
    }

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
        return LogFilePath;
    }


    public void setLogFilePath(String logFilePath) {
        //文件目录，仅允许设置一次
        if (LogFilePath == null){
            //防止传空值进来，如果空值，强行赋值空
            if (logFilePath == null){
                LogFilePath = "";
                return;
            }
            LogFilePath = logFilePath;
        }

    }
}
