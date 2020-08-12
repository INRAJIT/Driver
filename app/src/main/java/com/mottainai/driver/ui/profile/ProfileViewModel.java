package com.mottainai.driver.ui.profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mottainai.driver.data.ProfileRepository;
import com.mottainai.driver.data.model.profile.DriverProfileResponse;
import com.mottainai.driver.data.model.profile.DrivingLicenceResponse;
import com.mottainai.driver.data.model.profile.UpdatePasswordRequest;
import com.mottainai.driver.data.model.profile.UpdatePasswordResponse;
import com.mottainai.driver.data.model.profile.VehicleDetailsResponse;

public class ProfileViewModel extends ViewModel {

    private MutableLiveData<DriverProfileResponse> driverProfileResponse = new MutableLiveData<>();
    private MutableLiveData<DrivingLicenceResponse> drivingLicenceResponse = new MutableLiveData<>();
    private MutableLiveData<VehicleDetailsResponse> vehicleDetailsResponse = new MutableLiveData<>();
    private MutableLiveData<UpdatePasswordResponse> updatePasswordResponse = new MutableLiveData<>();

    private ProfileRepository profileRepository = ProfileRepository.getInstance();

    LiveData<DriverProfileResponse> getDriverProfileResponse() {
        return driverProfileResponse;
    }

    LiveData<DrivingLicenceResponse> getDrivingLicenceResponse() {
        return drivingLicenceResponse;
    }

    LiveData<VehicleDetailsResponse> getVehicleDetailsResponse() {
        return vehicleDetailsResponse;
    }

    LiveData<UpdatePasswordResponse> getUpdatePasswordResponse() {
        return updatePasswordResponse;
    }

    public void getDriverProfile(String id) {
        profileRepository.getDriverProfile(driverProfileResponse, id);
    }

    public void getDrivingLicenceDetails(String id) {
        profileRepository.getDrivingLicenceDetails(drivingLicenceResponse, id);
    }

    public void getVehicleDetails(String id) {
        profileRepository.getVehicleDetails(vehicleDetailsResponse, id);
    }

    public void updatePassword(String id, String oldPassword, String newPassword, String cnfPassword) {
        profileRepository.updatePassword(updatePasswordResponse, new UpdatePasswordRequest(id, oldPassword, newPassword, cnfPassword));
    }

}
