package com.staple.probkaesp;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HandShakeViewModel extends ViewModel {
    private HandshakeApi handshakeApi;
    private MutableLiveData<Boolean> isHandshaked = new MutableLiveData<>();

    public void initializeHandshakeApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.4.1")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        handshakeApi = retrofit.create(HandshakeApi.class);
    }

    public void postHandshake(HandshakeData handshakeData, Context context) {
        handshakeApi.postHandshake(handshakeData).enqueue(new HandshakeHandler(isHandshaked, handshakeData, context));
    }

}
