package com.mottainai.driver.data.model.home;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PickupWasteStreamRequest {

    @SerializedName("pickups_id")
    public List<Integer> pickupId;

    public PickupWasteStreamRequest(List<Integer> pickupId) {
        this.pickupId = pickupId;
    }
}
