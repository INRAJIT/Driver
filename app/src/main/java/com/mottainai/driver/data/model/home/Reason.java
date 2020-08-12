
package com.mottainai.driver.data.model.home;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Reason {

    @SerializedName("reason_id")
    @Expose
    private Integer reasonId;
    @SerializedName("reason_name")
    @Expose
    private String reasonName;

    public Integer getReasonId() {
        return reasonId;
    }

    public void setReasonId(Integer reasonId) {
        this.reasonId = reasonId;
    }

    public String getReasonName() {
        return reasonName;
    }

    public void setReasonName(String reasonName) {
        this.reasonName = reasonName;
    }

}
