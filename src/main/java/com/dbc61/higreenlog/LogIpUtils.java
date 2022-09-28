package com.dbc61.higreenlog;

import android.content.Context;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * Description：
 * Author：xlLee
 * Date：2022-01-01 10:51
 */
public class LogIpUtils {

    public LogIpUtils() {}

    /**
     * 获取内网IP地址的方法
     */
    public static String getIpAddress(Context context) {
        try {
            for (Enumeration<NetworkInterface> enNetI = NetworkInterface.getNetworkInterfaces(); enNetI.hasMoreElements(); ) {
                NetworkInterface netI = enNetI.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = netI.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (inetAddress instanceof Inet4Address && !inetAddress.isLoopbackAddress()) {
                        String ip = inetAddress.getHostAddress();
//                        System.out.println("当前内网IP测试："+aa);
//                        return inetAddress.getHostAddress().replace(".","");
                        if (EmptyUtils.isNotEmpty(ip)){
                            //保存ip，方便断网时取用
                            OtherData.setDevieceIp(context, ip);
                            return ip;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //如果断网获取不到ip，则使用上次ip
        return OtherData.getDevieceIp(context);
    }
}
