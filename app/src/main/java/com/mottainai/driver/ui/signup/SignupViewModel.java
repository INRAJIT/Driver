package com.mottainai.driver.ui.signup;

import android.util.Patterns;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mottainai.driver.R;
import com.mottainai.driver.data.SignupRepository;
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

public class SignupViewModel extends ViewModel {
    private MutableLiveData<SignupFormState> signupFormState = new MutableLiveData<>();
    private MutableLiveData<SignupFormState> signupFormContactState = new MutableLiveData<>();
    private MutableLiveData<SignupResponse> signupResponse = new MutableLiveData<>();
    private MutableLiveData<OtpResponse> otpPhoneResponse = new MutableLiveData<>();
    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<SetLoginDetailsResponse> setLoginDetailsResponse = new MutableLiveData<>();
    private MutableLiveData<OtpResponse> otpEmailResponse = new MutableLiveData<>();
    private MutableLiveData<LicenceFormState> licenceFormState = new MutableLiveData<>();
    private MutableLiveData<SetLicenceDetailsResponse> licenceDetailsResponse = new MutableLiveData<>();
    private MutableLiveData<VehicleTypeResponse> vehicleTypeResponse = new MutableLiveData<>();
    private MutableLiveData<VehicleFormState> vehicleFormState = new MutableLiveData<>();
    private MutableLiveData<SetVehicleDetailsResponse> vehicleDetailsResponse = new MutableLiveData<>();
    private MutableLiveData<CountryResponse> countryResponse = new MutableLiveData<>();
    private MutableLiveData<StateResponse> stateResponse = new MutableLiveData<>();
    private MutableLiveData<CityResponse> cityResponse = new MutableLiveData<>();

    private SignupRepository signupRepository = SignupRepository.getInstance();

    LiveData<CountryResponse> getCountryResponse() {
        return countryResponse;
    }

    LiveData<StateResponse> getStateResponse() {
        return stateResponse;
    }

    LiveData<CityResponse> getCityResponse() {
        return cityResponse;
    }

    LiveData<SignupFormState> getSignupFormState() {
        return signupFormState;
    }

    LiveData<SignupFormState> getSignupFormContactState() {
        return signupFormContactState;
    }

    LiveData<SignupResponse> getSignupResponse() {
        return signupResponse;
    }

    LiveData<OtpResponse> getOtpPhoneResponse() {
        return otpPhoneResponse;
    }

    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    LiveData<SetLoginDetailsResponse> getLoginDetailsResponse() {
        return setLoginDetailsResponse;
    }

    LiveData<OtpResponse> getOtpEmailResponse() {
        return otpEmailResponse;
    }

    LiveData<LicenceFormState> getLicenceDetailsFormState() {
        return licenceFormState;
    }

    LiveData<SetLicenceDetailsResponse> getLicenceDetailsResponse() {
        return licenceDetailsResponse;
    }

    LiveData<VehicleTypeResponse> getVehicleTypeResponse() {
        return vehicleTypeResponse;
    }

    LiveData<VehicleFormState> getVehicleDetailsFormState() {
        return vehicleFormState;
    }

    LiveData<SetVehicleDetailsResponse> getVehicleDetailsResponse() {
        return vehicleDetailsResponse;
    }

    public void getCountryList() {
        signupRepository.getCountryList(countryResponse);
    }

    public void getStateList(String countryId) {
        signupRepository.getStateList(stateResponse, countryId);
    }

    public void getCityList(String stateId) {
        signupRepository.getCityList(cityResponse, stateId);
    }

    public void signupIndividualDetails(String haulerType, String firstName, String middleName, String lastName,
                                        String phone, String address, String country, String state, String city, String pincode) {
        signupRepository.signup(signupResponse, new SignupRequest(haulerType, firstName, middleName,
                lastName, phone, address, country, state, city, pincode));

    }

    public void signupCompanyDetails(String haulerType, String companyName, String registrationNo,
                                     String address, String country, String state, String city, String pincode,
                                     String firstName, String middleName, String lastName, String phone) {
        signupRepository.signup(signupResponse, new SignupRequest(haulerType, companyName, registrationNo,
                address, country, state, city, pincode, firstName, middleName, lastName, phone));
    }

    public void verifyPhoneOtp(String userId, String userType, String otpFor, String otp) {
        signupRepository.verifyOtp(otpPhoneResponse, new OtpRequest(userId, userType, otpFor, otp, "0"));
    }

    public void setLoginDetails(String accountId, String email, String password) {
        signupRepository.setLoginDetails(setLoginDetailsResponse, new SetLoginDetailsRequest(accountId, email, password));
    }

    public void verifyEmailOtp(String userId, String userType, String otpFor, String otp) {
        signupRepository.verifyOtp(otpEmailResponse, new OtpRequest(userId, userType, otpFor, otp, "0"));
    }

    public void setLicenseDetails(String accountId, String firstName, String middleName,
                                  String lastName, String drivingLicenceNo, String licenceExpiryDate) {
        signupRepository.setLicenceDetails(licenceDetailsResponse,
                new SetLicenceDetailsRequest(accountId, firstName, middleName, lastName, drivingLicenceNo, licenceExpiryDate));
    }

    public void getVehicleTypes() {
        signupRepository.getVehicleTypes(vehicleTypeResponse);
    }

    public void setVehicleDetails(String accountId, String vehicleType, String vehicleRegistrationNumber,
                                  String registrationExpiryDate, String registrationCertificate, String insuranceCertificate) {
        signupRepository.setVehicleDetails(vehicleDetailsResponse,
                new SetVehicleDetailsRequest(accountId, vehicleType, vehicleRegistrationNumber,
                        registrationExpiryDate, registrationCertificate, insuranceCertificate));
    }

