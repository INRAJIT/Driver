
package com.mottainai.driver.data.model.pickuphistory.dashboard;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Response {

    @SerializedName("Total_Pickups")
    @Expose
    private Integer totalPickups;
    @SerializedName("Wastestream")
    @Expose
    private List<Wastestream> wastestream = null;

    public Integer getTotalPickups() {
        return totalPickups;
    }

    public void setTotalPickups(Integer totalPickups) {
        this.totalPickups = totalPickups;
    }

    public List<Wastestream> getWastestream() {
        return wastestream;
    }

    public void setWastestream(List<Wastestream> wastestream) {
        this.wastestream = wastestream;
    }

}
