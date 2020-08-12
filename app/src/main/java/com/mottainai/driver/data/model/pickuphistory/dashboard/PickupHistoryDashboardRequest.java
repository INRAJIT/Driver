package com.mottainai.driver.data.model.pickuphistory.dashboard;

import com.google.gson.annotations.SerializedName;

public class PickupHistoryDashboardRequest {
    @SerializedName("driver_id")
    public String driverId;
    @SerializedName("pickup_month")
    public int month;
    @SerializedName("pickup_year")
    public int year;

    public PickupHistoryDashboardRequest(String driverId, int month, int year) {
        this.driverId = driverId;
        this.month = month;
        this.year = year;
    }
}
