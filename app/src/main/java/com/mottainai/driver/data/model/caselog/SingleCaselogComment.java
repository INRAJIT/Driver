
package com.mottainai.driver.data.model.caselog;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SingleCaselogComment {

    @SerializedName("case_id")
    @Expose
    private String caseId;
    @SerializedName("comments")
    @Expose
    private String comments;
    @SerializedName("sender_type")
    @Expose
    private String senderType;

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getSenderType() {
        return senderType;
    }

    public void setSenderType(String senderType) {
        this.senderType = senderType;
    }

}
