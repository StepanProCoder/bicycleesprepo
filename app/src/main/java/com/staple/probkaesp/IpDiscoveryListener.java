package com.staple.probkaesp;

import android.net.nsd.NsdManager;
import android.net.nsd.NsdServiceInfo;
import android.util.Log;

import java.net.InetAddress;

public class IpDiscoveryListener implements NsdManager.DiscoveryListener {

    private static final String SERVICE_TYPE = "_http._tcp.";
    private NsdDiscovery nsdDiscovery;

    public IpDiscoveryListener(NsdDiscovery nsdDiscovery) {
        this.nsdDiscovery = nsdDiscovery;
    }

    @Override
    public void onStartDiscoveryFailed(String serviceType, int errorCode) {
        Log.e("NsdDiscovery", "Discovery failed: " + errorCode);
    }

    @Override
    public void onStopDiscoveryFailed(String serviceType, int errorCode) {
        Log.e("NsdDiscovery", "Stop discovery failed: " + errorCode);
    }

    @Override
    public void onDiscoveryStarted(String serviceType) {
        Log.d("NsdDiscovery", "Discovery started");
    }

    @Override
    public void onDiscoveryStopped(String serviceType) {
        Log.d("NsdDiscovery", "Discovery stopped");
    }

    @Override
    public void onServiceFound(NsdServiceInfo serviceInfo) {
        Log.d("NsdDiscovery", "Service found: " + serviceInfo.getServiceName());
        if(!serviceInfo.getServiceName().equals("SpeedESP"))
            return;

        NsdManager.ResolveListener resolveListener = new IpResolveListener(nsdDiscovery);
        // Обработка найденного сервиса
        if (serviceInfo.getServiceType().equals(SERVICE_TYPE)) {
            nsdDiscovery.getNsdManager().resolveService(serviceInfo, resolveListener);
        }
    }

    @Override
    public void onServiceLost(NsdServiceInfo serviceInfo) {
        Log.d("NsdDiscovery", "Service lost: " + serviceInfo.getServiceName());
    }
}
