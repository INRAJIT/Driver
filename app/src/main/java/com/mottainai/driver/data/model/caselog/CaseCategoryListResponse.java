
package com.mottainai.driver.data.model.caselog;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CaseCategoryListResponse {

    @SerializedName("status")
    @Expose
    private boolean status;
    @SerializedName("success")
    @Expose
    private List<CaseCategory> success = null;

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<CaseCategory> getSuccess() {
        return success;
    }

    public void setSuccess(List<CaseCategory> success) {
        this.success = success;
    }

}
