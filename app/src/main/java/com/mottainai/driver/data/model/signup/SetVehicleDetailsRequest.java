package com.mottainai.driver.data.model.signup;

import com.google.gson.annotations.SerializedName;

public class SetVehicleDetailsRequest {

    @SerializedName("account_id")
    public String accountId;
    @SerializedName("vehicle_type")
    public String vehicleType;
    @SerializedName("vehicle_registration_number")
    public String vehicleRegistrationNumber;
    @SerializedName("registration_expiry_date")
    public String registrationExpiryDate;
    @SerializedName("registration_certificate")
    public String registrationCertificate;
    @SerializedName("insurance_certificate")
    public String insuranceCertificate;

    public SetVehicleDetailsRequest(String accountId, String vehicleType, String vehicleRegistrationNumber,
                                    String registrationExpiryDate, String registrationCertificate, String insuranceCertificate) {
        this.accountId = accountId;
        this.vehicleType = vehicleType;
        this.vehicleRegistrationNumber = vehicleRegistrationNumber;
        this.registrationExpiryDate = registrationExpiryDate;
        this.registrationCertificate = registrationCertificate;
        this.insuranceCertificate = insuranceCertificate;
    }
}
