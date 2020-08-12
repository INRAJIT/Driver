package com.mottainai.driver.ui.profile;

import android.os.Bundle;

import androidx.lifecycle.ViewModelProvider;

import com.mottainai.driver.BaseActivity;
import com.mottainai.driver.R;

public class ProfileActivity extends BaseActivity {

    ProfileViewModel profileViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        init();
    }

    private void init() {
        setup(getString(R.string.my_profile_title));
        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        addFragment(new MyProfileFragment(), MyProfileFragment.class.getName());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(mFragmentManager.getBackStackEntryCount() == 0) {
            setToolbarTitle(getString(R.string.my_profile_title));
        }
    }
}
