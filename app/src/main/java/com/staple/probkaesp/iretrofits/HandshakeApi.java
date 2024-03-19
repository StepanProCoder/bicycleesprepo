package com.staple.probkaesp.iretrofits;

import com.staple.probkaesp.datamodels.HandshakeData;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface HandshakeApi {
    @POST("/handshaker/")
    Call<ResponseBody> postHandshake(@Body HandshakeData handshakeData);
}
