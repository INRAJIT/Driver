
package com.mottainai.driver.data.model.home;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mottainai.driver.data.model.login.Driver;

public class DriverPickupResponse {

    @SerializedName("status")
    @Expose
    private boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("pickup")
    @Expose
    private List<Pickup> pickup = null;
    @SerializedName("total_page")
    @Expose
    private Integer totalPage;

    public DriverPickupResponse(boolean status, String message) {
        this.status = status;
        this.message = message;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Pickup> getPickup() {
        return pickup;
    }

    public void setPickup(List<Pickup> pickup) {
        this.pickup = pickup;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

}
