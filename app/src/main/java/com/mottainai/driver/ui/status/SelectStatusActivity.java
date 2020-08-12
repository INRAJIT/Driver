package com.mottainai.driver.ui.status;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.mottainai.driver.R;
import com.mottainai.driver.ui.dialogs.CustomProgressBar;
import com.mottainai.driver.utils.Constants;
import com.mottainai.driver.utils.PrefManager;

public class SelectStatusActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView close;
    private RadioGroup radioStatus;
    private CustomProgressBar progressBar;
    private SelectStatusViewModel selectStatusViewModel;
    private PrefManager prefManager;
    private String driverId;
    private int status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_status);
        init();
    }

    private void init() {
        close = findViewById(R.id.close);
        radioStatus = findViewById(R.id.radio_status);
        close.setOnClickListener(this);

        selectStatusViewModel = new ViewModelProvider(this).get(SelectStatusViewModel.class);
        prefManager = new PrefManager(this);
        progressBar = new CustomProgressBar();
        driverId = prefManager.getSharedPrefValue(PrefManager.ID);
        status = prefManager.getSharedPrefIntValue(PrefManager.DRIVER_STATUS);
        ((RadioButton)radioStatus.getChildAt(status - 1)).setChecked(true);

        radioStatus.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.ready) {
                    setDriverStatus(Constants.STATUS_READY);
                } else if (checkedId == R.id.full) {
                    setDriverStatus(Constants.STATUS_TRUCK_FULL);
                } else if (checkedId == R.id.onbreak) {
                    setDriverStatus(Constants.STATUS_ON_BREAK);
                } else if (checkedId == R.id.busy) {
                    setDriverStatus(Constants.STATUS_BUSY);
                }
            }
        });

        selectStatusViewModel.getDriverStatusResponse().observe(this, driverStatusResponse -> {
            progressBar.dialog.dismiss();
            if (driverStatusResponse.getStatus()) {
                prefManager.setSharedPrefValue(PrefManager.DRIVER_STATUS, status);
                Toast.makeText(this, driverStatusResponse.getMessage(), Toast.LENGTH_SHORT).show();
                sendDataToDashBoard();
                finish();
            }
        });

    }

    private void setDriverStatus(int status) {
        this.status = status;
        progressBar.show(this, getString(R.string.progress_dialog_title));
        selectStatusViewModel.setDriverStatus(driverId, status);
    }

    @Override
    public void onClick(View v) {
        if (v == close) {
            sendDataToDashBoard();
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        sendDataToDashBoard();
        super.onBackPressed();
    }

    private void sendDataToDashBoard() {
        Intent intent = new Intent();
        intent.putExtra(Constants.DRIVER_STATUS, status);
        setResult(RESULT_OK, intent);
    }
}
