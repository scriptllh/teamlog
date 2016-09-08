package com.wiseach.teamlog.utils;

import com.wiseach.teamlog.Constants;
import com.wiseach.teamlog.db.UserAuthDBHelper;
import net.coobird.thumbnailator.Thumbnails;
import net.sourceforge.stripes.action.FileBean;

import java.io.File;
import java.io.IOException;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * User: Arlen Tan
 * 12-8-17 上午10:30
 */
public class NetUtils {

    public static final String HTTP_DEFAULT_URL = "http://{0}/";
    public static final String HTTP_OTHER_PORT_URL = "http://{0}:{1}/";

    public static List<String> getAllIps() {
        Enumeration<NetworkInterface> networkInterfaces = null;
        List<String> ips = new ArrayList<String>();
        try {
            networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                Enumeration<InetAddress> inetAddresses = networkInterfaces.nextElement().getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    InetAddress inetAddress = inetAddresses.nextElement();
                    if (inetAddress.isLoopbackAddress()) continue;
                    ips.add(inetAddress.getHostAddress());
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return ips;
    }

    public static List<String> getAllHostUrl(int port) {
        List<String> ips = getAllIps(),urls = new ArrayList<String>();
        for (String ip : ips) {
            urls.add(port == 80 ? MessageFormat.format(HTTP_DEFAULT_URL, ip) : MessageFormat.format(HTTP_OTHER_PORT_URL, ip, String.valueOf(port)));
        }
        return urls;
    }
}
