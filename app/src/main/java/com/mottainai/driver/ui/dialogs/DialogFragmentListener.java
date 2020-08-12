package com.mottainai.driver.ui.dialogs;

import android.os.Bundle;

import com.mottainai.driver.utils.EnumConstants;

public interface DialogFragmentListener {
    void onDialogFragmentListItemClick(Bundle bundle, EnumConstants.TransactionType requestedScreen);
}
