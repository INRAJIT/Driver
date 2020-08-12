package com.mottainai.driver.data.model.caselog;

import com.google.gson.annotations.SerializedName;

public class CommentGenerationCaseLogRequest {
    @SerializedName("case_id")
    public String caseId;
    @SerializedName("comments")
    public String comments;
    @SerializedName("sender_type")
    public String senderType;

    public CommentGenerationCaseLogRequest(String caseId, String comments, String senderType) {
        this.caseId = caseId;
        this.comments = comments;
        this.senderType = senderType;
    }
}
