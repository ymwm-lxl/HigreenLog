package com.dbc61.higreenlog;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Description：存储一些杂项数据
 * Author：xlLee
 * Date：2022-08-10 16:59
 */
public class OtherData {

    /* sp 表名 */
    private static final String SP_NAME = "HIGREENLOG_SP";
    /* 记录清理日志的时间 */
    private static final String KEY_CLEAR_LOG_TIME = "keyClearLogTime";
    /* 记录清理日志的时间 */
    private static final String KEY_DEVICE_IP = "keyDeviceIp";

    private static SharedPreferences getSp(Context context){
        return context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
    }

    /**
     * 获取上次清理日志的时间
     */
    public static long getClearLogTime(Context context){
        return getSp(context).getLong(KEY_CLEAR_LOG_TIME,0);
    }

    /**
     * 设置上次清理日志的时间
     */
    public static void setClearLogTime(Context context, long clearTime){
        SharedPreferences.Editor editor = getSp(context).edit();
        editor.putLong(KEY_CLEAR_LOG_TIME, clearTime);
        editor.apply();
    }

    /**
     * 获取设备保存的ip
     * 用于设备断网时临时使用
     */
    public static String getDevieceIp(Context context){
        return getSp(context).getString(KEY_DEVICE_IP,"");
    }

    /**
     * 获取设备保存的ip
     * 用于设备断网时临时使用
     */
    public static void setDevieceIp(Context context, String ip){
        SharedPreferences.Editor editor = getSp(context).edit();
        editor.putString(KEY_DEVICE_IP, ip);
        editor.apply();
    }



}
