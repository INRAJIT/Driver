package com.mottainai.driver.data.model.home;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AcceptPickupRequest {
    @SerializedName("driver_id")
    public String driverId;
    @SerializedName("pickup_id")
    public List<Integer> pickupId;

    public AcceptPickupRequest(String driverId, List<Integer> pickupId) {
        this.driverId = driverId;
        this.pickupId = pickupId;
    }
}
