package com.staple.probkaesp;

import androidx.lifecycle.MutableLiveData;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResponsePostHandler implements Callback<ResponseBody> {

    MutableLiveData<Boolean> statusGetOrPost;

    public ResponsePostHandler(MutableLiveData<Boolean> statusGetOrPost) {
        this.statusGetOrPost = statusGetOrPost;
    }

    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        statusGetOrPost.setValue(true);
    }

    @Override
    public void onFailure(Call<ResponseBody> call, Throwable t) {
        statusGetOrPost.setValue(false);
    }
}
