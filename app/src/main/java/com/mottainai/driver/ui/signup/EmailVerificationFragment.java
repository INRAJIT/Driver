package com.mottainai.driver.ui.signup;

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
import com.mottainai.driver.data.model.signup.OtpResponse;
import com.mottainai.driver.ui.dialogs.CustomProgressBar;

public class EmailVerificationFragment extends Fragment implements View.OnClickListener {

    private PinView otp;
    TextView otpInfo;
    private CustomProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_email_verification, container, false);
        init(view);
        return view;
    }

    private void init(View rootView) {
        otp = rootView.findViewById(R.id.otp);
        otpInfo = rootView.findViewById(R.id.otp_info);
        final Button verify = rootView.findViewById(R.id.verify);
        verify.setOnClickListener(this);
        progressBar = new CustomProgressBar();

        String otpInfoText = String.format(getString(R.string.email_verification_info),
                getArguments().getString("email"));
        otpInfo.setText(otpInfoText);

        ((SignupActivity) getActivity()).signupViewModel.getOtpEmailResponse().observe(getViewLifecycleOwner(), new Observer<OtpResponse>() {
            @Override
            public void onChanged(OtpResponse otpResponse) {
                progressBar.dialog.dismiss();
                if (otpResponse.getStatus()) {
                    Toast.makeText(getActivity(), otpResponse.getMessage(), Toast.LENGTH_SHORT).show();

                    Bundle bundle = new Bundle();
                    bundle.putString("accountId", otpResponse.getUserId());

                    DrivingLicenseDetailsFragment drivingLicenseDetailsFragment = new DrivingLicenseDetailsFragment();
                    drivingLicenseDetailsFragment.setArguments(bundle);

                    ((SignupActivity) getActivity()).replaceFragment(drivingLicenseDetailsFragment,
                            DrivingLicenseDetailsFragment.class.getName());
                } else {
                    Toast.makeText(getActivity(), otpResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.verify) {
            if (otp.getText().toString().length() == 4) {
                progressBar.show(getActivity(), getString(R.string.progress_dialog_title));
                final String accountId = getArguments().getString("accountId");
                ((SignupActivity) getActivity()).signupViewModel.verifyEmailOtp(accountId, "2",
                        "1", otp.getText().toString());
            } else {
                Toast.makeText(getActivity(), R.string.invalid_otp, Toast.LENGTH_SHORT).show();
            }
        }
    }

}
