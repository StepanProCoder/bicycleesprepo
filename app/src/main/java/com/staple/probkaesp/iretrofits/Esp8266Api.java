package com.staple.probkaesp.iretrofits;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Esp8266Api {
    @GET("/sensors/")
    Call<ResponseBody> getSensorData(@Query("latitude") double latitude, @Query("longitude") double longitude);

    @POST("/sensors/")
    Call<ResponseBody> postConfig(@Body RequestBody requestBody);
}

