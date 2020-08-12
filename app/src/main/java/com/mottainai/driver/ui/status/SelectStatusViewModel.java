package com.mottainai.driver.ui.status;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mottainai.driver.data.SelectStatusRepository;
import com.mottainai.driver.data.model.status.DriverStatusRequest;
import com.mottainai.driver.data.model.status.DriverStatusResponse;

public class SelectStatusViewModel extends ViewModel {

    private MutableLiveData<DriverStatusResponse> driverStatusResponse = new MutableLiveData<>();

    private SelectStatusRepository selectStatusRepository = SelectStatusRepository.getInstance();

    LiveData<DriverStatusResponse> getDriverStatusResponse() {
        return driverStatusResponse;
    }

    public void setDriverStatus(String driverId, int status) {
        selectStatusRepository.setDriverStatus(new DriverStatusRequest(driverId, status), driverStatusResponse);
    }
}
