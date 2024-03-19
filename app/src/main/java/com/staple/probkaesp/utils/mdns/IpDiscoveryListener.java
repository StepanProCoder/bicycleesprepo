package com.staple.probkaesp.utils.mdns;

import android.net.nsd.NsdManager;
import android.net.nsd.NsdServiceInfo;
import android.util.Log;

public class IpDiscoveryListener implements NsdManager.DiscoveryListener {
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

//        if(!serviceInfo.getServiceName().contains("SpeedESP"))
//            return;

        Log.d("NsdDiscovery", "Service found: " + serviceInfo.getServiceName());

        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        NsdManager.ResolveListener resolveListener = new IpResolveListener(nsdDiscovery);
        // Обработка найденного сервиса
        if (serviceInfo.getServiceType().equals(NsdDiscovery.SERVICE_TYPE)) {
            Log.d("RESOLVING", "IP RES");
            nsdDiscovery.getNsdManager().resolveService(serviceInfo, resolveListener);
        }
    }

    @Override
    public void onServiceLost(NsdServiceInfo serviceInfo) {
        Log.d("NsdDiscovery", "Service lost: " + serviceInfo.getServiceName());
    }
}
