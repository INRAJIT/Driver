
package com.mottainai.driver.data.model.caselog;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CaseLog {

    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("comments")
    @Expose
    private String comments;
    @SerializedName("generator_type")
    @Expose
    private String generatorType;
    @SerializedName("status_info")
    @Expose
    private String statusInfo;
    @SerializedName("created_at")
    @Expose
    private String createdAt;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getGeneratorType() {
        return generatorType;
    }

    public void setGeneratorType(String generatorType) {
        this.generatorType = generatorType;
    }

    public String getStatusInfo() {
        return statusInfo;
    }

    public void setStatusInfo(String statusInfo) {
        this.statusInfo = statusInfo;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

}
