
package com.mottainai.driver.data.model.caselog;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CaseLogListResponse {

    @SerializedName("status")
    @Expose
    private boolean status;
    @SerializedName("success")
    @Expose
    private List<CaseLog> success = null;

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<CaseLog> getSuccess() {
        return success;
    }

    public void setSuccess(List<CaseLog> success) {
        this.success = success;
    }

}
