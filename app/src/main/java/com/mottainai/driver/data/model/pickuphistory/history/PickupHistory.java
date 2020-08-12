
package com.mottainai.driver.data.model.pickuphistory.history;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PickupHistory {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("driver_id")
    @Expose
    private Integer driverId;
    @SerializedName("Customer_name")
    @Expose
    private String customerName;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("pickup_start_time")
    @Expose
    private String pickupStartTime;
    @SerializedName("customer_type")
    @Expose
    private String customerType;
    @SerializedName("pickup_date")
    @Expose
    private String pickupDate;
    @SerializedName("WasteStream")
    @Expose
    private List<WasteStream> wasteStream = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDriverId() {
        return driverId;
    }

    public void setDriverId(Integer driverId) {
        this.driverId = driverId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPickupStartTime() {
        return pickupStartTime;
    }

    public void setPickupStartTime(String pickupStartTime) {
        this.pickupStartTime = pickupStartTime;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public String getPickupDate() {
        return pickupDate;
    }

    public void setPickupDate(String pickupDate) {
        this.pickupDate = pickupDate;
    }

    public List<WasteStream> getWasteStream() {
        return wasteStream;
    }

    public void setWasteStream(List<WasteStream> wasteStream) {
        this.wasteStream = wasteStream;
    }

}
