
package com.mottainai.driver.data.model.profile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DriverProfile {

    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Contact_No")
    @Expose
    private String contactNo;
    @SerializedName("Email")
    @Expose
    private String email;
    @SerializedName("driving_licence_no")
    @Expose
    private String drivingLicenceNo;
    @SerializedName("DOB")
    @Expose
    private String dOB;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("driver_zone")
    @Expose
    private List<Object> driverZone = null;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDrivingLicenceNo() {
        return drivingLicenceNo;
    }

    public void setDrivingLicenceNo(String drivingLicenceNo) {
        this.drivingLicenceNo = drivingLicenceNo;
    }

    public String getDOB() {
        return dOB;
    }

    public void setDOB(String dOB) {
        this.dOB = dOB;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<Object> getDriverZone() {
        return driverZone;
    }

    public void setDriverZone(List<Object> driverZone) {
        this.driverZone = driverZone;
    }

}
