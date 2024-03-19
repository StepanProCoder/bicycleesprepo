package com.staple.probkaesp.handlers;

import android.content.Context;
import android.util.Log;

import com.staple.probkaesp.datamodels.HandshakeData;
import com.staple.probkaesp.utils.saveloadprefs.SaveLoadResult;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HandshakeHandler implements Callback<ResponseBody> {

    Boolean isHandshaked;
    HandshakeData handshakeData;
    Context context;

    public HandshakeHandler(Boolean isHandshaked, HandshakeData handshakeData, Context context) {
        this.isHandshaked = isHandshaked;
        this.handshakeData = handshakeData;
        this.context = context;
    }

    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        isHandshaked = true;
        //Log.d("ID", handshakeData.getId());
        //SaveLoadResult.saveResult("SystemSensors", "id", handshakeData.getId(), context);
        try {
            //Log.d("HANDSHAKE", response.body().string());
            SaveLoadResult.saveResult("SystemSensors", "uuid", response.body().string(), context);
            Log.d("SAVEDUUID", SaveLoadResult.loadResult("SystemSensors", "uuid", context));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onFailure(Call<ResponseBody> call, Throwable t) {
        isHandshaked = false;
        Log.d("HANDSHAKE", "ERROR");
    }
}