package com.mottainai.driver.data.model.signup;

import com.google.gson.annotations.SerializedName;

public class SetLicenceDetailsRequest {

    @SerializedName("account_id")
    public String accountId;
    @SerializedName("first_name")
    public String firstName;
    @SerializedName("middle_name")
    public String middleName;
    @SerializedName("last_name")
    public String lastName;
    @SerializedName("driving_license_number")
    public String drivingLicenceNo;
    @SerializedName("license_expiry_date")
    public String licenceExpiryDate;

    public SetLicenceDetailsRequest(String accountId, String firstName, String middleName, String lastName,
                         String drivingLicenceNo, String licenceExpiryDate) {
        this.accountId = accountId;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.drivingLicenceNo = drivingLicenceNo;
        this.licenceExpiryDate = licenceExpiryDate;
    }
}
