package com.mottainai.driver.data.model.signup;

import com.google.gson.annotations.SerializedName;

public class OtpRequest {

    @SerializedName("user_id")
    public String userId;
    @SerializedName("user_type")
    public String userType;
    @SerializedName("otp_for")
    public String otpFor;
    @SerializedName("enter_otp")
    public String otp;
    @SerializedName("is_user_active")
    public String isUserActive;

    public OtpRequest(String userId, String userType, String otpFor, String otp, String isUserActive) {
        this.userId = userId;
        this.userType = userType;
        this.otpFor = otpFor;
        this.otp = otp;
        this.isUserActive = isUserActive;
    }
}
