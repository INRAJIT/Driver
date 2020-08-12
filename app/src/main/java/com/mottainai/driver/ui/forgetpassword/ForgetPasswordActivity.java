package com.mottainai.driver.ui.forgetpassword;

import android.os.Bundle;

import androidx.lifecycle.ViewModelProvider;

import com.mottainai.driver.BaseActivity;
import com.mottainai.driver.R;

public class ForgetPasswordActivity extends BaseActivity {

    ForgetPasswordViewModel forgetPasswordViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        init();
    }

    private void init() {
        setup(getString(R.string.forgot_password_title));
        replaceFragment(new VerifyEmailFragment(), VerifyEmailFragment.class.getName());
        forgetPasswordViewModel = new ViewModelProvider(this).get(ForgetPasswordViewModel.class);
    }
}
