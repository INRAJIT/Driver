package com.mottainai.driver.data.model.home;

import com.google.gson.annotations.SerializedName;

public class DriverLogoutRequest {

    @SerializedName("driver_id")
    public String driverId;

    public DriverLogoutRequest(String driverId) {
        this.driverId = driverId;
    }
}
