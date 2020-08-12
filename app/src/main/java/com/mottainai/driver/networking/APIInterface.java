package com.mottainai.driver.networking;

import com.mottainai.driver.data.model.caselog.CaseCategoryListResponse;
import com.mottainai.driver.data.model.caselog.CaseLogListResponse;
import com.mottainai.driver.data.model.caselog.CreateCaseLogRequest;
import com.mottainai.driver.data.model.caselog.CreateCaseLogResponse;
import com.mottainai.driver.data.model.forgetpassword.ResetPasswordRequest;
import com.mottainai.driver.data.model.forgetpassword.ResetPasswordResponse;
import com.mottainai.driver.data.model.forgetpassword.VerifyEmailRequest;
import com.mottainai.driver.data.model.forgetpassword.VerifyEmailResponse;
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
import com.mottainai.driver.data.model.login.LoginRequest;
import com.mottainai.driver.data.model.login.LoginResponse;
import com.mottainai.driver.data.model.pickuphistory.dashboard.PickupHistoryDashboardRequest;
import com.mottainai.driver.data.model.pickuphistory.dashboard.PickupHistoryDashboardResponse;
import com.mottainai.driver.data.model.pickuphistory.history.PickupHistoryRequest;
import com.mottainai.driver.data.model.pickuphistory.history.PickupHistoryResponse;
import com.mottainai.driver.data.model.profile.DriverProfileResponse;
import com.mottainai.driver.data.model.profile.DrivingLicenceResponse;
import com.mottainai.driver.data.model.profile.UpdatePasswordRequest;
import com.mottainai.driver.data.model.profile.UpdatePasswordResponse;
import com.mottainai.driver.data.model.profile.VehicleDetailsResponse;
import com.mottainai.driver.data.model.service.DriverLocation;
import com.mottainai.driver.data.model.signup.CityResponse;
import com.mottainai.driver.data.model.signup.CountryResponse;
import com.mottainai.driver.data.model.signup.OtpRequest;
import com.mottainai.driver.data.model.signup.OtpResponse;
import com.mottainai.driver.data.model.signup.SetLicenceDetailsRequest;
import com.mottainai.driver.data.model.signup.SetLicenceDetailsResponse;
import com.mottainai.driver.data.model.signup.SetLoginDetailsRequest;
import com.mottainai.driver.data.model.signup.SetLoginDetailsResponse;
import com.mottainai.driver.data.model.signup.SetVehicleDetailsResponse;
import com.mottainai.driver.data.model.signup.SignupRequest;
import com.mottainai.driver.data.model.signup.SignupResponse;
import com.mottainai.driver.data.model.signup.StateResponse;
import com.mottainai.driver.data.model.signup.VehicleTypeResponse;
import com.mottainai.driver.data.model.status.DriverStatusRequest;
import com.mottainai.driver.data.model.status.DriverStatusResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface APIInterface {

    @POST("Driver/login")
    Call<LoginResponse> makeLoginRequest(@Body LoginRequest loginRequest);

    @GET("getCountryCodeList")
    Call<CountryResponse> getCountryList();

    @GET("location")
    Call<StateResponse> getStateList(@Query("countryid") String countryId);

    @GET("location")
    Call<CityResponse> getCityList(@Query("stateid") String stateId);

    @POST("Hauler/sign-up")
    Call<SignupResponse> signupEnterDetails(@Body SignupRequest signupRequest);

    @POST("verifyOtp")
    Call<OtpResponse> verifyOtp(@Body OtpRequest otpRequest);

    @POST("Hauler/set-login-detail")
    Call<SetLoginDetailsResponse> setLoginDetails(@Body SetLoginDetailsRequest loginRequest);

    @POST("Hauler/set-licence-detail")
    Call<SetLicenceDetailsResponse> setLicenseDetails(@Body SetLicenceDetailsRequest licenceDetailsRequest);

    @GET("getVehicleType")
    Call<VehicleTypeResponse> getVehicleTypes();

    @Multipart
    @POST("Hauler/set-vehicle-detail")
    Call<SetVehicleDetailsResponse> setVehicleDetails(@Part("account_id") RequestBody accountId,
                                                      @Part("vehicle_type") RequestBody vehicleType,
                                                      @Part("vehicle_registration_number") RequestBody vehicleRegistrationNumber,
                                                      @Part("registration_expiry_date") RequestBody registrationExpiryDate,
                                                      @Part MultipartBody.Part registrationCertificate,
                                                      @Part MultipartBody.Part insuranceCertificate);

    @POST("Driver/verifyEmail")
    Call<VerifyEmailResponse> verifyEmail(@Body VerifyEmailRequest verifyEmailRequest);

    @POST("Driver/resetPassword")
    Call<ResetPasswordResponse> resetPassword(@Body ResetPasswordRequest resetPasswordRequest);

    @GET("Driver/getprofile")
    Call<DriverProfileResponse> getDriverProfile(@Query("id") String id);

    @GET("Driver/drivinglicence")
    Call<DrivingLicenceResponse> getDrivingLicenceDetails(@Query("id") String id);

    @GET("Driver/vehicle_details")
    Call<VehicleDetailsResponse> getVehicleDetails(@Query("id") String id);

    @POST("Driver/updatepassword")
    Call<UpdatePasswordResponse> updatePassword(@Body UpdatePasswordRequest updatePasswordRequest);

    @GET("Driver/get-driver-current-location")
    Call<DriverLocation> getDriverCurrentLocation(@Query("driver_id") String driverId);

    @POST("Driver/set-driver-working-status")
    Call<DriverStatusResponse> setDriverStatus(@Body DriverStatusRequest driverStatusRequest);

    @POST("Driver/logout")
    Call<DriverLogoutResponse> driverLogout(@Body DriverLogoutRequest driverLogoutRequest);

    @GET("Driver/getDriverPickups")
    Call<DriverPickupResponse> getDriverPickup(@Query("driverId") String driverId, @Query("pickup_type") String pickupType,
                                               @Query("pageno") String pageno, @Query("limit") String limit);

    @POST("Pickup/pickup-Acceptance")
    Call<AcceptPickupResponse> acceptPickup(@Body AcceptPickupRequest acceptPickup);

    @POST("Pickup/decline-pickup")
    Call<RejectPickupResponse> declinePickup(@Body RejectPickupRequest rejectPickup);

    @POST("Pickup/Reportproblem")
    Call<RejectPickupResponse> rejectPickup(@Body RejectPickupRequest rejectPickup);

    @POST("Pickup/get-pickup-waste-stream-lists")   ///*****5g*****///
    Call<PickupWasteStreamResponse> getPickupWasteStreams(@Body PickupWasteStreamRequest pickupWasteStreamRequest);

    @POST("Pickup/driver-pickup-submit")   ///*****New extra api for 5g screen*****///
    Call<Total_Driver_PickUp_Response> postTotalPickup(@Body Total_Driver_PickUp_Request total_driver_pickUp_request);

    @GET("Driver/getProblemReoprtReason")
    Call<ReportProblemReasonResponse> getReportProblemReasons(@Query("reason_type") String reasonType);

    @POST("Pickup/pickuphistory")
    Call<PickupHistoryDashboardResponse> getPickupDashboardHistory(@Body PickupHistoryDashboardRequest pickupHistoryDashboardRequest);

    @POST("Pickup/past-driver-pickup")
    Call<PickupHistoryResponse> getPickupHistory(@Body PickupHistoryRequest pickupHistoryRequest);

    @GET("getCategory")
    Call<CaseCategoryListResponse> getCaseCategory();

    @POST("caselog")
    Call<CreateCaseLogResponse> createCaseLog(@Body CreateCaseLogRequest resetPasswordRequest);

    @GET("getcaselog")
    Call<CaseLogListResponse> getCaseLogList(@Query("generator_type") String generatorType,
                                             @Query("case_generator_id") String caseGeneratorId);
}
