package com.mottainai.driver.data.model.signup;

import com.google.gson.annotations.SerializedName;

public class SignupRequest {

    @SerializedName("hauler_type")
    public String haulerType;
    @SerializedName("company_name")
    public String companyName;
    @SerializedName("registration_no")
    public String registrationNo;
    @SerializedName("address")
    public String address;
    @SerializedName("country")
    public String country;
    @SerializedName("state")
    public String state;
    @SerializedName("city")
    public String city;
    @SerializedName("pincode")
    public String pincode;
    @SerializedName("first_name")
    public String firstName;
    @SerializedName("middle_name")
    public String middleName;
    @SerializedName("last_name")
    public String lastName;
    @SerializedName("phone")
    public String phone;

    public SignupRequest(String haulerType, String firstName, String middleName, String lastName,
                         String phone, String address, String country, String state, String city, String pincode) {
        this.haulerType = haulerType;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.phone = phone;
        this.address = address;
        this.country = country;
        this.state = state;
        this.city = city;
        this.pincode = pincode;
    }

    public SignupRequest(String haulerType, String companyName, String registrationNo,
                         String address, String country, String state, String city, String pincode,
                         String firstName, String middleName, String lastName, String phone) {
        this.haulerType = haulerType;
        this.companyName = companyName;
        this.registrationNo = registrationNo;
        this.address = address;
        this.country = country;
        this.state = state;
        this.city = city;
        this.pincode = pincode;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.phone = phone;
    }
}
