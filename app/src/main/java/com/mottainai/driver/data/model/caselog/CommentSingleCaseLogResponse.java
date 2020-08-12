
package com.mottainai.driver.data.model.caselog;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CommentSingleCaseLogResponse {

    @SerializedName("status")
    @Expose
    private boolean status;
    @SerializedName("success")
    @Expose
    private List<SingleCaselogComment> success = null;

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<SingleCaselogComment> getSuccess() {
        return success;
    }

    public void setSuccess(List<SingleCaselogComment> success) {
        this.success = success;
    }

}
