package com.mottainai.driver.ui.signup;

import androidx.annotation.Nullable;

public class LicenceFormState {
    @Nullable
    private Integer error;
    private boolean isDataValid;

    LicenceFormState(@Nullable Integer error) {
        this.error = error;
        this.isDataValid = false;
    }

    LicenceFormState(boolean isDataValid) {
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
