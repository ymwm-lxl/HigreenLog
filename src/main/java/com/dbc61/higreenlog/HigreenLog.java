package com.dbc61.higreenlog;

import android.app.Application;
import android.util.Log;

import com.dbc61.higreenlog.config.LogConfig;
import com.dbc61.higreenlog.config.LogLevelEnum;
import com.dbc61.higreenlog.utils.FileUtils;
import com.dbc61.higreenlog.utils.LogTimeUtils;

import org.w3c.dom.Text;

import java.io.File;

/**
 * Description：海吉星中台 - 日志模块
 * Author：xlLee
 * Date：2021-11-17 11:39
 */
public final class HigreenLog {
    private static final String LOG_TAG_HIGH = "HIGH";
    private static final String LOG_TAG_LOW = "LOW ";


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

    /**
     * 打印 HEIGHT 级别的日志记录
     * Height:积极的，打印所有日志
     */
    public static void high(String text){
        if (LogConfig.getInstance().getLoglevel() != LogLevelEnum.HIGH) return;

        String paragraph = makeChar(LOG_TAG_HIGH, text);

        if (LogConfig.getInstance().isLogIsPrint()) printLog(paragraph);
        FileUtils.writeLogFile(sApplication,paragraph);
    }

    /**
     * 打印 low 级别的日志记录
     * log：消极的，只打印问题日志
     */
    public static void low(String text){

        String paragraph = makeChar(LOG_TAG_LOW, text);

        if (LogConfig.getInstance().isLogIsPrint()) printLog(paragraph);
        FileUtils.writeLogFile(sApplication,paragraph);

    }

    /**
     * 获取所有日志文件
     */
    public static File[] getAllLogFile(){
        return FileUtils.getLogFile(sApplication);
    }


    /**
     * 生成存储的字符
     * | L O W | 1637137820368 | 2021-11-17 16:30:20 周三 | 测试打印 |
     */
    private static String makeChar(String levelTag,String text){
        long time = LogTimeUtils.getNowMills();
        StringBuffer logText = new StringBuffer();
        logText.append("| ");
        logText.append(levelTag);
        logText.append(" | ");
        logText.append(time);
        logText.append(" | ");
        logText.append(LogTimeUtils.millis2String(time));
        logText.append(" ");
        logText.append(LogTimeUtils.getChineseWeek(time));
        logText.append(" | ");
        logText.append(text);
        logText.append("");
        logText.append(" |");
        return logText.toString();
    }

    /**
     * 打印日志方法
     */
    private static void printLog(String text){
        Log.i("HIGREEN","------------------------------------------------------------------");
        Log.i("HIGREEN"," ");
        Log.i("HIGREEN",""+text);
        Log.i("HIGREEN"," ");
        Log.i("HIGREEN","------------------------------------------------------------------");
    }



}
