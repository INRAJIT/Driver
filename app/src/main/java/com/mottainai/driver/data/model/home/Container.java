
package com.mottainai.driver.data.model.home;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Container {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("container_name")
    @Expose
    private String containerName;
    @SerializedName("capacity")
    @Expose
    private Integer capacity;
    @SerializedName("capacity_unit")
    @Expose
    private String capacityUnit;
    @SerializedName("waste_stream_name_id")
    @Expose
    private Integer wasteStreamNameId;
    @SerializedName("waste_stream_name")
    @Expose
    private String wasteStreamName;
    @SerializedName("container_quantity")
    @Expose
    private Integer containerQuantity;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContainerName() {
        return containerName;
    }

    public void setContainerName(String containerName) {
        this.containerName = containerName;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getCapacityUnit() {
        return capacityUnit;
    }

    public void setCapacityUnit(String capacityUnit) {
        this.capacityUnit = capacityUnit;
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

    public Integer getContainerQuantity() {
        return containerQuantity;
    }

    public void setContainerQuantity(Integer containerQuantity) {
        this.containerQuantity = containerQuantity;
    }

}
