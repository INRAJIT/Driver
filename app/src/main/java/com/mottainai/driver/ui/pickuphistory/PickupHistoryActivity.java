package com.mottainai.driver.ui.pickuphistory;

import android.os.Bundle;

import androidx.lifecycle.ViewModelProvider;

import com.mottainai.driver.BaseActivity;
import com.mottainai.driver.R;
import com.mottainai.driver.utils.PrefManager;

public class PickupHistoryActivity extends BaseActivity {

    String driverId;
    PickupHistoryViewModel pickupHistoryViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pickup_history);
        init();
    }

    private void init() {
        setup(getString(R.string.pickup_history_title));
        replaceFragment(new PickupHistoryDashboardFragment(), PickupHistoryDashboardFragment.class.getName());
        driverId = new PrefManager(this).getSharedPrefValue(PrefManager.ID);
        pickupHistoryViewModel = new ViewModelProvider(this).get(PickupHistoryViewModel.class);
    }
}