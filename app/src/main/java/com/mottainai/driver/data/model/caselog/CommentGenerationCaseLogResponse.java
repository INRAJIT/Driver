
package com.mottainai.driver.data.model.caselog;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CommentGenerationCaseLogResponse {

    @SerializedName("status")
    @Expose
    private boolean status;
    @SerializedName("success")
    @Expose
    private String success;

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

}
