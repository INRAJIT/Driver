
package com.mottainai.driver.data.model.caselog;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CaseCategory implements Parcelable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("category")
    @Expose
    private String category;

    protected CaseCategory(Parcel in) {
        id = in.readString();
        category = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(category);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CaseCategory> CREATOR = new Creator<CaseCategory>() {
        @Override
        public CaseCategory createFromParcel(Parcel in) {
            return new CaseCategory(in);
        }

        @Override
        public CaseCategory[] newArray(int size) {
            return new CaseCategory[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

}
