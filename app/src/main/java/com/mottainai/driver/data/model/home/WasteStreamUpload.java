package com.mottainai.driver.data.model.home;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WasteStreamUpload {
   // added ...
    @SerializedName("pickup_id")
    @Expose
    private Integer pickupId;
    @SerializedName("waste_stream_name_id")
    private Integer wasteStreamNameId;
    @SerializedName("waste_stream_name")
    private String wasteStreamName;
    @SerializedName("container")
    private List<ContainerRequest> container = null;
    @SerializedName("bags")
    @Expose
    private List<Bag> bags = null;

    //added ...
    public Integer getPickupId() {
        return pickupId;
    }
    //added ...
    public void setPickupId(Integer pickupId) {
        this.pickupId = pickupId;
    }

    public Integer getWasteStreamNameId() {
        return wasteStreamNameId;
    }

    public void setWasteStreamNameId(Integer wasteStreamNameId) {
        this.wasteStreamNameId = wasteStreamNameId;
    }

    public String getWasteStreamName() {
        return wasteStreamName;
    }

    public void setWasteStreamName(String wasteStreamName) {
        this.wasteStreamName = wasteStreamName;
    }

    public List<ContainerRequest> getContainer() {
        return container;
    }

    public void setContainer(List<ContainerRequest> container) {
        this.container = container;
    }

    public List<Bag> getBags() {
        return bags;
    }

    public void setBags(List<Bag> bags) {
        this.bags = bags;
    }
}
