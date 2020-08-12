
package com.mottainai.driver.data.model.pickuphistory.history;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WasteStream {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("waste_stream_name")
    @Expose
    private String wasteStreamName;
    @SerializedName("quantityinkg")
    @Expose
    private Integer quantityinkg;
    @SerializedName("quantityingms")
    @Expose
    private Integer quantityingms;
    @SerializedName("Bindetails")
    @Expose
    private String bindetails;

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

    public Integer getQuantityinkg() {
        return quantityinkg;
    }

    public void setQuantityinkg(Integer quantityinkg) {
        this.quantityinkg = quantityinkg;
    }

    public Integer getQuantityingms() {
        return quantityingms;
    }

    public void setQuantityingms(Integer quantityingms) {
        this.quantityingms = quantityingms;
    }

    public String getBindetails() {
        return bindetails;
    }

    public void setBindetails(String bindetails) {
        this.bindetails = bindetails;
    }

}
