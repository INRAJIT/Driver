
package com.mottainai.driver.data.model.profile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DrivingLicenceResponse {

    @SerializedName("status")
    @Expose
    private boolean status;
    @SerializedName("success")
    @Expose
    private DrivingLicence success;

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public DrivingLicence getSuccess() {
        return success;
    }

    public void setSuccess(DrivingLicence success) {
        this.success = success;
    }

}
