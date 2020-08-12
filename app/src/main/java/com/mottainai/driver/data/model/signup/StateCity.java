package com.mottainai.driver.data.model.signup;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StateCity implements Parcelable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;

    protected StateCity(Parcel in) {
        id = in.readString();
        name = in.readString();
    }

    public static final Creator<StateCity> CREATOR = new Creator<StateCity>() {
        @Override
        public StateCity createFromParcel(Parcel in) {
            return new StateCity(in);
        }

        @Override
        public StateCity[] newArray(int size) {
            return new StateCity[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
    }
}
