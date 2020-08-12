package com.mottainai.driver.ui.signup;

import android.os.Bundle;

import androidx.lifecycle.ViewModelProvider;

import com.mottainai.driver.BaseActivity;
import com.mottainai.driver.R;

public class SignupActivity extends BaseActivity {

    SignupViewModel signupViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        init();
    }

    private void init() {
        setup(getString(R.string.signup_hauler));
        addFragment(new SelectTypeFragment(), SelectTypeFragment.class.getName());
        signupViewModel = new ViewModelProvider(this).get(SignupViewModel.class);
    }
}
