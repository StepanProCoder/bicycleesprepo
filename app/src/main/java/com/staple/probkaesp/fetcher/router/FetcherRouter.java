package com.staple.probkaesp.fetcher.router;

import android.content.Context;

import com.staple.probkaesp.utils.activity.ActivityUtils;
import com.staple.probkaesp.handshaker.view.HandshakeActivity;
import com.staple.probkaesp.SensorSelectionActivity;

public class FetcherRouter
{
    private Context fetcherContext;
    public FetcherRouter(Context context)
    {
        this.fetcherContext = context;
    }

    public void shiftToHandshake()
    {
        ActivityUtils.startNewActivity(fetcherContext, HandshakeActivity.class);
    }

    public void shiftToSensorSelection()
    {
        ActivityUtils.startNewActivity(fetcherContext, SensorSelectionActivity.class);
    }

}
