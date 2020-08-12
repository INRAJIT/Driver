package com.mottainai.driver.data;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.mottainai.driver.data.model.forgetpassword.ResetPasswordRequest;
import com.mottainai.driver.data.model.forgetpassword.ResetPasswordResponse;
import com.mottainai.driver.data.model.forgetpassword.VerifyEmailRequest;
import com.mottainai.driver.data.model.forgetpassword.VerifyEmailResponse;
import com.mottainai.driver.data.model.signup.OtpRequest;
import com.mottainai.driver.data.model.signup.OtpResponse;
import com.mottainai.driver.networking.APIInterface;
import com.mottainai.driver.networking.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgetPasswordRepository {
    private static volatile ForgetPasswordRepository instance;

    APIInterface apiInterface;

    // private constructor : singleton access
    private ForgetPasswordRepository() {
        this.apiInterface = RetrofitService.createService(APIInterface.class);
    }

    public static ForgetPasswordRepository getInstance() {
        if (instance == null) {
            instance = new ForgetPasswordRepository();
        }
        return instance;
    }

    public void verifyEmail(final MutableLiveData<VerifyEmailResponse> verifyEmailResponse, final VerifyEmailRequest verifyEmailRequest) {
        apiInterface.verifyEmail(verifyEmailRequest).enqueue(new Callback<VerifyEmailResponse>() {
            @Override
            public void onResponse(Call<VerifyEmailResponse> call, Response<VerifyEmailResponse> response) {
                if (response.isSuccessful()) {
                    verifyEmailResponse.setValue(response.body());
                }
            }
            @Override
            public void onFailure(Call<VerifyEmailResponse> call, Throwable t) {
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
                Log.d("gaurav", t.getMessage()+", "+t.getCause()+", "+t.getLocalizedMessage()+", "+t.getStackTrace());
            }
        });
    }

    public void resetPassword(final MutableLiveData<ResetPasswordResponse> resetPasswordResponse, final ResetPasswordRequest resetPasswordRequest) {
        apiInterface.resetPassword(resetPasswordRequest).enqueue(new Callback<ResetPasswordResponse>() {
            @Override
            public void onResponse(Call<ResetPasswordResponse> call, Response<ResetPasswordResponse> response) {
                if (response.isSuccessful()) {
                    resetPasswordResponse.setValue(response.body());
                }
            }
            @Override
            public void onFailure(Call<ResetPasswordResponse> call, Throwable t) {
            }
        });
    }
}
