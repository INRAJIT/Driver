package com.mottainai.driver.data;

import androidx.lifecycle.MutableLiveData;

import com.mottainai.driver.data.model.caselog.CaseCategoryListResponse;
import com.mottainai.driver.data.model.caselog.CaseLogListResponse;
import com.mottainai.driver.data.model.caselog.CreateCaseLogRequest;
import com.mottainai.driver.data.model.caselog.CreateCaseLogResponse;
import com.mottainai.driver.networking.APIInterface;
import com.mottainai.driver.networking.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CaseLogRepository {
    private static volatile CaseLogRepository instance;

    APIInterface apiInterface;

    // private constructor : singleton access
    private CaseLogRepository() {
        this.apiInterface = RetrofitService.createService(APIInterface.class);
    }

    public static CaseLogRepository getInstance() {
        if (instance == null) {
            instance = new CaseLogRepository();
        }
        return instance;
    }

    public void getCaseCategory(final MutableLiveData<CaseCategoryListResponse> caseLogListResponse) {
        apiInterface.getCaseCategory().
                enqueue(new Callback<CaseCategoryListResponse>() {
                    @Override
                    public void onResponse(Call<CaseCategoryListResponse> call, Response<CaseCategoryListResponse> response) {
                        if (response.isSuccessful()) {
                            caseLogListResponse.setValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<CaseCategoryListResponse> call, Throwable t) {
                    }
                });
    }

    public void createCaseLog(CreateCaseLogRequest createCaseLogRequest, final MutableLiveData<CreateCaseLogResponse> createCaseLogResponse) {
        apiInterface.createCaseLog(createCaseLogRequest).enqueue(new Callback<CreateCaseLogResponse>() {
            @Override
            public void onResponse(Call<CreateCaseLogResponse> call, Response<CreateCaseLogResponse> response) {
                if (response.isSuccessful()) {
                    createCaseLogResponse.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<CreateCaseLogResponse> call, Throwable t) {

            }
        });
    }

    public void getCaseLogList(String generatorType, String caseGeneratorId, final MutableLiveData<CaseLogListResponse> caseLogListResponse) {
        apiInterface.getCaseLogList(generatorType, caseGeneratorId).enqueue(new Callback<CaseLogListResponse>() {
            @Override
            public void onResponse(Call<CaseLogListResponse> call, Response<CaseLogListResponse> response) {
                if(response.isSuccessful()) {
                    caseLogListResponse.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<CaseLogListResponse> call, Throwable t) {

            }
        });
    }
}
