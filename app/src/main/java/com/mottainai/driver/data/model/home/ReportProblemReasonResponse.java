
package com.mottainai.driver.data.model.home;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReportProblemReasonResponse {

    @SerializedName("status")
    @Expose
    private boolean status;
    @SerializedName("reasons")
    @Expose
    private List<Reason> reasons = null;

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<Reason> getReasons() {
        return reasons;
    }

    public void setReasons(List<Reason> reasons) {
        this.reasons = reasons;
    }

}
