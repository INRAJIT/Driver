package com.mottainai.driver.data.model.home;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WasteStreamUploadRequest {

    public List<WasteStreamUpload> getWasteStream() {
        return wasteStream;
    }

    public void setWasteStream(List<WasteStreamUpload> wasteStream) {
        this.wasteStream = wasteStream;
    }

    @SerializedName("waste_stream")
    private List<WasteStreamUpload> wasteStream = null;
}
