package com.mottainai.driver.data.model.signup;

import com.google.gson.annotations.SerializedName;

public class SetLoginDetailsRequest {

    @SerializedName("account_id")
    public String accountId;
    @SerializedName("email")
    public String email;
    @SerializedName("password")
    public String password;

    public SetLoginDetailsRequest(String accountId, String email, String password) {
        this.accountId = accountId;
        this.email = email;
        this.password = password;
    }
}
