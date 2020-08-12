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
import com.mottainai.driver.data.model.signup.SignupResponse;
import com.mottainai.driver.ui.dialogs.CustomProgressBar;

public class ContactDetailsFragment extends Fragment implements View.OnClickListener {

    private AppCompatEditText editFirstName;
    private AppCompatEditText editMiddleName;
    private AppCompatEditText editLastName;
    private AppCompatEditText editPhone;
    private Button next;
    private CustomProgressBar progressBar;
    private String name, registrationNo, address, pincode, country, state, city;
    private String firstName, middleName, lastName, phone;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact_details, container, false);
        init(view);
        return view;
    }

    private void init(View rootView) {
        editFirstName = rootView.findViewById(R.id.edit_first_name);
        editMiddleName = rootView.findViewById(R.id.edit_middle_name);
        editLastName = rootView.findViewById(R.id.edit_last_name);
        editPhone = rootView.findViewById(R.id.edit_phone);
        next = rootView.findViewById(R.id.next);
        next.setOnClickListener(this);
        progressBar = new CustomProgressBar();

        Bundle bundle = getArguments();
        name = bundle.getString("name");
        registrationNo = bundle.getString("registrationNo");
        address = bundle.getString("address");
        pincode = bundle.getString("pincode");
        country = bundle.getString("country");
        state = bundle.getString("state");
        city = bundle.getString("city");

        ((SignupActivity) getActivity()).signupViewModel.getSignupFormContactState().observe(getViewLifecycleOwner(), new Observer<SignupFormState>() {
            @Override
            public void onChanged(SignupFormState signupFormState) {
                if (signupFormState.isDataValid()) {
                    progressBar.show(getActivity(), getString(R.string.progress_dialog_title));
                    ((SignupActivity) getActivity()).signupViewModel.signupCompanyDetails("2", name, registrationNo,
                            address, country, state, city, pincode, firstName, middleName, lastName, phone);
                } else {
                    Toast.makeText(getActivity(), signupFormState.getError(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        ((SignupActivity) getActivity()).signupViewModel.getSignupResponse().observe(getViewLifecycleOwner(), new Observer<SignupResponse>() {
            @Override
            public void onChanged(SignupResponse signupResponse) {
                progressBar.dialog.dismiss();
                if (signupResponse.getStatus()) {
                    Toast.makeText(getActivity(), "Account Id: " + signupResponse.getAccountId(), Toast.LENGTH_SHORT).show();

                    Bundle bundle = new Bundle();
                    bundle.putString("accountId", signupResponse.getAccountId());
                    bundle.putString("phone", phone);

                    PhoneVerificationFragment phoneVerificationFragment = new PhoneVerificationFragment();
                    phoneVerificationFragment.setArguments(bundle);
                    ((SignupActivity) getActivity()).replaceFragment(phoneVerificationFragment,
                            PhoneVerificationFragment.class.getName());
                } else {
                    Toast.makeText(getActivity(), signupResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == next) {
            firstName = editFirstName.getText().toString();
            middleName = editMiddleName.getText().toString();
            lastName = editLastName.getText().toString();
            phone = editPhone.getText().toString();

            ((SignupActivity) getActivity()).signupViewModel.signupContactDataChanged(firstName, middleName,
                    lastName, phone);
        }
    }

}
