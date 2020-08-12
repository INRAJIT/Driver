package com.mottainai.driver.data;

import androidx.lifecycle.MutableLiveData;

import com.mottainai.driver.data.model.status.DriverStatusRequest;
import com.mottainai.driver.data.model.status.DriverStatusResponse;
import com.mottainai.driver.networking.APIInterface;
import com.mottainai.driver.networking.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectStatusRepository {
    private static volatile SelectStatusRepository instance;

    APIInterface apiInterface;

    // private constructor : singleton access
    private SelectStatusRepository() {
        this.apiInterface = RetrofitService.createService(APIInterface.class);
    }

    public static SelectStatusRepository getInstance() {
        if (instance == null) {
            instance = new SelectStatusRepository();
        }
        return instance;
    }

    public void setDriverStatus(DriverStatusRequest driverStatusRequest, final MutableLiveData<DriverStatusResponse> driverStatusResponse){
        apiInterface.setDriverStatus(driverStatusRequest).enqueue(new Callback<DriverStatusResponse>() {
            @Override
            public void onResponse(Call<DriverStatusResponse> call, Response<DriverStatusResponse> response) {
                if(response.isSuccessful()) {
                    driverStatusResponse.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<DriverStatusResponse> call, Throwable t) {

            }
        });
    }
}
