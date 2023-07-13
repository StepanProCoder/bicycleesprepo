package com.staple.probkaesp;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Esp8266Api {
    @GET("/speed/")
    Call<SwitchStatus> getSwitchStatus();
}

