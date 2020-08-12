package com.mottainai.driver.data.model.profile;

import com.google.gson.annotations.SerializedName;

public class UpdatePasswordRequest {

    @SerializedName("id")
    public String id;
    @SerializedName("old_password")
    public String oldPassword;
    @SerializedName("new_password")
    public String newPassword;
    @SerializedName("cnf_password")
    public String cnfPassword;

    public UpdatePasswordRequest(String id, String oldPassword, String newPassword, String cnfPassword) {
        this.id = id;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
        this.cnfPassword = cnfPassword;
    }
}
