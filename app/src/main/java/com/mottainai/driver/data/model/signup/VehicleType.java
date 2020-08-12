
package com.mottainai.driver.data.model.signup;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VehicleType implements Parcelable {

    @SerializedName("vehicle_type_name")
    @Expose
    private String vehicleTypeName;
    @SerializedName("id")
    @Expose
    private String id;

    protected VehicleType(Parcel in) {
        vehicleTypeName = in.readString();
        id = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(vehicleTypeName);
        dest.writeString(id);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<VehicleType> CREATOR = new Creator<VehicleType>() {
        @Override
        public VehicleType createFromParcel(Parcel in) {
            return new VehicleType(in);
        }

        @Override
        public VehicleType[] newArray(int size) {
            return new VehicleType[size];
        }
    };

    public String getVehicleTypeName() {
        return vehicleTypeName;
    }

    public void setVehicleTypeName(String vehicleTypeName) {
        this.vehicleTypeName = vehicleTypeName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
