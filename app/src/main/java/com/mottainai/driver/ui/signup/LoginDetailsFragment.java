package com.mottainai.driver.ui.signup;

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
import com.mottainai.driver.data.model.signup.SetLoginDetailsResponse;
import com.mottainai.driver.ui.dialogs.CustomProgressBar;

public class LoginDetailsFragment extends Fragment implements View.OnClickListener {

    private AppCompatEditText email, password;
    private Button next;
    private CustomProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login_details, container, false);
        init(view);
        return view;
    }

    private void init(View rootView) {
        email = rootView.findViewById(R.id.edit_email);
        password = rootView.findViewById(R.id.edit_password);
        next = rootView.findViewById(R.id.next);
        next.setOnClickListener(this);
        progressBar = new CustomProgressBar();

        ((SignupActivity) getActivity()).signupViewModel.getLoginFormState().observe(getViewLifecycleOwner(), new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState.getEmailError() != null) {
                    email.setError(getString(loginFormState.getEmailError()));
                } else if (loginFormState.getPasswordError() != null) {
                    password.setError(getString(loginFormState.getPasswordError()));
                } else {
                    progressBar.show(getActivity(), getString(R.string.progress_dialog_title));
                    final String accountId = getArguments().getString("accountId");
                    ((SignupActivity) getActivity()).signupViewModel.setLoginDetails(accountId, email.getText().toString(),
                            password.getText().toString());
                }
            }
        });

        ((SignupActivity) getActivity()).signupViewModel.getLoginDetailsResponse().observe(getViewLifecycleOwner(), new Observer<SetLoginDetailsResponse>() {
            @Override
            public void onChanged(SetLoginDetailsResponse loginDetailsResponse) {
                progressBar.dialog.dismiss();
                if (loginDetailsResponse.getStatus()) {
                    Toast.makeText(getActivity(), loginDetailsResponse.getAccountId(), Toast.LENGTH_SHORT).show();

                    Bundle bundle = new Bundle();
                    bundle.putString("accountId", loginDetailsResponse.getAccountId());
                    bundle.putString("email", email.getText().toString());

                    EmailVerificationFragment emailVerificationFragment = new EmailVerificationFragment();
                    emailVerificationFragment.setArguments(bundle);

                    ((SignupActivity)getActivity()).replaceFragment(emailVerificationFragment,
                            EmailVerificationFragment.class.getName());
                } else {
                    Toast.makeText(getActivity(), loginDetailsResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.next) {
            ((SignupActivity) getActivity()).signupViewModel.loginDataChanged(email.getText().toString(),
                    password.getText().toString());
        }
    }
}
