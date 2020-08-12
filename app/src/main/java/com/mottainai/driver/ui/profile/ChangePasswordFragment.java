package com.mottainai.driver.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mottainai.driver.BaseActivity;
import com.mottainai.driver.R;
import com.mottainai.driver.ui.dialogs.CustomProgressBar;
import com.mottainai.driver.ui.forgetpassword.ForgetPasswordActivity;
import com.mottainai.driver.ui.forgetpassword.ResetSuccessFragment;
import com.mottainai.driver.utils.PrefManager;

public class ChangePasswordFragment extends Fragment implements View.OnClickListener {

    private EditText editOldPassword;
    private EditText editPassword;
    private EditText editConfirmPassword;
    private TextView forgetPassword;
    private Button submit;
    private CustomProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_password, container, false);
        init(view);
        return view;
    }

    private void init(View rootView) {
        ((BaseActivity) getActivity()).setToolbarTitle(getString(R.string.change_password_title));
        editOldPassword = rootView.findViewById(R.id.edit_old_password);
        editPassword = rootView.findViewById(R.id.edit_password);
        editConfirmPassword = rootView.findViewById(R.id.edit_confirm_password);
        forgetPassword = rootView.findViewById(R.id.forget_password);
        submit = rootView.findViewById(R.id.submit);
        progressBar = new CustomProgressBar();

        forgetPassword.setOnClickListener(this);
        submit.setOnClickListener(this);

        ((ProfileActivity) getActivity()).profileViewModel.getUpdatePasswordResponse().observe(getViewLifecycleOwner(),
                updatePasswordResponse -> {
                    progressBar.dialog.dismiss();
                    if (updatePasswordResponse.getStatus()) {
                        Toast.makeText(getContext(), updatePasswordResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        ((ProfileActivity) getActivity()).replaceFragment(new ResetSuccessFragment(),
                                ResetSuccessFragment.class.getName());
                    }
                });
    }

    @Override
    public void onClick(View v) {
        if (v == submit) {
            String oldPassword = editOldPassword.getText().toString();
            String newPassword = editPassword.getText().toString();
            String confirmPassword = editConfirmPassword.getText().toString();
            if (oldPassword.isEmpty() || !isPasswordValid(newPassword)) {
                Toast.makeText(getContext(), R.string.invalid_password, Toast.LENGTH_SHORT).show();
            } else if (!newPassword.equals(confirmPassword)) {
                Toast.makeText(getContext(), R.string.invalid_password_match, Toast.LENGTH_SHORT).show();
            } else {
                progressBar.show(getActivity(), getString(R.string.progress_dialog_title));
                String id = new PrefManager(getContext()).getSharedPrefValue(PrefManager.ID);
                ((ProfileActivity) getActivity()).profileViewModel.updatePassword(id, oldPassword, newPassword, confirmPassword);
            }
        } else if(v == forgetPassword) {
            Intent intent = new Intent(getActivity(), ForgetPasswordActivity.class);
            startActivity(intent);
        }
    }

    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }
}
