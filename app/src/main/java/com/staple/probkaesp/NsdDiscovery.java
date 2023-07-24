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
    private MainViewModel mainViewModel;
    private NsdManager.DiscoveryListener mDiscoveryListener;

    public NsdDiscovery(Context context, MainViewModel mainViewModel) {
        mContext = context;
        this.mainViewModel = mainViewModel;
        mNsdManager = (NsdManager) mContext.getSystemService(Context.NSD_SERVICE);
    }

    public NsdManager getNsdManager() {
        return mNsdManager;
    }

    public void startDiscovery() {
        mDiscoveryListener = new IpDiscoveryListener(this);
        mNsdManager.discoverServices(SERVICE_TYPE, NsdManager.PROTOCOL_DNS_SD, mDiscoveryListener);
    }

    public void stopDiscovery() {
        mNsdManager.stopServiceDiscovery(mDiscoveryListener);
    }

    public void handleIpAddress(String ipAddress) {
        Log.d("IP", ipAddress);
        mainViewModel.initializeEsp8266Api(ipAddress);
    }

}
