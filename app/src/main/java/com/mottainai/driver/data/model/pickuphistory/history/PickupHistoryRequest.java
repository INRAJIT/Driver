package com.mottainai.driver.data.model.pickuphistory.history;

import com.google.gson.annotations.SerializedName;

public class PickupHistoryRequest {
    @SerializedName("driver_id")
    public String driverId;
    @SerializedName("filter_by")
    public int filterBy;
    @SerializedName("date_type")
    public String dateType;
    @SerializedName("filter_date")
    public String filterDate;

    public PickupHistoryRequest(String driverId, int filterBy, String dateType, String filterDate) {
        this.driverId = driverId;
        this.filterBy = filterBy;
        this.dateType = dateType;
        this.filterDate = filterDate;
    }
}
