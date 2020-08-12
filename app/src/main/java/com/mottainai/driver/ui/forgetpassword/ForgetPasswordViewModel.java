package com.mottainai.driver.ui.forgetpassword;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mottainai.driver.data.ForgetPasswordRepository;
import com.mottainai.driver.data.model.forgetpassword.ResetPasswordRequest;
import com.mottainai.driver.data.model.forgetpassword.ResetPasswordResponse;
import com.mottainai.driver.data.model.forgetpassword.VerifyEmailRequest;
import com.mottainai.driver.data.model.forgetpassword.VerifyEmailResponse;
import com.mottainai.driver.data.model.signup.OtpRequest;
import com.mottainai.driver.data.model.signup.OtpResponse;

public class ForgetPasswordViewModel extends ViewModel {

    private MutableLiveData<OtpResponse> otpEmailResponse = new MutableLiveData<>();
    private MutableLiveData<VerifyEmailResponse> verifyEmailResponse = new MutableLiveData<>();
    private MutableLiveData<ResetPasswordResponse> resetPasswordResponse = new MutableLiveData<>();

    private ForgetPasswordRepository forgetPasswordRepository = ForgetPasswordRepository.getInstance();

    LiveData<OtpResponse> getOtpEmailResponse() {
        return otpEmailResponse;
    }

    LiveData<VerifyEmailResponse> getVerifyEmailResponse() {
        return verifyEmailResponse;
    }

    LiveData<ResetPasswordResponse> getResetPasswordResponse() {
        return resetPasswordResponse;
    }

    public void verifyEmail(String email) {
        forgetPasswordRepository.verifyEmail(verifyEmailResponse, new VerifyEmailRequest(email));
    }

    public void verifyEmailOtp(String userId, String userType, String otpFor, String otp) {
        forgetPasswordRepository.verifyOtp(otpEmailResponse, new OtpRequest(userId, userType, otpFor, otp, "0"));
    }

    public void resetPassword(String driverId, String newPassword, String cnfPassword) {
        forgetPasswordRepository.resetPassword(resetPasswordResponse, new ResetPasswordRequest(driverId, newPassword, cnfPassword));
    }
}
