package com.staple.probkaesp;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface HandshakeApi {
    @POST("/handshaker/")
    Call<ResponseBody> postHandshake(@Body HandshakeData handshakeData);
}
