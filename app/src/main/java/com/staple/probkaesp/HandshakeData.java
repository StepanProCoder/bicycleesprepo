package com.staple.probkaesp;

import com.google.gson.annotations.SerializedName;

public class HandshakeData {
    @SerializedName("ssid")
    private String ssid;
    @SerializedName("password")
    private String password;
    @SerializedName("id")
    private String id;

    public HandshakeData(String ssid, String password, String id) {
        this.ssid = ssid;
        this.password = password;
        this.id = id;
    }
}

