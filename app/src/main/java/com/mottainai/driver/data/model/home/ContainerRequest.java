package com.mottainai.driver.data.model.home;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ContainerRequest {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("container_name")
    @Expose
    private String containerName;
    @SerializedName("container_quantity")
    @Expose
    private Integer containerQuantity;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContainerName() {
        return containerName;
    }

    public void setContainerName(String containerName) {
        this.containerName = containerName;
    }

    public Integer getContainerQuantity() {
        return containerQuantity;
    }

    public void setContainerQuantity(Integer containerQuantity) {
        this.containerQuantity = containerQuantity;
    }
}
