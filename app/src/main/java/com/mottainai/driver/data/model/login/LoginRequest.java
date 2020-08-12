package com.mottainai.driver.data.model.login;

import com.google.gson.annotations.SerializedName;

public class LoginRequest {

    @SerializedName("email")
    public String email;
    @SerializedName("password")
    public String password;
    @SerializedName("device_token")
    public String deviceToken;

    public LoginRequest(String email, String password, String deviceToken) {
        this.email = email;
        this.password = password;
        this.deviceToken = deviceToken;
    }
}
