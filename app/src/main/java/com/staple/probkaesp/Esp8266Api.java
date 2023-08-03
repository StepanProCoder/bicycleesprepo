package com.staple.probkaesp;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

public interface Esp8266Api {
    @GET("/sensors/")
    Call<ResponseBody> getSensorData();
}

