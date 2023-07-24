package com.staple.probkaesp;

import android.net.nsd.NsdManager;
import android.net.nsd.NsdServiceInfo;

import java.net.InetAddress;

public class IpResolveListener implements NsdManager.ResolveListener{

    private NsdDiscovery nsdDiscovery;

    public IpResolveListener(NsdDiscovery nsdDiscovery) {
        this.nsdDiscovery = nsdDiscovery;
    }

    @Override
    public void onResolveFailed(NsdServiceInfo serviceInfo, int errorCode) {
        // Обработка ошибки при разрешении сервиса
    }

    @Override
    public void onServiceResolved(NsdServiceInfo serviceInfo) {
        // Получение IP-адреса сервиса
        InetAddress address = serviceInfo.getHost();
        String ipAddress = address.getHostAddress();
        nsdDiscovery.handleIpAddress(ipAddress);
        // Используйте полученный IP-адрес для связи с ESP8266
    }

}
