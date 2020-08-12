package com.mottainai.driver.ui.home;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.mottainai.driver.BaseActivity;
import com.mottainai.driver.DashBoardViewModel;
import com.mottainai.driver.R;
import com.mottainai.driver.data.model.home.Reason;
import com.mottainai.driver.ui.dialogs.CustomProgressBar;
import com.mottainai.driver.utils.Constants;
import com.mottainai.driver.utils.PrefManager;

import java.util.List;

public class ReportProblemFragment extends Fragment implements View.OnClickListener {

    private RadioGroup radioReason;
    private TextView statusComment;
    private EditText commentBox;
    private Button submit;
    private CustomProgressBar progressBar;

    private DashBoardViewModel dashBoardViewModel;
    private String driverId;
    private int reasonId = -1;
    private List<Integer> pickupList;
    private boolean isOther;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_report_problem, container, false);
        init(view);
        return view;
    }

    private void init(View rootView) {
        ((BaseActivity) getActivity()).setToolbarTitle(getString(R.string.report_problem_title));
        submit = rootView.findViewById(R.id.submit);
        pickupList = getArguments().getIntegerArrayList(Constants.KEY_PICKUP_IDS);
        radioReason = rootView.findViewById(R.id.radio_reasons);
        statusComment = rootView.findViewById(R.id.status_comment);
        commentBox = rootView.findViewById(R.id.comment);
        submit = rootView.findViewById(R.id.submit);
        submit.setOnClickListener(this);

        // get DashBoard View Model
        dashBoardViewModel = new ViewModelProvider(getActivity()).get(DashBoardViewModel.class);
        PrefManager prefManager = new PrefManager(getContext());
        driverId = prefManager.getSharedPrefValue(PrefManager.ID);
        progressBar = new CustomProgressBar();

        radioReason.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                reasonId = checkedId;
                AppCompatRadioButton radioButton = group.findViewById(checkedId);
                if (radioButton.getText().toString().equalsIgnoreCase("other")) {
                    isOther = true;
                    statusComment.setVisibility(View.VISIBLE);
                    commentBox.setVisibility(View.VISIBLE);
                } else {
                    isOther = false;
                    statusComment.setVisibility(View.GONE);
                    commentBox.setVisibility(View.GONE);
                }
            }
        });

        dashBoardViewModel.getReportProblemReasons().observe(getViewLifecycleOwner(), reportProblemReasonResponse -> {
            progressBar.dialog.dismiss();
            if (reportProblemReasonResponse.getStatus()) {
                List<Reason> reasonList = reportProblemReasonResponse.getReasons();
                for (int i = 0; i < reasonList.size(); i++) {
                    Reason reason = reasonList.get(i);
                    AppCompatRadioButton radioButton = new AppCompatRadioButton(getContext());
                    radioButton.setId(reason.getReasonId());
                    radioButton.setText(reason.getReasonName());
                    radioButton.setTextColor(getResources().getColor(R.color.colorTextDefault));
                    radioButton.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                            getResources().getDimension(R.dimen.text_size_medium));
                    radioReason.addView(radioButton);
                }
            } else {
                getActivity().finish();
            }
        });

        dashBoardViewModel.getRejectPickupResponse().observe(getViewLifecycleOwner(), rejectPickupResponse -> {
            progressBar.dialog.dismiss();
            if (rejectPickupResponse.getStatus()) {
                Toast.makeText(getContext(), rejectPickupResponse.getMessage(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), rejectPickupResponse.getMessage(), Toast.LENGTH_SHORT).show();
            }
            getActivity().finish();
        });

        // get dynamic reasons
        getReportProblemReasons();
    }

    private void getReportProblemReasons() {
        progressBar.show(getContext(), getString(R.string.progress_dialog_title));
        dashBoardViewModel.getReportProblemReasons("1");
    }

    @Override
    public void onClick(View v) {
        if (v == submit) {
            String comment = commentBox.getText().toString();
            if (reasonId == -1) {
                Toast.makeText(getContext(), R.string.invalid_reason, Toast.LENGTH_SHORT).show();
            } else if (commentBox.getVisibility() == View.VISIBLE && comment.isEmpty()) {
                Toast.makeText(getContext(), R.string.invalid_comment, Toast.LENGTH_SHORT).show();
            } else {
                rejectPickup();
            }
        }
    }

    private void rejectPickup() {
        progressBar.show(getContext(), getString(R.string.progress_dialog_title));
        dashBoardViewModel.rejectPickup(driverId, 2, reasonId, pickupList, isOther, commentBox.getText().toString());
    }

}
