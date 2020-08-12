package com.mottainai.driver.data.model.forgetpassword;

import com.google.gson.annotations.SerializedName;

public class ResetPasswordRequest {

    @SerializedName("driver_id")
    public String driverId;
    @SerializedName("new_password")
    public String newPassword;
    @SerializedName("cnf_password")
    public String cnfPassword;

    public ResetPasswordRequest(String driverId, String newPassword, String cnfPassword) {
        this.driverId = driverId;
        this.newPassword = newPassword;
        this.cnfPassword = cnfPassword;
    }
}
