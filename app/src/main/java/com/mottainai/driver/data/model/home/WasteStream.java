
package com.mottainai.driver.data.model.home;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WasteStream {

    @SerializedName("pickup_id")
    @Expose
    private Integer pickupId;
    @SerializedName("waste_stream_name_id")
    @Expose
    private Integer wasteStreamNameId;
    @SerializedName("waste_stream_name")
    @Expose
    private String wasteStreamName;
    @SerializedName("container")
    @Expose
    private List<Container> container = null;

    public Integer getPickupId() {
        return pickupId;
    }
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

    public List<Container> getContainer() {
        return container;
    }

    public void setContainer(List<Container> container) {
        this.container = container;
    }

}
