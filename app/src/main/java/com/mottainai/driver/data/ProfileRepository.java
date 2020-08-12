package com.mottainai.driver.data;

import androidx.lifecycle.MutableLiveData;

import com.mottainai.driver.data.model.profile.DriverProfileResponse;
import com.mottainai.driver.data.model.profile.DrivingLicenceResponse;
import com.mottainai.driver.data.model.profile.UpdatePasswordRequest;
import com.mottainai.driver.data.model.profile.UpdatePasswordResponse;
import com.mottainai.driver.data.model.profile.VehicleDetailsResponse;
import com.mottainai.driver.networking.APIInterface;
import com.mottainai.driver.networking.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileRepository {
    private static volatile ProfileRepository instance;

    APIInterface apiInterface;

    // private constructor : singleton access
    private ProfileRepository() {
        this.apiInterface = RetrofitService.createService(APIInterface.class);
    }

    public static ProfileRepository getInstance() {
        if (instance == null) {
            instance = new ProfileRepository();
        }
        return instance;
    }

    public void getDriverProfile(final MutableLiveData<DriverProfileResponse> driverProfileResponse, String id) {
        apiInterface.getDriverProfile(id).
                enqueue(new Callback<DriverProfileResponse>() {
                    @Override
                    public void onResponse(Call<DriverProfileResponse> call, Response<DriverProfileResponse> response) {
                        if (response.isSuccessful()) {
                            driverProfileResponse.setValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<DriverProfileResponse> call, Throwable t) {
                    }
                });
    }

    public void getDrivingLicenceDetails(final MutableLiveData<DrivingLicenceResponse> drivingLicenceResponse, String id) {
        apiInterface.getDrivingLicenceDetails(id).
                enqueue(new Callback<DrivingLicenceResponse>() {
                    @Override
                    public void onResponse(Call<DrivingLicenceResponse> call, Response<DrivingLicenceResponse> response) {
                        if (response.isSuccessful()) {
                            drivingLicenceResponse.setValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<DrivingLicenceResponse> call, Throwable t) {
                    }
                });
    }

    public void getVehicleDetails(final MutableLiveData<VehicleDetailsResponse> vehicleDetailsResponse, String id) {
        apiInterface.getVehicleDetails(id).
                enqueue(new Callback<VehicleDetailsResponse>() {
                    @Override
                    public void onResponse(Call<VehicleDetailsResponse> call, Response<VehicleDetailsResponse> response) {
                        if (response.isSuccessful()) {
                            vehicleDetailsResponse.setValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<VehicleDetailsResponse> call, Throwable t) {
                    }
                });
    }

    public void updatePassword(final MutableLiveData<UpdatePasswordResponse> updatePasswordResponse, UpdatePasswordRequest updatePasswordRequest) {
        apiInterface.updatePassword(updatePasswordRequest).
                enqueue(new Callback<UpdatePasswordResponse>() {
                    @Override
                    public void onResponse(Call<UpdatePasswordResponse> call, Response<UpdatePasswordResponse> response) {
                        if (response.isSuccessful()) {
                            updatePasswordResponse.setValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<UpdatePasswordResponse> call, Throwable t) {
                    }
                });
    }

}
