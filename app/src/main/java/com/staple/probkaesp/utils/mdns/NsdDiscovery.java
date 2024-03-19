package com.staple.probkaesp.utils.mdns;
import android.content.Context;
import android.net.nsd.NsdManager;
import android.util.Log;

import com.staple.probkaesp.fetcher.interactor.FetcherInteractor;
import com.staple.probkaesp.utils.mdns.IpDiscoveryListener;

public class NsdDiscovery {

    public static final String SERVICE_TYPE = "_speedesp._tcp.";
    private Context mContext;
    private NsdManager mNsdManager;
    private FetcherInteractor fetcherInteractor;
    private NsdManager.DiscoveryListener mDiscoveryListener;

    public NsdDiscovery(FetcherInteractor fetcherInteractor, Context context) {
        this.fetcherInteractor = fetcherInteractor;
        this.mContext = context;
        this.mNsdManager = (NsdManager) mContext.getSystemService(Context.NSD_SERVICE);
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

    public void handleIpAddress(String hostName, String ipAddress) {
        Log.d("IP", ipAddress);
        fetcherInteractor.initializeEsp8266Api(hostName, ipAddress);
    }

}