    public void signupIndividualDataChanged(String firstName, String middleName, String lastName,
                                            String phone, String addressLineOne, String addressLineTwo,
                                            String pincode, String country, String state, String city) {
        if (firstName.isEmpty()) {
            signupFormState.setValue(new SignupFormState(R.string.invalid_first_name));
        } else if (middleName.isEmpty()) {
            signupFormState.setValue(new SignupFormState(R.string.invalid_middle_name));
        } else if (lastName.isEmpty()) {
            signupFormState.setValue(new SignupFormState(R.string.invalid_last_name));
        } else if (phone.isEmpty()) {
            signupFormState.setValue(new SignupFormState(R.string.invalid_phone_number));
        } else if (addressLineOne.isEmpty()) {
            signupFormState.setValue(new SignupFormState(R.string.invalid_company_address_lineone));
        }  else if (pincode.isEmpty()) {
            signupFormState.setValue(new SignupFormState(R.string.invalid_company_pincode));
        } else if (country == null || country.isEmpty()) {
            signupFormState.setValue(new SignupFormState(R.string.select_country));
        } else if (state == null || state.isEmpty()) {
            signupFormState.setValue(new SignupFormState(R.string.select_state));
        } else if (city == null || city.isEmpty()) {
            signupFormState.setValue(new SignupFormState(R.string.select_city));
        } else {
            signupFormState.setValue(new SignupFormState(true));
        }
    }

    public void signupCompanyDataChanged(String companyName, String registrationNo,
                                         String addressLineOne, String addressLineTwo, String pincode,
                                         String country, String state, String city) {
        if (companyName.isEmpty()) {
            signupFormState.setValue(new SignupFormState(R.string.invalid_company_name));
        } else if (registrationNo.isEmpty()) {
            signupFormState.setValue(new SignupFormState(R.string.invalid_company_registration));
        } else if (addressLineOne.isEmpty()) {
            signupFormState.setValue(new SignupFormState(R.string.invalid_company_address_lineone));
        } else if (addressLineTwo.isEmpty()) {
            signupFormState.setValue(new SignupFormState(R.string.invalid_company_address_linetwo));
        } else if (pincode.isEmpty()) {
            signupFormState.setValue(new SignupFormState(R.string.invalid_company_pincode));
        } else if (country == null || country.isEmpty()) {
            signupFormState.setValue(new SignupFormState(R.string.select_country));
        } else if (state == null || state.isEmpty()) {
            signupFormState.setValue(new SignupFormState(R.string.select_state));
        } else if (city == null || city.isEmpty()) {
            signupFormState.setValue(new SignupFormState(R.string.select_city));
        } else {
            signupFormState.setValue(new SignupFormState(true));
        }
    }

    public void signupContactDataChanged(String firstName, String middleName, String lastName,
                                         String phone) {
        if (firstName.isEmpty()) {
            signupFormContactState.setValue(new SignupFormState(R.string.invalid_first_name));
        } else if (middleName.isEmpty()) {
            signupFormContactState.setValue(new SignupFormState(R.string.invalid_middle_name));
        } else if (lastName.isEmpty()) {
            signupFormContactState.setValue(new SignupFormState(R.string.invalid_last_name));
        } else if (phone.isEmpty()) {
            signupFormContactState.setValue(new SignupFormState(R.string.invalid_phone_number));
        } else {
            signupFormContactState.setValue(new SignupFormState(true));
        }
    }

    public void loginDataChanged(String email, String password) {
        if (!isEmailValid(email)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_email, null));
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
        } else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }

    public void licenceDataChanged(String firstName, String middleName, String lastName,
                                   String drivingLicenceNo, String licenceExpiryDate) {
        if (firstName.isEmpty()) {
            licenceFormState.setValue(new LicenceFormState(R.string.invalid_first_name));
        } else if (middleName.isEmpty()) {
            licenceFormState.setValue(new LicenceFormState(R.string.invalid_middle_name));
        } else if (lastName.isEmpty()) {
            licenceFormState.setValue(new LicenceFormState(R.string.invalid_last_name));
        } else if (drivingLicenceNo.isEmpty()) {
            licenceFormState.setValue(new LicenceFormState(R.string.invalid_licence_no));
        } else if (licenceExpiryDate.isEmpty()) {
            licenceFormState.setValue(new LicenceFormState(R.string.invalid_expiry_date));
        } else {
            licenceFormState.setValue(new LicenceFormState(true));
        }
    }

    public void vehicleDataChanged(String vehicleType, String vehicleRegistrationNumber,
                                   String registrationExpiryDate, String registrationCertificate, String insuranceCertificate) {
        if (vehicleType == null || vehicleType.isEmpty()) {
            vehicleFormState.setValue(new VehicleFormState(R.string.invalid_vehicle_type));
        } else if (vehicleRegistrationNumber.isEmpty()) {
            vehicleFormState.setValue(new VehicleFormState(R.string.invalid_registration_no));
        } else if (registrationExpiryDate.isEmpty()) {
            vehicleFormState.setValue(new VehicleFormState(R.string.invalid_expiry_date));
        } else if (registrationCertificate == null || registrationCertificate.isEmpty()) {
            vehicleFormState.setValue(new VehicleFormState(R.string.invalid_registration_certificate));
        } else if (insuranceCertificate == null || insuranceCertificate.isEmpty()) {
            vehicleFormState.setValue(new VehicleFormState(R.string.invalid_insurance_certificate));
        } else {
            vehicleFormState.setValue(new VehicleFormState(true));
        }
    }

    // A placeholder email validation check
    private boolean isEmailValid(String email) {
        if (email == null) {
            return false;
        }
        if (email.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }
        return false;
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }

}
