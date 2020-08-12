
package com.mottainai.driver.data.model.signup;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VehicleTypeResponse {

    @SerializedName("vehicleType")
    @Expose
    private List<VehicleType> vehicleType = null;

    public List<VehicleType> getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(List<VehicleType> vehicleType) {
        this.vehicleType = vehicleType;
    }

}
