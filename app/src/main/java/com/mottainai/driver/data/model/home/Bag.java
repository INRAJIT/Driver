package com.mottainai.driver.data.model.home;

import com.google.gson.annotations.SerializedName;

public class Bag {

    @SerializedName("bags_title")
    public String bagsTitle;
    @SerializedName("bags_weight_kg")
    public int bagsWeightKg;
    @SerializedName("bags_weight_gm")
    public int bagsWeightGm;

    @SerializedName("bar_code")
    public String barCode;

    public String getBagsTitle() {
        return bagsTitle;
    }

    public void setBagsTitle(String bagsTitle) {
        this.bagsTitle = bagsTitle;
    }

    public int getBagsWeightKg() {
        return bagsWeightKg;
    }

    public void setBagsWeightKg(int bagsWeightKg) {
        this.bagsWeightKg = bagsWeightKg;
    }

    public int getBagsWeightGm() {
        return bagsWeightGm;
    }

    public void setBagsWeightGm(int bagsWeightGm) {
        this.bagsWeightGm = bagsWeightGm;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }
}
