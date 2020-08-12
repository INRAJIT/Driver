package com.mottainai.driver.data.model.status;

import com.google.gson.annotations.SerializedName;

public class DriverStatusRequest {

    @SerializedName("driver_id")
    public String driverId;
    @SerializedName("working_status")
    public int workingStatus;

    public DriverStatusRequest(String driverId, int workingStatus) {
        this.driverId = driverId;
        this.workingStatus = workingStatus;
    }
}
