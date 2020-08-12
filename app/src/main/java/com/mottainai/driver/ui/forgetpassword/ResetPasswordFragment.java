package com.mottainai.driver.ui.forgetpassword;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.mottainai.driver.R;
import com.mottainai.driver.data.model.forgetpassword.ResetPasswordResponse;
import com.mottainai.driver.ui.dialogs.CustomProgressBar;

public class ResetPasswordFragment extends Fragment implements View.OnClickListener {

    private AppCompatEditText editPassword, editConfirmPassword;
    private Button submit;
    private CustomProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reset_password, container, false);
        init(view);
        return view;
    }

    private void init(View rootView) {
        editPassword = rootView.findViewById(R.id.edit_password);
        editConfirmPassword = rootView.findViewById(R.id.edit_confirm_password);
        submit = rootView.findViewById(R.id.submit);
        submit.setOnClickListener(this);
        progressBar = new CustomProgressBar();

        ((ForgetPasswordActivity) getActivity()).forgetPasswordViewModel.getResetPasswordResponse().observe(getViewLifecycleOwner(), new Observer<ResetPasswordResponse>() {
            @Override
            public void onChanged(ResetPasswordResponse resetPasswordResponse) {
                progressBar.dialog.dismiss();
                if (resetPasswordResponse.getStatus()) {
                    ((ForgetPasswordActivity) getActivity()).replaceFragment(new ResetSuccessFragment(),
                            ResetSuccessFragment.class.getName());
                } else {
                    Toast.makeText(getActivity(), resetPasswordResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == submit) {
            String password = editPassword.getText().toString();
            String confirmPassword = editConfirmPassword.getText().toString();
            if (!isPasswordValid(password)) {
                Toast.makeText(getContext(), R.string.invalid_password, Toast.LENGTH_SHORT).show();
            } else if (!password.equals(confirmPassword)) {
                Toast.makeText(getContext(), R.string.invalid_password_match, Toast.LENGTH_SHORT).show();
            } else {
                progressBar.show(getActivity(), getString(R.string.progress_dialog_title));
                final String accountId = getArguments().getString("accountId");
                ((ForgetPasswordActivity) getActivity()).forgetPasswordViewModel.resetPassword(accountId, password, confirmPassword);
            }
        }
    }

    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }

}
