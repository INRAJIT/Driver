
package com.mottainai.driver.data.model.home;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PickupWasteStreamResponse {

    @SerializedName("status")
    @Expose
    private boolean status;
    @SerializedName("waste_stream")
    @Expose
    private List<WasteStream> wasteStream = null;

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<WasteStream> getWasteStream() {
        return wasteStream;
    }

    public void setWasteStream(List<WasteStream> wasteStream) {
        this.wasteStream = wasteStream;
    }

}
