package com.staple.probkaesp.utils.mdns;

import android.net.nsd.NsdManager;
import android.net.nsd.NsdServiceInfo;
import android.util.Log;

import java.net.InetAddress;

public class IpResolveListener implements NsdManager.ResolveListener{

    private NsdDiscovery nsdDiscovery;

    public IpResolveListener(NsdDiscovery nsdDiscovery) {
        this.nsdDiscovery = nsdDiscovery;
    }

    @Override
    public void onResolveFailed(NsdServiceInfo serviceInfo, int errorCode) {
        // Обработка ошибки при разрешении сервиса
        Log.d("IPERR", "RESOLVE FAILED " + serviceInfo.getServiceName() + " " + errorCode);
    }

    @Override
    public void onServiceResolved(NsdServiceInfo serviceInfo) {
        // Получение IP-адреса сервиса
        Log.d("IPSUCC", serviceInfo.getServiceName());
        InetAddress address = serviceInfo.getHost();
        String ipAddress = address.getHostAddress();
        String hostName = serviceInfo.getServiceName();
        nsdDiscovery.handleIpAddress(hostName, ipAddress);
        // Используйте полученный IP-адрес для связи с ESP8266
    }

}
