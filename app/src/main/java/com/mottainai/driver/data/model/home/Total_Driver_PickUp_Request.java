package com.mottainai.driver.data.model.home;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Total_Driver_PickUp_Request {

    @SerializedName("driver_id")
    public String driverId;
    @SerializedName("pickup_id")
    public List<Integer> pickupId;
    @SerializedName("bags_title")
    public List<Integer> bagsTitle;
    @SerializedName("bags_quantity")
    public List<Integer> bagsQuantity;
    @SerializedName("bags_weight_kg")
    public List<Integer> bagsWeightKg;
    @SerializedName("bags_weight_gm")
    public List<Integer> bagsWeightGm;
    @SerializedName("barcode")
    public List<Integer> barCode;
    @SerializedName("container_id")
    public List<Integer> containerId;
    @SerializedName("container_qty")
    public List<Integer> containerQty;

    public Total_Driver_PickUp_Request(String driverId, List<Integer> pickupId, List<Integer> bagsTitle, List<Integer> bagsQuantity, List<Integer> bagsWeightKg, List<Integer> bagsWeightGm, List<Integer> barCode, List<Integer> containerId, List<Integer> containerQty) {
        this.driverId = driverId;
        this.pickupId = pickupId;
        this.bagsTitle = bagsTitle;
        this.bagsQuantity = bagsQuantity;
        this.bagsWeightKg = bagsWeightKg;
        this.bagsWeightGm = bagsWeightGm;
        this.barCode = barCode;
        this.containerId = containerId;
        this.containerQty = containerQty;
    }

}
