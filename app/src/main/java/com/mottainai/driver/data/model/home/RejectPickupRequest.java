package com.mottainai.driver.data.model.home;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RejectPickupRequest {
    @SerializedName("driver_id")
    public String driverId;
    @SerializedName("request_type")
    public int requestType;
    @SerializedName("reason_id")
    public int reasonId;
    @SerializedName("pickup_id")
    public List<Integer> pickupId;
    @SerializedName("is_other")
    public int isOther;
    @SerializedName("reason_comment")
    public String reasonComment;

    public RejectPickupRequest(String driverId, int requestType, int reasonId,
                               List<Integer> pickupId, int isOther) {
        this.driverId = driverId;
        this.requestType = requestType;
        this.reasonId = reasonId;
        this.pickupId = pickupId;
        this.isOther = isOther;
    }

    public RejectPickupRequest(String driverId, int requestType, int reasonId,
                               List<Integer> pickupId, int isOther, String reasonComment) {
        this.driverId = driverId;
        this.requestType = requestType;
        this.reasonId = reasonId;
        this.pickupId = pickupId;
        this.isOther = isOther;
        this.reasonComment = reasonComment;
    }
}
