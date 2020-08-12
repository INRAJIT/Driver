package com.mottainai.driver.ui.pickuphistory;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mottainai.driver.data.PickupHistoryRepository;
import com.mottainai.driver.data.model.pickuphistory.dashboard.PickupHistoryDashboardRequest;
import com.mottainai.driver.data.model.pickuphistory.dashboard.PickupHistoryDashboardResponse;
import com.mottainai.driver.data.model.pickuphistory.history.PickupHistoryRequest;
import com.mottainai.driver.data.model.pickuphistory.history.PickupHistoryResponse;

public class PickupHistoryViewModel extends ViewModel {

    private MutableLiveData<PickupHistoryDashboardResponse> pickupHistoryDashboardResponse = new MutableLiveData<>();
    private MutableLiveData<PickupHistoryResponse> pickupHistoryResponse = new MutableLiveData<>();

    private PickupHistoryRepository dashBoardRepository = PickupHistoryRepository.getInstance();

    LiveData<PickupHistoryDashboardResponse> getPickupHistoryDashboardResponse() {
        return pickupHistoryDashboardResponse;
    }

    LiveData<PickupHistoryResponse> getPickupHistoryResponse() {
        return pickupHistoryResponse;
    }

    public void getPickupHistoryDashboard(String driverId, int month, int year) {
        dashBoardRepository.getPickupHistoryDashboard(new PickupHistoryDashboardRequest(driverId, month, year), pickupHistoryDashboardResponse);
    }

    public void getPickupHistory(String driverId, int filterBy, String dateType, String filterDate) {
        dashBoardRepository.getPickupHistory(new PickupHistoryRequest(driverId, filterBy, dateType, filterDate), pickupHistoryResponse);
    }
}
