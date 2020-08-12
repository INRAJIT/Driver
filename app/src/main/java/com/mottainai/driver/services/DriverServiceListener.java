package com.mottainai.driver.services;

public interface DriverServiceListener {
    void onDriverLocationChanged(double latitude, double longitude);
}
