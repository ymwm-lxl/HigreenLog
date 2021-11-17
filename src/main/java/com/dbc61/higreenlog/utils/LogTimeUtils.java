package com.dbc61.higreenlog.utils;

import android.annotation.SuppressLint;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Description：时间工具类
 * Author：xlLee
 * Date：2021-11-17 14:16
 */
public final class LogTimeUtils {


    /**
     * 防止初始化
     */
    private LogTimeUtils() {}

    /**
     * 默认时间格式
     */
    private static SimpleDateFormat getDefaultFormat() {
        return getSafeDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    private static final ThreadLocal<Map<String, SimpleDateFormat>> SDF_THREAD_LOCAL
            = new ThreadLocal<Map<String, SimpleDateFormat>>() {
        @Override
        protected Map<String, SimpleDateFormat> initialValue() {
            return new HashMap<>();
        }
    };

    @SuppressLint("SimpleDateFormat")
    public static SimpleDateFormat getSafeDateFormat(String pattern) {
        Map<String, SimpleDateFormat> sdfMap = SDF_THREAD_LOCAL.get();
        //noinspection ConstantConditions
        SimpleDateFormat simpleDateFormat = sdfMap.get(pattern);
        if (simpleDateFormat == null) {
            simpleDateFormat = new SimpleDateFormat(pattern);
            sdfMap.put(pattern, simpleDateFormat);
        }
        return simpleDateFormat;
    }

    /**
     * 获取当前时间戳
     */
    public static long getNowMills() {
        return System.currentTimeMillis();
    }

    /**
     * 获取当前时间字符串
     */
    public static String getNowString() {
        return millis2String(System.currentTimeMillis(), getDefaultFormat());
    }

    /**
     * 将时间戳转为时间字符串
     */
    public static String millis2String(final long millis) {
        return millis2String(millis, getDefaultFormat());
    }

    /**
     * 将时间戳转为时间字符串
     */
    public static String millis2String(long millis, @NonNull final String pattern) {
        return millis2String(millis, getSafeDateFormat(pattern));
    }

    /**
     * 将时间戳转为时间字符串
     */
    public static String millis2String(final long millis, @NonNull final DateFormat format) {
        return format.format(new Date(millis));
    }

    /**
     * 将时间戳转为 Date 类型
     */
    public static Date millis2Date(final long millis) {
        return new Date(millis);
    }

    /**
     * 获取中式星期
     */
    public static String getChineseWeek(final long millis) {
        return getChineseWeek(new Date(millis));
    }

    /**
     * 获取中式星期
     */
    public static String getChineseWeek(final Date date) {
        return new SimpleDateFormat("E", Locale.CHINA).format(date);
    }

    /**
     * 获取当天零点时间戳
     */
    public static long getWeeOfToday() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }

    /**
     * 判断是否是今天
     */
    public static boolean isToday(final long millis) {
        long wee = getWeeOfToday();
        return millis >= wee && millis < wee + TimeConstants.DAY;
    }

    /**
     * 判断与今天的时间差
     */
    public static int getTimeSpanByToDay(final long millis){
        return getTimeSpanByDay(getNowMills(),millis);
    }

    /**
     * 判断两个时间的差 (单位天)
     */
    public static int getTimeSpanByDay(final long millis1,
                                   final long millis2) {
        Calendar cal1 =  Calendar.getInstance();
        cal1.setTimeInMillis(millis1);
        cal1.set(Calendar.HOUR_OF_DAY, 0);
        cal1.set(Calendar.SECOND, 0);
        cal1.set(Calendar.MINUTE, 0);
        cal1.set(Calendar.MILLISECOND, 0);

        Calendar cal2 =  Calendar.getInstance();
        cal2.setTimeInMillis(millis2);
        cal2.set(Calendar.HOUR_OF_DAY, 0);
        cal2.set(Calendar.SECOND, 0);
        cal2.set(Calendar.MINUTE, 0);
        cal2.set(Calendar.MILLISECOND, 0);

        return (int) ((cal1.getTimeInMillis() - cal2.getTimeInMillis()) / TimeConstants.DAY);
    }


    public static class TimeConstants{
        public static final int MSEC = 1;
        public static final int SEC  = 1000;
        public static final int MIN  = 60000;
        public static final int HOUR = 3600000;
        public static final int DAY  = 86400000;

        @IntDef({MSEC, SEC, MIN, HOUR, DAY})
        @Retention(RetentionPolicy.SOURCE)
        public @interface Unit {

        }
    }

}
