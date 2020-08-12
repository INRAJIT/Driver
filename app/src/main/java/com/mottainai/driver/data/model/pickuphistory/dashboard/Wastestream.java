
package com.mottainai.driver.data.model.pickuphistory.dashboard;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Wastestream {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("waste_stream_name")
    @Expose
    private String wasteStreamName;
    @SerializedName("quantityinkg")
    @Expose
    private String quantityinkg;
    @SerializedName("quantityingms")
    @Expose
    private String quantityingms;
    @SerializedName("Image")
    @Expose
    private String image;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWasteStreamName() {
        return wasteStreamName;
    }

    public void setWasteStreamName(String wasteStreamName) {
        this.wasteStreamName = wasteStreamName;
    }

    public String getQuantityinkg() {
        return quantityinkg;
    }

    public void setQuantityinkg(String quantityinkg) {
        this.quantityinkg = quantityinkg;
    }

    public String getQuantityingms() {
        return quantityingms;
    }

    public void setQuantityingms(String quantityingms) {
        this.quantityingms = quantityingms;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
