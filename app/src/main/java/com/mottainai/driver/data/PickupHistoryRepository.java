package com.mottainai.driver.data;

import androidx.lifecycle.MutableLiveData;

import com.mottainai.driver.data.model.pickuphistory.dashboard.PickupHistoryDashboardRequest;
import com.mottainai.driver.data.model.pickuphistory.dashboard.PickupHistoryDashboardResponse;
import com.mottainai.driver.data.model.pickuphistory.history.PickupHistoryRequest;
import com.mottainai.driver.data.model.pickuphistory.history.PickupHistoryResponse;
import com.mottainai.driver.networking.APIInterface;
import com.mottainai.driver.networking.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PickupHistoryRepository {
    private static volatile PickupHistoryRepository instance;

    APIInterface apiInterface;

    // private constructor : singleton access
    private PickupHistoryRepository() {
        this.apiInterface = RetrofitService.createService(APIInterface.class);
    }

    public static PickupHistoryRepository getInstance() {
        if (instance == null) {
            instance = new PickupHistoryRepository();
        }
        return instance;
    }

    public void getPickupHistoryDashboard(PickupHistoryDashboardRequest pickupHistoryDashboardRequest,
                                          final MutableLiveData<PickupHistoryDashboardResponse> pickupHistoryDashboardResponse) {
        apiInterface.getPickupDashboardHistory(pickupHistoryDashboardRequest).enqueue(new Callback<PickupHistoryDashboardResponse>() {
            @Override
            public void onResponse(Call<PickupHistoryDashboardResponse> call, Response<PickupHistoryDashboardResponse> response) {
                if (response.isSuccessful()) {
                    pickupHistoryDashboardResponse.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<PickupHistoryDashboardResponse> call, Throwable t) {

            }
        });
    }

    public void getPickupHistory(PickupHistoryRequest pickupHistoryRequest,
                                 final MutableLiveData<PickupHistoryResponse> pickupHistoryResponse) {
        apiInterface.getPickupHistory(pickupHistoryRequest).enqueue(new Callback<PickupHistoryResponse>() {
            @Override
            public void onResponse(Call<PickupHistoryResponse> call, Response<PickupHistoryResponse> response) {
                if(response.isSuccessful()) {
                    pickupHistoryResponse.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<PickupHistoryResponse> call, Throwable t) {

            }
        });
    }
}
