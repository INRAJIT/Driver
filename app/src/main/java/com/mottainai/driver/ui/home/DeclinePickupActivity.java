package com.mottainai.driver.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.lifecycle.ViewModelProvider;

import com.mottainai.driver.DashBoardViewModel;
import com.mottainai.driver.R;
import com.mottainai.driver.data.model.home.Reason;
import com.mottainai.driver.ui.dialogs.CustomProgressBar;
import com.mottainai.driver.utils.Constants;
import com.mottainai.driver.utils.PrefManager;

import java.util.List;

public class DeclinePickupActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView close;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decline_pickup);
        init();
    }

    private void init() {
        pickupList = getIntent().getIntegerArrayListExtra(Constants.KEY_PICKUP_IDS);
        close = findViewById(R.id.close);
        radioReason = findViewById(R.id.radio_reasons);
        statusComment = findViewById(R.id.status_comment);
        commentBox = findViewById(R.id.comment);
        submit = findViewById(R.id.submit);
        close.setOnClickListener(this);
        submit.setOnClickListener(this);

        // get DashBoard View Model
        dashBoardViewModel = new ViewModelProvider(this).get(DashBoardViewModel.class);
        PrefManager prefManager = new PrefManager(this);
        driverId = prefManager.getSharedPrefValue(PrefManager.ID);
        progressBar = new CustomProgressBar();

        radioReason.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                reasonId = checkedId;
                AppCompatRadioButton radioButton = group.findViewById(checkedId);
                if(radioButton.getText().toString().equalsIgnoreCase("other")) {
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

        dashBoardViewModel.getReportProblemReasons().observe(this, reportProblemReasonResponse -> {
            progressBar.dialog.dismiss();
            if (reportProblemReasonResponse.getStatus()) {
                List<Reason> reasonList = reportProblemReasonResponse.getReasons();
                for (int i = 0; i < reasonList.size(); i++) {
                    Reason reason = reasonList.get(i);
                    AppCompatRadioButton radioButton = new AppCompatRadioButton(DeclinePickupActivity.this);
                    radioButton.setId(reason.getReasonId());
                    radioButton.setText(reason.getReasonName());
                    radioButton.setTextColor(getResources().getColor(R.color.colorTextDefault));
                    radioButton.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                            getResources().getDimension(R.dimen.text_size_medium));
                    radioReason.addView(radioButton);
                }
            } else {
                finish();
            }
        });

        dashBoardViewModel.getDeclinePickupResponse().observe(this, rejectPickupResponse -> {
            progressBar.dialog.dismiss();
            if (rejectPickupResponse.getStatus()) {
                Toast.makeText(this, rejectPickupResponse.getMessage(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, rejectPickupResponse.getMessage(), Toast.LENGTH_SHORT).show();
            }
            finish();
        });

        // get dynamic reasons
        getReportProblemReasons();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        hideSoftKeyboard();
        return super.onTouchEvent(event);
    }

    private void hideSoftKeyboard() {
        try {
            InputMethodManager inputMethodManager =
                    (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {

        }
    }

    private void getReportProblemReasons() {
        progressBar.show(this, getString(R.string.progress_dialog_title));
        dashBoardViewModel.getReportProblemReasons("1");
    }

    @Override
    public void onClick(View v) {
        if (v == close) {
            finish();
        } else if (v == submit) {
            String comment = commentBox.getText().toString();
            if (reasonId == -1) {
                Toast.makeText(this, R.string.invalid_reason, Toast.LENGTH_SHORT).show();
            } else if (commentBox.getVisibility() == View.VISIBLE && comment.isEmpty()) {
                Toast.makeText(this, R.string.invalid_comment, Toast.LENGTH_SHORT).show();
            } else {
                rejectPickup();
            }
        }
    }

    private void rejectPickup() {
        progressBar.show(this, getString(R.string.progress_dialog_title));
        dashBoardViewModel.declinePickup(driverId, 1, reasonId, pickupList, isOther, commentBox.getText().toString());
    }
}
