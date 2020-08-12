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

import com.mottainai.driver.R;
import com.mottainai.driver.utils.ImageLoaderUtils;
import com.mottainai.driver.utils.PrefManager;

public class MyProfileFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        init(view);
        return view;
    }

    private void init(View rootView) {
        PrefManager prefManager = new PrefManager(getContext());
        ImageView profileImage = rootView.findViewById(R.id.profileImage);
        TextView name = rootView.findViewById(R.id.name);
        TextView email = rootView.findViewById(R.id.email);
        TextView phone = rootView.findViewById(R.id.phone);
        TextView licenceNo = rootView.findViewById(R.id.license_no);
        TextView dob = rootView.findViewById(R.id.dob);
        TextView drivingLicence = rootView.findViewById(R.id.driving_licence);
        TextView myVehicle = rootView.findViewById(R.id.my_vehicle);
        TextView changePassword = rootView.findViewById(R.id.change_password);

        name.setText(prefManager.getSharedPrefValue(PrefManager.NAME));
        email.setText(prefManager.getSharedPrefValue(PrefManager.EMAIL));
        phone.setText(prefManager.getSharedPrefValue(PrefManager.PHONE));
        licenceNo.setText(prefManager.getSharedPrefValue(PrefManager.LICENCE_NO));
        dob.setText(prefManager.getSharedPrefValue(PrefManager.DOB));

        ImageLoaderUtils.getInstance().updateProfileImage(getContext(), profileImage, prefManager.getSharedPrefValue(PrefManager.IMAGE));

        drivingLicence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ProfileActivity) getActivity()).addFragmentToStack(new DrivingLicenceFragment(),
                        DrivingLicenceFragment.class.getName());
            }
        });

        myVehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ProfileActivity) getActivity()).addFragmentToStack(new MyVehicleFragment(),
                        MyVehicleFragment.class.getName());
            }
        });

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ProfileActivity) getActivity()).addFragmentToStack(new ChangePasswordFragment(),
                        ChangePasswordFragment.class.getName());
            }
        });
    }

}
