package com.mottainai.driver.data.model.signup;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StateResponse {

    @SerializedName("status")
    @Expose
    private boolean status;
    @SerializedName("states")
    @Expose
    private List<StateCity> states = null;

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<StateCity> getStates() {
        return states;
    }

    public void setStates(List<StateCity> states) {
        this.states = states;
    }
}