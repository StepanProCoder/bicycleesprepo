package com.staple.probkaesp.handlers;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResponsePostHandler implements Callback<ResponseBody>
{
    private MutableLiveData<Boolean> isGetOrPost;
    private Runnable handshakeLambda;

    public ResponsePostHandler(MutableLiveData<Boolean> isGetOrPost, Runnable handshakeLambda)
    {
        this.isGetOrPost = isGetOrPost;
        this.handshakeLambda = handshakeLambda;
    }

    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response)
    {
        try
        {
            if(response.body().string().equals("RESETTING"))
            {
                Log.d("HSHK", "handshaking again");
                handshakeLambda.run();
            }
            else
            {
                isGetOrPost.setValue(true);
            }
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onFailure(Call<ResponseBody> call, Throwable t)
    {
        isGetOrPost.setValue(false);
    }
}
