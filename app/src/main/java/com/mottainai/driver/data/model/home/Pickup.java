
package com.mottainai.driver.data.model.home;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pickup {

    @SerializedName("customer_id")
    @Expose
    private Integer customerId;
    @SerializedName("address_id")
    @Expose
    private Integer addressId;
    @SerializedName("customer_name")
    @Expose
    private String customerName;
    @SerializedName("customertype")
    @Expose
    private String customertype;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("latitude")
    @Expose
    private Double latitude;
    @SerializedName("longitude")
    @Expose
    private Double longitude;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("pincode")
    @Expose
    private Integer pincode;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("pickup_end_time")
    @Expose
    private Object pickupEndTime;
    @SerializedName("pickup_date")
    @Expose
    private String pickupDate;
    @SerializedName("is_accept")
    @Expose
    private boolean isAccept;
    @SerializedName("wastestream")
    @Expose
    private List<String> wastestream = null;
    @SerializedName("pickupsId")
    @Expose
    private List<Integer> pickupsId = null;
    @SerializedName("orderId")
    @Expose
    private List<Integer> orderId = null;

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomertype() {
        return customertype;
    }

    public void setCustomertype(String customertype) {
        this.customertype = customertype;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getPincode() {
        return pincode;
    }

    public void setPincode(Integer pincode) {
        this.pincode = pincode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Object getPickupEndTime() {
        return pickupEndTime;
    }

    public void setPickupEndTime(Object pickupEndTime) {
        this.pickupEndTime = pickupEndTime;
    }

    public String getPickupDate() {
        return pickupDate;
    }

    public void setPickupDate(String pickupDate) {
        this.pickupDate = pickupDate;
    }

    public boolean isAccept() {
        return isAccept;
    }

    public void setAccept(boolean accept) {
        isAccept = accept;
    }

    public List<String> getWastestream() {
        return wastestream;
    }

    public void setWastestream(List<String> wastestream) {
        this.wastestream = wastestream;
    }

    public List<Integer> getPickupsId() {
        return pickupsId;
    }

    public void setPickupsId(List<Integer> pickupsId) {
        this.pickupsId = pickupsId;
    }

    public List<Integer> getOrderId() {
        return orderId;
    }

    public void setOrderId(List<Integer> orderId) {
        this.orderId = orderId;
    }

}
