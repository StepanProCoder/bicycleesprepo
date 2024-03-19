package com.staple.probkaesp.handshaker.router;

import android.content.Context;

import com.staple.probkaesp.utils.activity.ActivityUtils;
import com.staple.probkaesp.LottieActivity;

public class HandshakeRouter
{
    private Context handshakeContext;
    public HandshakeRouter(Context context)
    {
        handshakeContext = context;
    }
    public void shiftToLottie()
    {
        ActivityUtils.startNewActivity(handshakeContext, LottieActivity.class);
    }
}
