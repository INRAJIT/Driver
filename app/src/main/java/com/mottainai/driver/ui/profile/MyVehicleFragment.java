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
import com.mottainai.driver.data.model.profile.VehicleDetails;
import com.mottainai.driver.ui.dialogs.CustomProgressBar;
import com.mottainai.driver.utils.ImageLoaderUtils;
import com.mottainai.driver.utils.PrefManager;

public class MyVehicleFragment extends Fragment {

    private ImageView vehicleImage;
    private TextView chasisNo;
    private TextView registrationNo;
    private TextView registrationDate;
    private TextView modelNo;
    private TextView gpsId;
    private TextView name;
    private TextView fuelType;

    private CustomProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_vehicle, container, false);
        init(view);
        return view;
    }

    private void init(View rootView) {
        ((BaseActivity) getActivity()).setToolbarTitle(getString(R.string.my_vehicle_title));
        vehicleImage = rootView.findViewById(R.id.vehicleImage);
        chasisNo = rootView.findViewById(R.id.chasis_no);
        registrationNo = rootView.findViewById(R.id.registration_no);
        registrationDate = rootView.findViewById(R.id.registration_date);
        modelNo = rootView.findViewById(R.id.model_no);
        gpsId = rootView.findViewById(R.id.gps_id);
        name = rootView.findViewById(R.id.name);
        fuelType = rootView.findViewById(R.id.fuel_type);

        progressBar = new CustomProgressBar();
        getVehicleDetails();

        ((ProfileActivity) getActivity()).profileViewModel.getVehicleDetailsResponse().observe(getViewLifecycleOwner(), vehicleDetailsResponse -> {
            progressBar.dialog.dismiss();
            if (vehicleDetailsResponse.getStatus()) {
                VehicleDetails vehicleDetails = vehicleDetailsResponse.getSuccess();
                ImageLoaderUtils.getInstance().loadImage(getContext(), vehicleImage, vehicleDetails.getVehicleImage());
                chasisNo.setText(vehicleDetails.getChassisNumber());
                registrationNo.setText(vehicleDetails.getVehicleRegistrationNumber());
                registrationDate.setText(vehicleDetails.getVehicleRegistrationDate());
                modelNo.setText(vehicleDetails.getVehicleModelNo());
                gpsId.setText(vehicleDetails.getGPSDeviceId());
                name.setText(vehicleDetails.getOwnerName());
                fuelType.setText(vehicleDetails.getFuelType());
            }
        });

    }

    private void getVehicleDetails() {
        progressBar.show(getContext(), getString(R.string.progress_dialog_title));
        ((ProfileActivity) getActivity()).profileViewModel.getVehicleDetails(new PrefManager(getContext()).
                getSharedPrefValue(PrefManager.ID));
    }
}
