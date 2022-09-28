package com.dbc61.higreenlog;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Description：
 * Author：xlLee
 * Date：2022-09-15 19:43
 */
public final class LogThreadPoolManager extends ThreadPoolExecutor {

    private static volatile LogThreadPoolManager sInstance;

    public LogThreadPoolManager() {
        // 这里最大线程数为什么不是 Int 最大值？因为在华为荣耀机子上面有最大线程数限制
        // 经过测试华为荣耀手机不能超过 300 个线程，否则会出现内存溢出
        // java.lang.OutOfMemoryError：pthread_create (1040KB stack) failed: Out of memory
        // 由于应用自身占用了一些线程数，故减去 300 - 100 = 200 个
        super(0, 200,
                10 * 1000L, TimeUnit.MILLISECONDS,
                new SynchronousQueue<>());
    }

    public static LogThreadPoolManager getInstance() {
        if(sInstance == null) {
            synchronized (LogThreadPoolManager.class) {
                if(sInstance == null) {
                    sInstance = new LogThreadPoolManager();
                }
            }
        }
        return sInstance;
    }
}
