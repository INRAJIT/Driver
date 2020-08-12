package com.mottainai.driver.data.model.forgetpassword;

import com.google.gson.annotations.SerializedName;

public class VerifyEmailRequest {

    @SerializedName("email")
    public String email;

    public VerifyEmailRequest(String email) {
        this.email = email;
    }
}
