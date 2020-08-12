package com.mottainai.driver.ui.forgetpassword;

import android.os.Bundle;
import android.util.Patterns;
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
import com.mottainai.driver.data.model.forgetpassword.VerifyEmailResponse;
import com.mottainai.driver.ui.dialogs.CustomProgressBar;

public class VerifyEmailFragment extends Fragment implements View.OnClickListener {

    private AppCompatEditText editEmail;
    private CustomProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_verify_email, container, false);
        init(view);
        return view;
    }

    private void init(View rootView) {
        editEmail = rootView.findViewById(R.id.email);
        final Button next = rootView.findViewById(R.id.next);
        next.setOnClickListener(this);
        progressBar = new CustomProgressBar();

        ((ForgetPasswordActivity) getActivity()).forgetPasswordViewModel.getVerifyEmailResponse().observe(getViewLifecycleOwner(), new Observer<VerifyEmailResponse>() {
            @Override
            public void onChanged(VerifyEmailResponse verifyEmailResponse) {
                progressBar.dialog.dismiss();
                if (verifyEmailResponse.getStatus()) {
                    Toast.makeText(getActivity(), verifyEmailResponse.getMessage(), Toast.LENGTH_SHORT).show();

                    Bundle bundle = new Bundle();
                    bundle.putString("accountId", verifyEmailResponse.getDriverId());
                    bundle.putString("email", editEmail.getText().toString());

                    EnterOtpFragment enterOtpFragment = new EnterOtpFragment();
                    enterOtpFragment.setArguments(bundle);

                    ((ForgetPasswordActivity) getActivity()).replaceFragment(enterOtpFragment,
                            EnterOtpFragment.class.getName());
                } else {
                    Toast.makeText(getActivity(), verifyEmailResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.next) {
            String email = editEmail.getText().toString();
            if (isEmailValid(email)) {
                progressBar.show(getActivity(), getString(R.string.progress_dialog_title));
                ((ForgetPasswordActivity) getActivity()).forgetPasswordViewModel.verifyEmail(email);
            } else {
                Toast.makeText(getActivity(), R.string.invalid_email, Toast.LENGTH_SHORT).show();
            }
        }
    }

    // A placeholder email validation check
    private boolean isEmailValid(String email) {
        if (email == null) {
            return false;
        }
        if (email.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }
        return false;
    }
}
