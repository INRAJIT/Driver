package com.mottainai.driver.ui.home;

import android.os.Bundle;
import android.util.Log;

import androidx.lifecycle.ViewModelProvider;

import com.mottainai.driver.BaseActivity;
import com.mottainai.driver.DashBoardViewModel;
import com.mottainai.driver.R;
import com.mottainai.driver.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class PickupActivity extends BaseActivity {

    private List<Integer> pickupList;
    public DashBoardViewModel dashBoardViewModel;
    public String customerImage, customerName, customerAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pickup);
        init();
    }

    private void init() {
        // get DashBoard View Model
        dashBoardViewModel = new ViewModelProvider(this).get(DashBoardViewModel.class);
        setup(getString(R.string.enter_pickup_title));
        Bundle bundle = getIntent().getExtras();
        pickupList = bundle.getIntegerArrayList(Constants.KEY_PICKUP_IDS);
        customerImage = bundle.getString(Constants.KEY_CUSTOMER_IMAGE);
        customerName = bundle.getString(Constants.KEY_CUSTOMER_NAME);
        customerAddress = bundle.getString(Constants.KEY_CUSTOMER_ADDRESS);

        // starting fragment
        startEnterPickupDetailsFragment();
    }

    private void startEnterPickupDetailsFragment() {
        EnterPickupDetailsFragment enterPickupDetailsFragment = new EnterPickupDetailsFragment();
        Bundle bundleFragment = new Bundle();
        bundleFragment.putIntegerArrayList(Constants.KEY_PICKUP_IDS, (ArrayList<Integer>) pickupList);
        enterPickupDetailsFragment.setArguments(bundleFragment);
        replaceFragment(enterPickupDetailsFragment, EnterPickupDetailsFragment.class.getName());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(mFragmentManager.getBackStackEntryCount() == 0) {
            setToolbarTitle(getString(R.string.enter_pickup_title));
        }
    }
}
