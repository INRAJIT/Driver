package com.mottainai.driver.ui.signup;

import androidx.annotation.Nullable;

public class SignupFormState {
    @Nullable
    private Integer error;
    private boolean isDataValid;

    SignupFormState(@Nullable Integer error) {
        this.error = error;
        this.isDataValid = false;
    }

    SignupFormState(boolean isDataValid) {
        this.error = null;
        this.isDataValid = isDataValid;
    }

    @Nullable
    Integer getError() {
        return error;
    }

    boolean isDataValid() {
        return isDataValid;
    }
}
