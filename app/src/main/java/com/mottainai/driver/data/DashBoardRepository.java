package com.mottainai.driver.data;

import androidx.lifecycle.MutableLiveData;

import com.mottainai.driver.data.model.home.AcceptPickupRequest;
import com.mottainai.driver.data.model.home.AcceptPickupResponse;
import com.mottainai.driver.data.model.home.DriverLogoutRequest;
import com.mottainai.driver.data.model.home.DriverLogoutResponse;
import com.mottainai.driver.data.model.home.DriverPickupResponse;
import com.mottainai.driver.data.model.home.PickupWasteStreamRequest;
import com.mottainai.driver.data.model.home.PickupWasteStreamResponse;
import com.mottainai.driver.data.model.home.RejectPickupRequest;
import com.mottainai.driver.data.model.home.RejectPickupResponse;
import com.mottainai.driver.data.model.home.ReportProblemReasonResponse;
import com.mottainai.driver.data.model.home.Total_Driver_PickUp_Request;
import com.mottainai.driver.data.model.home.Total_Driver_PickUp_Response;
import com.mottainai.driver.networking.APIInterface;
import com.mottainai.driver.networking.RetrofitService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Query;

public class DashBoardRepository {
    private static volatile DashBoardRepository instance;

    APIInterface apiInterface;

    // private constructor : singleton access
    private DashBoardRepository() {
        this.apiInterface = RetrofitService.createService(APIInterface.class);
    }

    public static DashBoardRepository getInstance() {
        if (instance == null) {
            instance = new DashBoardRepository();
        }
        return instance;
    }

    public void driverLogout(DriverLogoutRequest driverLogoutRequest, MutableLiveData<DriverLogoutResponse> driverLogoutResponse) {
        apiInterface.driverLogout(driverLogoutRequest).enqueue(new Callback<DriverLogoutResponse>() {
            @Override
            public void onResponse(Call<DriverLogoutResponse> call, Response<DriverLogoutResponse> response) {
                if (response.isSuccessful()) {
                    driverLogoutResponse.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<DriverLogoutResponse> call, Throwable t) {

            }
        });
    }

    public void getDriverPickup(String driverId, String pickupType, String pageno, String limit, MutableLiveData<DriverPickupResponse> driverPickupResponse) {
        apiInterface.getDriverPickup(driverId, pickupType, pageno, limit).enqueue(new Callback<DriverPickupResponse>() {
            @Override
            public void onResponse(Call<DriverPickupResponse> call, Response<DriverPickupResponse> response) {
                if (response.isSuccessful()) {
                    driverPickupResponse.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<DriverPickupResponse> call, Throwable t) {
                driverPickupResponse.setValue(new DriverPickupResponse(false, "API response is not correct !!"));
            }
        });
    }

    public void acceptPickup(AcceptPickupRequest acceptPickup, MutableLiveData<AcceptPickupResponse> acceptPickupResponse) {
        apiInterface.acceptPickup(acceptPickup).enqueue(new Callback<AcceptPickupResponse>() {
            @Override
            public void onResponse(Call<AcceptPickupResponse> call, Response<AcceptPickupResponse> response) {
                if (response.isSuccessful()) {
                    acceptPickupResponse.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<AcceptPickupResponse> call, Throwable t) {

            }
        });
    }

    public void declinePickup(RejectPickupRequest rejectPickup, MutableLiveData<RejectPickupResponse> rejectPickupResponse) {
        apiInterface.declinePickup(rejectPickup).enqueue(new Callback<RejectPickupResponse>() {
            @Override
            public void onResponse(Call<RejectPickupResponse> call, Response<RejectPickupResponse> response) {
                if(response.isSuccessful()) {
                    rejectPickupResponse.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<RejectPickupResponse> call, Throwable t) {

            }
        });
    }

    public void rejectPickup(RejectPickupRequest rejectPickup, MutableLiveData<RejectPickupResponse> rejectPickupResponse) {
        apiInterface.rejectPickup(rejectPickup).enqueue(new Callback<RejectPickupResponse>() {
            @Override
            public void onResponse(Call<RejectPickupResponse> call, Response<RejectPickupResponse> response) {
                if(response.isSuccessful()) {
                    rejectPickupResponse.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<RejectPickupResponse> call, Throwable t) {

            }
        });
    }

    public void getReportProblemReasons(String reasonType, MutableLiveData<ReportProblemReasonResponse> reportProblemReasonResponse) {
        apiInterface.getReportProblemReasons(reasonType).enqueue(new Callback<ReportProblemReasonResponse>() {
            @Override
            public void onResponse(Call<ReportProblemReasonResponse> call, Response<ReportProblemReasonResponse> response) {
                if(response.isSuccessful()) {
                    reportProblemReasonResponse.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<ReportProblemReasonResponse> call, Throwable t) {

            }
        });
    }

    public void getPickupWasteStreams(PickupWasteStreamRequest pickupWasteStreamRequest, MutableLiveData<PickupWasteStreamResponse> pickupWasteStreamResponse) {
        apiInterface.getPickupWasteStreams(pickupWasteStreamRequest).enqueue(new Callback<PickupWasteStreamResponse>() {
            @Override
            public void onResponse(Call<PickupWasteStreamResponse> call, Response<PickupWasteStreamResponse> response) {
                if(response.isSuccessful()) {
                    pickupWasteStreamResponse.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<PickupWasteStreamResponse> call, Throwable t) {

            }
        });
    }

 ///Addtion by rajit for new api which response name is "Total Driver Pickup Response" ////

    public void postTotalPickup(Total_Driver_PickUp_Request total_driver_pickUp_request, MutableLiveData<Total_Driver_PickUp_Response> total_driver_pickUp_response) {
        apiInterface.postTotalPickup(total_driver_pickUp_request).enqueue(new Callback<Total_Driver_PickUp_Response>() {
            @Override
            public void onResponse(Call<Total_Driver_PickUp_Response> call, Response<Total_Driver_PickUp_Response> response) {
                if(response.isSuccessful()) {
                    total_driver_pickUp_response.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<Total_Driver_PickUp_Response> call, Throwable t) {

            }
        });
    }
}

