package com.staple.probkaesp;
import android.content.Context;
import android.net.nsd.NsdManager;
import android.net.nsd.NsdServiceInfo;
import android.util.Log;

import java.net.InetAddress;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NsdDiscovery {

    private static final String SERVICE_TYPE = "_http._tcp.";

    private Context mContext;
    private NsdManager mNsdManager;
    private NsdManager.DiscoveryListener mDiscoveryListener;

    public Esp8266Api esp8266Api;

    public NsdDiscovery(Context context) {
        mContext = context;
        mNsdManager = (NsdManager) mContext.getSystemService(Context.NSD_SERVICE);
    }

    public void startDiscovery() {
        mDiscoveryListener = new NsdManager.DiscoveryListener() {
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
                NsdManager.ResolveListener resolveListener = new NsdManager.ResolveListener() {
                    @Override
                    public void onResolveFailed(NsdServiceInfo serviceInfo, int errorCode) {
                        // Обработка ошибки при разрешении сервиса
                    }

                    @Override
                    public void onServiceResolved(NsdServiceInfo serviceInfo) {
                        // Получение IP-адреса сервиса
                        InetAddress address = serviceInfo.getHost();
                        String ipAddress = address.getHostAddress();
                        handleIpAddress(ipAddress);
                        // Используйте полученный IP-адрес для связи с ESP8266
                    }
                };
                // Обработка найденного сервиса
                if (serviceInfo.getServiceType().equals(SERVICE_TYPE)) {
                    mNsdManager.resolveService(serviceInfo, resolveListener);
                }
            }

            @Override
            public void onServiceLost(NsdServiceInfo serviceInfo) {
                Log.d("NsdDiscovery", "Service lost: " + serviceInfo.getServiceName());
            }
        };

        mNsdManager.discoverServices(SERVICE_TYPE, NsdManager.PROTOCOL_DNS_SD, mDiscoveryListener);
    }

    public void stopDiscovery() {
        mNsdManager.stopServiceDiscovery(mDiscoveryListener);
    }

    private void handleIpAddress(String ipAddress) {
        // Обработка полученного IP-адреса ESP8266
        Log.d("IP", ipAddress);
        // Создание экземпляра Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://" + ipAddress) // Замените "esp8266-ip-address" на IP-адрес ESP8266
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Создание экземпляра интерфейса для взаимодействия с API
        esp8266Api = retrofit.create(Esp8266Api.class);
    }

}
