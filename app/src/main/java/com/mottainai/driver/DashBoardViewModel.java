package com.mottainai.driver;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mottainai.driver.data.DashBoardRepository;
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

import java.util.List;

public class DashBoardViewModel extends ViewModel {

    private MutableLiveData<DriverLogoutResponse> driverLogoutResponse = new MutableLiveData<>();
    private MutableLiveData<DriverPickupResponse> driverPickupResponse = new MutableLiveData<>();
    private MutableLiveData<AcceptPickupResponse> acceptPickupResponse = new MutableLiveData<>();
    private MutableLiveData<RejectPickupResponse> declinePickupResponse = new MutableLiveData<>();
    private MutableLiveData<RejectPickupResponse> rejectPickupResponse = new MutableLiveData<>();
    private MutableLiveData<ReportProblemReasonResponse> reportProblemReasonResponse = new MutableLiveData<>();
    private MutableLiveData<PickupWasteStreamResponse> pickupWasteStreamResponse = new MutableLiveData<>();
    private MutableLiveData<Total_Driver_PickUp_Response> total_driver_pickUp_response  = new MutableLiveData<>();

    private DashBoardRepository dashBoardRepository = DashBoardRepository.getInstance();

    public LiveData<DriverLogoutResponse> getDriverLogoutResponse() {
        return driverLogoutResponse;
    }

    public LiveData<DriverPickupResponse> getDriverPickupResponse() {
        return driverPickupResponse;
    }

    public LiveData<AcceptPickupResponse> getAcceptPickupResponse() {
        return acceptPickupResponse;
    }

    public LiveData<RejectPickupResponse> getDeclinePickupResponse() {
        return declinePickupResponse;
    }

    public LiveData<RejectPickupResponse> getRejectPickupResponse() {
        return rejectPickupResponse;
    }

    public LiveData<ReportProblemReasonResponse> getReportProblemReasons() {
        return reportProblemReasonResponse;
    }

    //locha
    public LiveData<Total_Driver_PickUp_Response>  getTotal_Driver_PickUp_Response() {
        return  total_driver_pickUp_response;
    }

    public LiveData<PickupWasteStreamResponse> getPickupWasteStreamResponse() {
        return pickupWasteStreamResponse;
    }

    public void driverLogout(String driverId) {
        DriverLogoutRequest driverLogoutRequest = new DriverLogoutRequest(driverId);
        dashBoardRepository.driverLogout(driverLogoutRequest, driverLogoutResponse);
    }

    public void getDriverPickup(String driverId) {
        dashBoardRepository.getDriverPickup(driverId, "pending", "0", "1", driverPickupResponse);
    }

    public void acceptPickup(String driverId, List<Integer> pickupList) {
        dashBoardRepository.acceptPickup(new AcceptPickupRequest(driverId, pickupList), acceptPickupResponse);
    }

    public void declinePickup(String driverId, int requestType, int reasonId,
                             List<Integer> pickupId, boolean isOther, String comment) {
        if (isOther) {
            RejectPickupRequest rejectPickupRequest = new RejectPickupRequest(driverId, requestType,
                    reasonId, pickupId, 2, comment);
            dashBoardRepository.declinePickup(rejectPickupRequest, declinePickupResponse);
        } else {
            RejectPickupRequest rejectPickupRequest = new RejectPickupRequest(driverId, requestType,
                    reasonId, pickupId, 1);
            dashBoardRepository.declinePickup(rejectPickupRequest, declinePickupResponse);
        }
    }

    public void rejectPickup(String driverId, int requestType, int reasonId,
                             List<Integer> pickupId, boolean isOther, String comment) {
        if (isOther) {
            RejectPickupRequest rejectPickupRequest = new RejectPickupRequest(driverId, requestType,
                    reasonId, pickupId, 2, comment);
            dashBoardRepository.rejectPickup(rejectPickupRequest, rejectPickupResponse);
        } else {
            RejectPickupRequest rejectPickupRequest = new RejectPickupRequest(driverId, requestType,
                    reasonId, pickupId, 1);
            dashBoardRepository.rejectPickup(rejectPickupRequest, rejectPickupResponse);
        }
    }

    public void getReportProblemReasons(String reasonType) {
        dashBoardRepository.getReportProblemReasons(reasonType, reportProblemReasonResponse);
    }

    public void getPickupWasteStreams(List<Integer> pickupId) {
        dashBoardRepository.getPickupWasteStreams(new PickupWasteStreamRequest(pickupId), pickupWasteStreamResponse);
    }

    ///Additon extra api for 5g screen//
    public void postTotalPickup(String driverId,List<Integer> pickupId,List<Integer> bagsTitle, List<Integer> bagsQuantity, List<Integer> bagsWeightKg, List<Integer> bagsWeightGm, List<Integer> barCode, List<Integer> containerId, List<Integer> containerQty ) {
        dashBoardRepository.postTotalPickup(new Total_Driver_PickUp_Request(driverId,pickupId,bagsTitle,bagsQuantity,bagsWeightKg,bagsWeightGm,barCode,containerId,containerQty), total_driver_pickUp_response);
    }

}
