package com.mottainai.driver.data.model.signup;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CityResponse {

    @SerializedName("status")
    @Expose
    private boolean status;
    @SerializedName("cities")
    @Expose
    private List<StateCity> cities = null;

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<StateCity> getCities() {
        return cities;
    }

    public void setCities(List<StateCity> cities) {
        this.cities = cities;
    }

}
