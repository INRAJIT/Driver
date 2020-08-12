package com.mottainai.driver.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mottainai.driver.BaseActivity;
import com.mottainai.driver.R;
import com.mottainai.driver.data.model.profile.DrivingLicence;
import com.mottainai.driver.ui.dialogs.CustomProgressBar;
import com.mottainai.driver.utils.ImageLoaderUtils;
import com.mottainai.driver.utils.PrefManager;

public class DrivingLicenceFragment extends Fragment {

    private ImageView licenceImage;
    private TextView licenseNo;
    private TextView name;
    private TextView dob;
    private TextView nationality;
    private TextView iss;
    private TextView exp;
    private TextView phone;

    private CustomProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_driving_licence, container, false);
        init(view);
        return view;
    }

    private void init(View rootView) {
        ((BaseActivity) getActivity()).setToolbarTitle(getString(R.string.driving_licence_title));
        licenceImage = rootView.findViewById(R.id.licenceImage);
        licenseNo = rootView.findViewById(R.id.license_no);
        name = rootView.findViewById(R.id.name);
        dob = rootView.findViewById(R.id.dob);
        nationality = rootView.findViewById(R.id.nationality);
        iss = rootView.findViewById(R.id.iss);
        exp = rootView.findViewById(R.id.exp);
        phone = rootView.findViewById(R.id.phone);

        progressBar = new CustomProgressBar();
        getDrivingLicenceDetails();

        ((ProfileActivity)getActivity()).profileViewModel.getDrivingLicenceResponse().observe(getViewLifecycleOwner(), drivingLicenceResponse -> {
            progressBar.dialog.dismiss();
            if(drivingLicenceResponse.getStatus()) {
                DrivingLicence drivingLicence = drivingLicenceResponse.getSuccess();
                ImageLoaderUtils.getInstance().loadImage(getContext(), licenceImage, drivingLicence.getLicenceImage());
                licenseNo.setText(drivingLicence.getLicenceNumber());
                name.setText(drivingLicence.getName());
                dob.setText(drivingLicence.getDateOfBirth());
                nationality.setText(drivingLicence.getNationality());
                iss.setText(drivingLicence.getIssueValidityPeriod());
                exp.setText(drivingLicence.getExpireValidityPeriod());
                phone.setText(drivingLicence.getNextToKinPhoneNo());
            }
        });
    }

    private void getDrivingLicenceDetails() {
        progressBar.show(getContext(), getString(R.string.progress_dialog_title));
        ((ProfileActivity)getActivity()).profileViewModel.getDrivingLicenceDetails(new PrefManager(getContext()).
                getSharedPrefValue(PrefManager.ID));
    }

}
