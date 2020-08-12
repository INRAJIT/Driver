package com.mottainai.driver.services;

public interface DriverServiceBinderInterface {
    void setListener(DriverServiceListener driverServiceListener);
    void removeListener();
    void onDestroy();
}
