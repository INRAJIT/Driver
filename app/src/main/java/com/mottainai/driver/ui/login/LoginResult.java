package com.mottainai.driver.ui.login;

import androidx.annotation.Nullable;

import com.mottainai.driver.data.model.login.LoginResponse;

/**
 * Authentication result : success (user details) or error message.
 */
public class LoginResult {
    @Nullable
    private LoginResponse loginResponse;
    @Nullable
    private Integer error;

    public LoginResult(@Nullable Integer error) {
        this.error = error;
    }

    public LoginResult(@Nullable LoginResponse loginResponse) {
        this.loginResponse = loginResponse;
    }

    @Nullable
    LoginResponse getLoginResponse() {
        return loginResponse;
    }

    @Nullable
    Integer getError() {
        return error;
    }
}
