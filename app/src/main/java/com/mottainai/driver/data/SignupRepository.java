package com.mottainai.driver.data;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.mottainai.driver.data.model.signup.CityResponse;
import com.mottainai.driver.data.model.signup.CountryResponse;
import com.mottainai.driver.data.model.signup.OtpRequest;
import com.mottainai.driver.data.model.signup.OtpResponse;
import com.mottainai.driver.data.model.signup.SetLicenceDetailsRequest;
import com.mottainai.driver.data.model.signup.SetLicenceDetailsResponse;
import com.mottainai.driver.data.model.signup.SetLoginDetailsRequest;
import com.mottainai.driver.data.model.signup.SetLoginDetailsResponse;
import com.mottainai.driver.data.model.signup.SetVehicleDetailsRequest;
import com.mottainai.driver.data.model.signup.SetVehicleDetailsResponse;
import com.mottainai.driver.data.model.signup.SignupRequest;
import com.mottainai.driver.data.model.signup.SignupResponse;
import com.mottainai.driver.data.model.signup.StateResponse;
import com.mottainai.driver.data.model.signup.VehicleTypeResponse;
import com.mottainai.driver.networking.APIInterface;
import com.mottainai.driver.networking.RetrofitService;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupRepository {
    private static volatile SignupRepository instance;

    APIInterface apiInterface;

    // private constructor : singleton access
    private SignupRepository() {
        this.apiInterface = RetrofitService.createService(APIInterface.class);
    }

    public static SignupRepository getInstance() {
        if (instance == null) {
            instance = new SignupRepository();
        }
        return instance;
    }

    public void getCountryList(final MutableLiveData<CountryResponse> countryResponse) {
        apiInterface.getCountryList().
                enqueue(new Callback<CountryResponse>() {
                    @Override
                    public void onResponse(Call<CountryResponse> call, Response<CountryResponse> response) {
                        if (response.isSuccessful()) {
                            countryResponse.setValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<CountryResponse> call, Throwable t) {
                    }
                });
    }

    public void getStateList(final MutableLiveData<StateResponse> stateResponse, String countryId) {
        apiInterface.getStateList(countryId).
                enqueue(new Callback<StateResponse>() {
                    @Override
                    public void onResponse(Call<StateResponse> call, Response<StateResponse> response) {
                        if (response.isSuccessful()) {
                            stateResponse.setValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<StateResponse> call, Throwable t) {
                    }
                });
    }

    public void getCityList(final MutableLiveData<CityResponse> cityResponse, String stateId) {
        apiInterface.getCityList(stateId).
                enqueue(new Callback<CityResponse>() {
                    @Override
                    public void onResponse(Call<CityResponse> call, Response<CityResponse> response) {
                        if (response.isSuccessful()) {
                            cityResponse.setValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<CityResponse> call, Throwable t) {
                    }
                });
    }

    public void signup(final MutableLiveData<SignupResponse> signupResponse, SignupRequest signupRequest) {
        apiInterface.signupEnterDetails(signupRequest).
                enqueue(new Callback<SignupResponse>() {
                    @Override
                    public void onResponse(Call<SignupResponse> call, Response<SignupResponse> response) {
                        if (response.isSuccessful()) {
                            signupResponse.setValue(response.body());
                        } else {
                            signupResponse.setValue(new SignupResponse(false, "Signup Failed"));
                        }
                    }

                    @Override
                    public void onFailure(Call<SignupResponse> call, Throwable t) {
                        signupResponse.setValue(new SignupResponse(false, t.getMessage()));
                    }
                });
    }

    public void verifyOtp(final MutableLiveData<OtpResponse> otpResponse, final OtpRequest otpRequest) {
        apiInterface.verifyOtp(otpRequest).enqueue(new Callback<OtpResponse>() {
            @Override
            public void onResponse(Call<OtpResponse> call, Response<OtpResponse> response) {
                if (response.isSuccessful()) {
                    otpResponse.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<OtpResponse> call, Throwable t) {
                otpResponse.setValue(new OtpResponse());
            }
        });
    }

    public void setLoginDetails(final MutableLiveData<SetLoginDetailsResponse> loginResponse, final SetLoginDetailsRequest loginRequest) {
        apiInterface.setLoginDetails(loginRequest).enqueue(new Callback<SetLoginDetailsResponse>() {
            @Override
            public void onResponse(Call<SetLoginDetailsResponse> call, Response<SetLoginDetailsResponse> response) {
                if (response.isSuccessful()) {
                    loginResponse.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<SetLoginDetailsResponse> call, Throwable t) {
                loginResponse.setValue(new SetLoginDetailsResponse());
            }
        });
    }

    public void setLicenceDetails(final MutableLiveData<SetLicenceDetailsResponse> licenceDetailsResponse,
                                  final SetLicenceDetailsRequest licenceDetailsRequest) {
        apiInterface.setLicenseDetails(licenceDetailsRequest).enqueue(new Callback<SetLicenceDetailsResponse>() {
            @Override
            public void onResponse(Call<SetLicenceDetailsResponse> call, Response<SetLicenceDetailsResponse> response) {
                if (response.isSuccessful()) {
                    licenceDetailsResponse.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<SetLicenceDetailsResponse> call, Throwable t) {
                licenceDetailsResponse.setValue(new SetLicenceDetailsResponse(false, t.getMessage()));
            }
        });
    }

    public void getVehicleTypes(final MutableLiveData<VehicleTypeResponse> vehicleTypeResponse) {
        apiInterface.getVehicleTypes().enqueue(new Callback<VehicleTypeResponse>() {
            @Override
            public void onResponse(Call<VehicleTypeResponse> call, Response<VehicleTypeResponse> response) {
                if (response.isSuccessful()) {
                    vehicleTypeResponse.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<VehicleTypeResponse> call, Throwable t) {
            }
        });
    }

    public void setVehicleDetails(final MutableLiveData<SetVehicleDetailsResponse> vehicleDetailsResponse,
                                  final SetVehicleDetailsRequest vehicleDetailsRequest) {

        // add part within the multipart request
        RequestBody accountId =
                RequestBody.create(MediaType.parse("multipart/form-data"), vehicleDetailsRequest.accountId);
        RequestBody vehicleType =
                RequestBody.create(MediaType.parse("multipart/form-data"), vehicleDetailsRequest.vehicleType);
        RequestBody vehicleRegistrationNumber =
                RequestBody.create(MediaType.parse("multipart/form-data"), vehicleDetailsRequest.vehicleRegistrationNumber);
        RequestBody registrationExpiryDate =
                RequestBody.create(MediaType.parse("multipart/form-data"), vehicleDetailsRequest.registrationExpiryDate);

        // Multipart Registration Certificate
        File registrationCertificateFile = new File(vehicleDetailsRequest.registrationCertificate);
        RequestBody requestFileOne =
                RequestBody.create(MediaType.parse("multipart/form-data"), registrationCertificateFile);

        MultipartBody.Part registrationCertificateBody =
                MultipartBody.Part.createFormData("registration_certificate", registrationCertificateFile.getName(), requestFileOne);

        // Multipart Insurance Certificate
        File insuranceCertificateFile = new File(vehicleDetailsRequest.insuranceCertificate);
        RequestBody requestFileTwo =
                RequestBody.create(MediaType.parse("multipart/form-data"), insuranceCertificateFile);

        MultipartBody.Part insuranceCertificateBody =
                MultipartBody.Part.createFormData("insurance_certificate", insuranceCertificateFile.getName(), requestFileTwo);

        apiInterface.setVehicleDetails(accountId, vehicleType, vehicleRegistrationNumber, registrationExpiryDate,
                registrationCertificateBody, insuranceCertificateBody).enqueue(new Callback<SetVehicleDetailsResponse>() {
            @Override
            public void onResponse(Call<SetVehicleDetailsResponse> call, Response<SetVehicleDetailsResponse> response) {
                if (response.isSuccessful()) {
                    vehicleDetailsResponse.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<SetVehicleDetailsResponse> call, Throwable t) {
                vehicleDetailsResponse.setValue(new SetVehicleDetailsResponse(false, t.getMessage()));
            }
        });
    }
}
