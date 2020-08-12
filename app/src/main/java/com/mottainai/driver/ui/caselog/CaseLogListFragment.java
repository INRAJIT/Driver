package com.mottainai.driver.ui.caselog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mottainai.driver.R;
import com.mottainai.driver.data.model.caselog.CaseLog;
import com.mottainai.driver.ui.dialogs.CustomProgressBar;
import com.mottainai.driver.utils.PrefManager;

import java.util.ArrayList;

public class CaseLogListFragment extends Fragment {

    private RecyclerView recyclerView;
    private CaseLogListAdapter caseLogListAdapter;
    private CustomProgressBar progressBar;

    private ArrayList<CaseLog> caseLogList = new ArrayList<>();

    private CaseLogViewModel  caseLogViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_case_log_list, container, false);
        init(view);
        return view;
    }

    private void init(View root) {
        recyclerView = root.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        caseLogListAdapter = new CaseLogListAdapter(caseLogList);
        recyclerView.setAdapter(caseLogListAdapter);
        progressBar = new CustomProgressBar();

        caseLogViewModel = new ViewModelProvider(this).get(CaseLogViewModel.class);

        caseLogViewModel.getCaseLogListResponse().observe(getViewLifecycleOwner(), caseLogListResponse -> {
            progressBar.dialog.dismiss();
            if(caseLogListResponse.getStatus()) {
                caseLogList.addAll(caseLogListResponse.getSuccess());
                caseLogListAdapter.notifyDataSetChanged();
            }
        });

        // get case-log list
        getCaseLogList();
    }

    private void getCaseLogList() {
        progressBar.show(getContext(), getString(R.string.progress_dialog_title));
        caseLogViewModel.getCaseLogList(new PrefManager(getContext()).getSharedPrefValue(PrefManager.ID));
    }
}
