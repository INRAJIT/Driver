package com.mottainai.driver.ui.signup;

import androidx.annotation.Nullable;

public class VehicleFormState {
    @Nullable
    private Integer error;
    private boolean isDataValid;

    VehicleFormState(@Nullable Integer error) {
        this.error = error;
        this.isDataValid = false;
    }

    VehicleFormState(boolean isDataValid) {
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
