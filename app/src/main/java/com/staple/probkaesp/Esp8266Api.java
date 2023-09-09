package com.staple.probkaesp;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Esp8266Api {
    @GET("/sensors/")
    Call<ResponseBody> getSensorData();

    @POST("/sensors/")
    Call<ResponseBody> postConfig(@Body RequestBody requestBody);
}

