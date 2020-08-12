
package com.mottainai.driver.data.model.profile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DrivingLicence {

    @SerializedName("Licence Image")
    @Expose
    private String licenceImage;
    @SerializedName("Licence Number")
    @Expose
    private String licenceNumber;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Date_of_Birth")
    @Expose
    private String dateOfBirth;
    @SerializedName("Issue_Validity_Period")
    @Expose
    private String issueValidityPeriod;
    @SerializedName("Expire_Validity_Period")
    @Expose
    private String expireValidityPeriod;
    @SerializedName("Next_to_kin_phone_no")
    @Expose
    private String nextToKinPhoneNo;
    @SerializedName("Nationality")
    @Expose
    private String nationality;

    public String getLicenceImage() {
        return licenceImage;
    }

    public void setLicenceImage(String licenceImage) {
        this.licenceImage = licenceImage;
    }

    public String getLicenceNumber() {
        return licenceNumber;
    }

    public void setLicenceNumber(String licenceNumber) {
        this.licenceNumber = licenceNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getIssueValidityPeriod() {
        return issueValidityPeriod;
    }

    public void setIssueValidityPeriod(String issueValidityPeriod) {
        this.issueValidityPeriod = issueValidityPeriod;
    }

    public String getExpireValidityPeriod() {
        return expireValidityPeriod;
    }

    public void setExpireValidityPeriod(String expireValidityPeriod) {
        this.expireValidityPeriod = expireValidityPeriod;
    }

    public String getNextToKinPhoneNo() {
        return nextToKinPhoneNo;
    }

    public void setNextToKinPhoneNo(String nextToKinPhoneNo) {
        this.nextToKinPhoneNo = nextToKinPhoneNo;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }


}
