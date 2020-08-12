package com.mottainai.driver.ui.forgetpassword;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.chaos.view.PinView;
import com.mottainai.driver.R;
import com.mottainai.driver.data.model.forgetpassword.VerifyEmailResponse;
import com.mottainai.driver.data.model.signup.OtpResponse;
import com.mottainai.driver.ui.dialogs.CustomProgressBar;

public class EnterOtpFragment extends Fragment implements View.OnClickListener {

    private PinView otp;
    private TextView otpInfo;
    private TextView resend;
    private CustomProgressBar progressBar;

    private String email;
    private boolean isResendClicked;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_enter_otp, container, false);
        init(view);
        return view;
    }

    private void init(View rootView) {
        otp = rootView.findViewById(R.id.otp);
        otpInfo = rootView.findViewById(R.id.otp_info);
        resend = rootView.findViewById(R.id.resend);
        final Button verify = rootView.findViewById(R.id.verify);
        resend.setOnClickListener(this);
        verify.setOnClickListener(this);
        progressBar = new CustomProgressBar();

        email = getArguments().getString("email");
        String otpInfoText = String.format(getString(R.string.email_verification_info), email);
        otpInfo.setText(otpInfoText);

        ((ForgetPasswordActivity) getActivity()).forgetPasswordViewModel.getOtpEmailResponse().observe(getViewLifecycleOwner(), new Observer<OtpResponse>() {
            @Override
            public void onChanged(OtpResponse otpResponse) {
                progressBar.dialog.dismiss();
                if (otpResponse.getStatus()) {
                    Toast.makeText(getActivity(), otpResponse.getMessage(), Toast.LENGTH_SHORT).show();

                    Bundle bundle = new Bundle();
                    bundle.putString("accountId", otpResponse.getUserId());

                    ResetPasswordFragment resetPasswordFragment = new ResetPasswordFragment();
                    resetPasswordFragment.setArguments(bundle);

                    ((ForgetPasswordActivity) getActivity()).replaceFragment(resetPasswordFragment,
                            ResetPasswordFragment.class.getName());
                } else {
                    Toast.makeText(getActivity(), otpResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        ((ForgetPasswordActivity) getActivity()).forgetPasswordViewModel.getVerifyEmailResponse().observe(getViewLifecycleOwner(), new Observer<VerifyEmailResponse>() {
            @Override
            public void onChanged(VerifyEmailResponse verifyEmailResponse) {
                if(isResendClicked) {
                    progressBar.dialog.dismiss();
                    if (verifyEmailResponse.getStatus()) {
                        Toast.makeText(getActivity(), R.string.otp_resend_success, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), verifyEmailResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.verify) {
            if (otp.getText().toString().length() == 4) {
                progressBar.show(getActivity(), getString(R.string.progress_dialog_title));
                final String accountId = getArguments().getString("accountId");
                ((ForgetPasswordActivity) getActivity()).forgetPasswordViewModel.verifyEmailOtp(accountId, "3",
                        "1", otp.getText().toString());
            } else {
                Toast.makeText(getActivity(), R.string.invalid_otp, Toast.LENGTH_SHORT).show();
            }
        } else if(v == resend) {
            isResendClicked = true;
            progressBar.show(getActivity(), getString(R.string.progress_dialog_title));
            ((ForgetPasswordActivity) getActivity()).forgetPasswordViewModel.verifyEmail(email);
        }
    }
}
