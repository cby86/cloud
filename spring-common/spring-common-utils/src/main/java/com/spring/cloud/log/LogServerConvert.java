package com.spring.cloud.log;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.Query;
import java.lang.management.ManagementFactory;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Set;

public class LogServerConvert extends ClassicConverter {
    @Override
    public String convert(ILoggingEvent event) {
        try {
            MBeanServer beanServer = ManagementFactory.getPlatformMBeanServer();
            Set<ObjectName> objectNames = beanServer.queryNames(new ObjectName("*:type=Connector,*"), Query.match(Query.attr("protocol"), Query.value("HTTP/1.1")));
            if (objectNames.isEmpty()) {
                return "default";
            }
            String port = objectNames.iterator().next().getKeyProperty("port");
            List<String> ipList = new ArrayList<>();
            Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
            while (netInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = netInterfaces.nextElement();
                Enumeration<InetAddress> enumeration = networkInterface.getInetAddresses();
                while (enumeration.hasMoreElements()) {
                    InetAddress inetAddress = enumeration.nextElement();
                    if (inetAddress != null && !inetAddress.isLoopbackAddress() && inetAddress.isSiteLocalAddress() && inetAddress instanceof Inet4Address && inetAddress.getHostAddress().indexOf(".") != -1) {
                        ipList.add(inetAddress.getHostAddress());
                    }
                }
            }
            return ipList.toString() + "[" + port + "]";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "default";
    }
}
