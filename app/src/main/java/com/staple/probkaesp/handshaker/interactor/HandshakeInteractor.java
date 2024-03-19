package com.staple.probkaesp.handshaker.interactor;

import android.content.Context;

import com.staple.probkaesp.iretrofits.HandshakeApi;
import com.staple.probkaesp.datamodels.HandshakeData;
import com.staple.probkaesp.handlers.HandshakeHandler;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HandshakeInteractor
{
    private HandshakeApi handshakeApi;
    private Boolean isHandshaked;

    public HandshakeInteractor()
    {
        initializeHandshakeApi();
    }

    public void initializeHandshakeApi()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.4.1")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        handshakeApi = retrofit.create(HandshakeApi.class);
    }

    public void postHandshake(HandshakeData handshakeData, Context context)
    {
        handshakeApi.postHandshake(handshakeData).enqueue(new HandshakeHandler(isHandshaked, handshakeData, context));
    }

}
