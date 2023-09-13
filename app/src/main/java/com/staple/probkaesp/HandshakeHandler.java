package com.staple.probkaesp;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HandshakeHandler implements Callback<ResponseBody> {

    MutableLiveData<Boolean> isHandshaked;

    public HandshakeHandler(MutableLiveData<Boolean> isHandshaked) {
        this.isHandshaked = isHandshaked;
    }

    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        isHandshaked.setValue(true);
        try {
            Log.d("HANDSHAKE", response.body().string());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onFailure(Call<ResponseBody> call, Throwable t) {
        isHandshaked.setValue(false);
        Log.d("HANDSHAKE", "ERROR");
    }
}