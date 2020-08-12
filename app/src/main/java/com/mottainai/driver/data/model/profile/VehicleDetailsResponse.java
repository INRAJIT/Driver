
package com.mottainai.driver.data.model.profile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VehicleDetailsResponse {

    @SerializedName("status")
    @Expose
    private boolean status;
    @SerializedName("success")
    @Expose
    private VehicleDetails success;

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public VehicleDetails getSuccess() {
        return success;
    }

    public void setSuccess(VehicleDetails success) {
        this.success = success;
    }

}
