
package com.mottainai.driver.data.model.profile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VehicleDetails {

    @SerializedName("chassis_number")
    @Expose
    private String chassisNumber;
    @SerializedName("vehicle_image")
    @Expose
    private String vehicleImage;
    @SerializedName("vehicle_registration_number")
    @Expose
    private String vehicleRegistrationNumber;
    @SerializedName("vehicle_registration_date")
    @Expose
    private String vehicleRegistrationDate;
    @SerializedName("vehicle_Model_No")
    @Expose
    private String vehicleModelNo;
    @SerializedName("gps_device_id")
    @Expose
    private String gPSDeviceId;
    @SerializedName("owner_name")
    @Expose
    private String ownerName;
    @SerializedName("fuel_type")
    @Expose
    private String fuelType;

    public String getChassisNumber() {
        return chassisNumber;
    }

    public void setChassisNumber(String chassisNumber) {
        this.chassisNumber = chassisNumber;
    }

    public String getVehicleImage() {
        return vehicleImage;
    }

    public void setVehicleImage(String vehicleImage) {
        this.vehicleImage = vehicleImage;
    }

    public String getVehicleRegistrationNumber() {
        return vehicleRegistrationNumber;
    }

    public void setVehicleRegistrationNumber(String vehicleRegistrationNumber) {
        this.vehicleRegistrationNumber = vehicleRegistrationNumber;
    }

    public String getVehicleRegistrationDate() {
        return vehicleRegistrationDate;
    }

    public void setVehicleRegistrationDate(String vehicleRegistrationDate) {
        this.vehicleRegistrationDate = vehicleRegistrationDate;
    }

    public String getVehicleModelNo() {
        return vehicleModelNo;
    }

    public void setVehicleModelNo(String vehicleModelNo) {
        this.vehicleModelNo = vehicleModelNo;
    }

    public String getGPSDeviceId() {
        return gPSDeviceId;
    }

    public void setGPSDeviceId(String gPSDeviceId) {
        this.gPSDeviceId = gPSDeviceId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

}
