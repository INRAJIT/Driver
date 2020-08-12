package com.mottainai.driver.ui.caselog;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mottainai.driver.data.CaseLogRepository;
import com.mottainai.driver.data.model.caselog.CaseCategoryListResponse;
import com.mottainai.driver.data.model.caselog.CaseLogListResponse;
import com.mottainai.driver.data.model.caselog.CreateCaseLogRequest;
import com.mottainai.driver.data.model.caselog.CreateCaseLogResponse;

public class CaseLogViewModel extends ViewModel {
    private MutableLiveData<CaseCategoryListResponse> caseCategoryListResponse = new MutableLiveData<>();
    private MutableLiveData<CreateCaseLogResponse> createCaseLogResponse = new MutableLiveData<>();
    private MutableLiveData<CaseLogListResponse> caseLogListResponse = new MutableLiveData<>();

    private CaseLogRepository caseLogRepository = CaseLogRepository.getInstance();

    LiveData<CaseCategoryListResponse> getCaseCategoryResponse() {
        return caseCategoryListResponse;
    }

    LiveData<CreateCaseLogResponse> createCaseLogResponse() {
        return createCaseLogResponse;
    }

    LiveData<CaseLogListResponse> getCaseLogListResponse() {
        return caseLogListResponse;
    }

    public void getCaseCategory() {
        caseLogRepository.getCaseCategory(caseCategoryListResponse);
    }

    public void createCaseLog(String caseCategoryId, String comments , String caseGeneratorId) {
        caseLogRepository.createCaseLog(new CreateCaseLogRequest(caseCategoryId, comments, "1", "1", caseGeneratorId),
                createCaseLogResponse);
    }

    public void getCaseLogList(String caseGeneratorId) {
        caseLogRepository.getCaseLogList("1", caseGeneratorId, caseLogListResponse);
    }
}
