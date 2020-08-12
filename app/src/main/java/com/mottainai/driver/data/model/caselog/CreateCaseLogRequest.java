package com.mottainai.driver.data.model.caselog;

import com.google.gson.annotations.SerializedName;

public class CreateCaseLogRequest {
    @SerializedName("case_category_id")
    public String caseCategoryId;
    @SerializedName("comments")
    public String comments;
    @SerializedName("case_type")
    public String caseType;
    @SerializedName("generator_type")
    public String generatorType;
    @SerializedName("case_generator_id")
    public String caseGeneratorId;

    public CreateCaseLogRequest(String caseCategoryId, String comments, String caseType, String generatorType , String caseGeneratorId) {
        this.caseCategoryId = caseCategoryId;
        this.comments = comments;
        this.caseType = caseType;
        this.generatorType = generatorType;
        this.caseGeneratorId = caseGeneratorId;
    }
}
