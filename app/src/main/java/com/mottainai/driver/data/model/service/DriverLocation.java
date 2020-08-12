
package com.mottainai.driver.data.model.service;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DriverLocation {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("Location")
    @Expose
    private Location location;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

}
