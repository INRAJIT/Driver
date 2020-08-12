package com.mottainai.driver.ui.signup;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.mottainai.driver.R;
import com.mottainai.driver.data.model.signup.SetLicenceDetailsResponse;
import com.mottainai.driver.ui.dialogs.CustomProgressBar;
import com.mottainai.driver.ui.dialogs.DateAndTimePickerDialogs;
import com.mottainai.driver.ui.dialogs.DateTimeSetListener;

public class DrivingLicenseDetailsFragment extends Fragment implements View.OnClickListener, DateTimeSetListener {

    private LinearLayout uploadBox;
    private AppCompatEditText editFirstName;
    private AppCompatEditText editMiddleName;
    private AppCompatEditText editLastName;
    private AppCompatEditText editLicenseNo;
    private AppCompatTextView textExpiry;
    private Button next;
    private CustomProgressBar progressBar;
    private DateAndTimePickerDialogs dateAndTimePickerDialogs;

    String firstName, middleName, lastName, drivingLicenceNo, licenceExpiryDate;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_driving_license_details, container, false);
        init(view);
        return view;
    }

    private void init(View rootView) {
        uploadBox = rootView.findViewById(R.id.upload_box);
        editFirstName = rootView.findViewById(R.id.edit_first_name);
        editMiddleName = rootView.findViewById(R.id.edit_middle_name);
        editLastName = rootView.findViewById(R.id.edit_last_name);
        editLicenseNo = rootView.findViewById(R.id.edit_license_no);
        textExpiry = rootView.findViewById(R.id.edit_expiry);
        next = (Button) rootView.findViewById(R.id.next);
        textExpiry.setOnClickListener(this);
        next.setOnClickListener(this);
        progressBar = new CustomProgressBar();
        dateAndTimePickerDialogs = new DateAndTimePickerDialogs(getContext(), this);

        ((SignupActivity) getActivity()).signupViewModel.getLicenceDetailsFormState().observe(getViewLifecycleOwner(), new Observer<LicenceFormState>() {
            @Override
            public void onChanged(LicenceFormState licenceFormState) {
                if (licenceFormState.isDataValid()) {
                    progressBar.show(getActivity(), getString(R.string.progress_dialog_title));
                    final String accountId = getArguments().getString("accountId");
                    ((SignupActivity) getActivity()).signupViewModel.setLicenseDetails(accountId,
                            firstName, middleName, lastName, drivingLicenceNo, licenceExpiryDate);
                } else {
                    Toast.makeText(getActivity(), licenceFormState.getError(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        ((SignupActivity) getActivity()).signupViewModel.getLicenceDetailsResponse().observe(getViewLifecycleOwner(), new Observer<SetLicenceDetailsResponse>() {
            @Override
            public void onChanged(SetLicenceDetailsResponse licenceDetailsResponse) {
                progressBar.dialog.dismiss();
                if (licenceDetailsResponse.getStatus()) {
                    Bundle bundle = new Bundle();
                    bundle.putString("accountId", licenceDetailsResponse.getAccountId());

                    VehicleDetailsFragment vehicleDetailsFragment = new VehicleDetailsFragment();
                    vehicleDetailsFragment.setArguments(bundle);

                    ((SignupActivity) getActivity()).replaceFragment(vehicleDetailsFragment,
                            VehicleDetailsFragment.class.getName());
                } else {
                    Toast.makeText(getActivity(), licenceDetailsResponse.getMessage(), Toast.LENGTH_SHORT).show();
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
            drivingLicenceNo = editLicenseNo.getText().toString();
            licenceExpiryDate = textExpiry.getText().toString();
            ((SignupActivity) getActivity()).signupViewModel.licenceDataChanged(firstName, middleName,
                    lastName, drivingLicenceNo, licenceExpiryDate);
        } else if (v == textExpiry) {
            dateAndTimePickerDialogs.showDatePickerDialog(true);
        }
    }

    @Override
    public void onDateSet(String date) {
        textExpiry.setText(date);
    }

    @Override
    public void onTimeSet(String time) {

    }
}
