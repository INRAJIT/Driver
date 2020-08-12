package com.mottainai.driver.ui.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.mottainai.driver.BaseActivity;
import com.mottainai.driver.DashboardActivity;
import com.mottainai.driver.R;
import com.mottainai.driver.SplashActivity;
import com.mottainai.driver.data.model.login.LoginResponse;
import com.mottainai.driver.ui.dialogs.CustomProgressBar;
import com.mottainai.driver.ui.forgetpassword.ForgetPasswordActivity;
import com.mottainai.driver.ui.signup.SignupActivity;
import com.mottainai.driver.utils.PrefManager;

public class LoginActivity extends BaseActivity {

    private PrefManager prefManager;
    private LoginViewModel loginViewModel;
    private CustomProgressBar progressBar;

    private static final String TAG = LoginActivity.class.getName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    private void init() {
        setup(getString(R.string.driver_login));
        prefManager = new PrefManager(LoginActivity.this);
        loginViewModel = ViewModelProviders.of(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        final EditText usernameEditText = findViewById(R.id.email);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login);
        final TextView forgetPassword = findViewById(R.id.forget_password);
        final TextView signup = findViewById(R.id.signup);
        progressBar = new CustomProgressBar();

        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState.getEmailError() != null) {
                    Toast.makeText(getApplicationContext(), getString(loginFormState.getEmailError()), Toast.LENGTH_SHORT).show();
                } else if (loginFormState.getPasswordError() != null) {
                    Toast.makeText(getApplicationContext(), getString(loginFormState.getPasswordError()), Toast.LENGTH_SHORT).show();
                } else {
                    progressBar.show(LoginActivity.this, getString(R.string.progress_dialog_title));
                    loginViewModel.login(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString(), prefManager.getSharedPrefValue(PrefManager.DEVICE_TOKEN));
                }
            }
        });

        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) {
                    return;
                }
                progressBar.dialog.dismiss();
                if (loginResult.getError() != null) {
                    showLoginFailed(loginResult.getError());
                } else if (loginResult.getLoginResponse().getStatus()) {

                    prefManager.saveDriverDetails(loginResult.getLoginResponse().getDriver());
                    updateUiWithUser(loginResult.getLoginResponse());
                    setResult(Activity.RESULT_OK);
                    //Complete and destroy login activity once successful
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, loginResult.getLoginResponse().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString());
                }
                return false;
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        });

        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
                startActivity(intent);
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });

        // get Device Token
        getDeviceToken();
    }

    private void updateUiWithUser(LoginResponse model) {
        String welcome = getString(R.string.welcome) + model.getDriver().getName();
        // TODO : initiate successful logged in experience
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
        Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
        startActivity(intent);
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }

    private void getDeviceToken() {
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();
                        // Log and toast
                        Log.d(TAG, "Device Token-"+token);
                        prefManager.setSharedPrefValue(PrefManager.DEVICE_TOKEN, token);
                    }
                });
    }
}
