package com.mottainai.driver.ui.signup;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;

import com.mottainai.driver.R;
import com.mottainai.driver.data.model.signup.SetVehicleDetailsResponse;
import com.mottainai.driver.data.model.signup.VehicleType;
import com.mottainai.driver.ui.dialogs.CustomProgressBar;
import com.mottainai.driver.ui.dialogs.DateAndTimePickerDialogs;
import com.mottainai.driver.ui.dialogs.DateTimeSetListener;
import com.mottainai.driver.ui.dialogs.DialogFragmentListener;
import com.mottainai.driver.ui.dialogs.SimpleRecyclerDialogFragment;
import com.mottainai.driver.utils.EnumConstants;
import com.mottainai.driver.utils.SelectImageUtils;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;
import static com.mottainai.driver.ui.dialogs.DialogViewHolder.INPUT_DATA;
import static com.mottainai.driver.ui.dialogs.DialogViewHolder.INPUT_TYPE;
import static com.mottainai.driver.ui.dialogs.DialogViewHolder.SELECTED_ROW_DATA;
import static com.mottainai.driver.ui.dialogs.DialogViewHolder.TYPE_VEHICLE_TYPES;
import static com.mottainai.driver.utils.SelectImageUtils.SELECT_GALLERY_IMAGE_REQUEST_ONE;
import static com.mottainai.driver.utils.SelectImageUtils.SELECT_GALLERY_IMAGE_REQUEST_TWO;

public class VehicleDetailsFragment extends Fragment implements View.OnClickListener, DialogFragmentListener, DateTimeSetListener {

    private LinearLayout selectVehicleType;
    private TextView textViewVehicleType;
    private AppCompatEditText editRegistration;
    private AppCompatTextView textExpiry;
    private AppCompatTextView selectCertificate;
    private AppCompatTextView selectInsurance;
    private Button next;
    private CustomProgressBar progressBar;
    private DateAndTimePickerDialogs dateAndTimePickerDialogs;

    private String vehicleType, vehicleRegistrationNumber, registrationExpiryDate, registrationCertificate,
            insuranceCertificate;
    private ArrayList<VehicleType> vehicleTypeList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vehicle_details, container, false);
        init(view);
        return view;
    }

    private void init(View rootView) {
        selectVehicleType = rootView.findViewById(R.id.select_vehicle_type);
        textViewVehicleType = rootView.findViewById(R.id.text_vehicle_type);
        editRegistration = rootView.findViewById(R.id.edit_registration);
        textExpiry = rootView.findViewById(R.id.edit_expiry);
        selectCertificate = rootView.findViewById(R.id.select_certificate);
        selectInsurance = rootView.findViewById(R.id.select_insurance);
        next = rootView.findViewById(R.id.next);
        selectVehicleType.setOnClickListener(this);
        textExpiry.setOnClickListener(this);
        selectCertificate.setOnClickListener(this);
        selectInsurance.setOnClickListener(this);
        next.setOnClickListener(this);
        progressBar = new CustomProgressBar();
        dateAndTimePickerDialogs = new DateAndTimePickerDialogs(getContext(), this);

        // get VehicleTypes
        getVehicleTypes();

        ((SignupActivity) getActivity()).signupViewModel.getVehicleTypeResponse().observe(getViewLifecycleOwner(),
                vehicleTypeResponse -> {
                    progressBar.dialog.dismiss();
                    vehicleTypeList = (ArrayList<VehicleType>) vehicleTypeResponse.getVehicleType();
                });


        ((SignupActivity) getActivity()).signupViewModel.getVehicleDetailsFormState().observe(getViewLifecycleOwner(), new Observer<VehicleFormState>() {
            @Override
            public void onChanged(VehicleFormState vehicleFormState) {
                if (vehicleFormState.isDataValid()) {
                    progressBar.show(getActivity(), getString(R.string.progress_dialog_title));
                    final String accountId = getArguments().getString("accountId");
                    ((SignupActivity) getActivity()).signupViewModel.setVehicleDetails(accountId,
                            vehicleType, vehicleRegistrationNumber, registrationExpiryDate, registrationCertificate, insuranceCertificate);
                } else {
                    Toast.makeText(getActivity(), vehicleFormState.getError(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        ((SignupActivity) getActivity()).signupViewModel.getVehicleDetailsResponse().observe(getViewLifecycleOwner(), new Observer<SetVehicleDetailsResponse>() {
            @Override
            public void onChanged(SetVehicleDetailsResponse vehicleDetailsResponse) {
                progressBar.dialog.dismiss();
                if (vehicleDetailsResponse.getStatus()) {
                    Toast.makeText(getActivity(), vehicleDetailsResponse.getAccountId(), Toast.LENGTH_SHORT).show();
                    getActivity().finish();
                } else {
                    Toast.makeText(getActivity(), vehicleDetailsResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == next) {
            vehicleRegistrationNumber = editRegistration.getText().toString();
            registrationExpiryDate = textExpiry.getText().toString();
            ((SignupActivity) getActivity()).signupViewModel.vehicleDataChanged(vehicleType, vehicleRegistrationNumber,
                    registrationExpiryDate, registrationCertificate, insuranceCertificate);
        } else if (v == selectVehicleType) {
            showVehicleListInDialog(vehicleTypeList, this, EnumConstants.TransactionType.SELECT_VEHICLE_TYPE);
        } else if (v == textExpiry) {
            dateAndTimePickerDialogs.showDatePickerDialog(true);
        } else if (v == selectCertificate) {
            SelectImageUtils.selectImageFromGallery(this, SELECT_GALLERY_IMAGE_REQUEST_ONE);
        } else if (v == selectInsurance) {
            SelectImageUtils.selectImageFromGallery(this, SELECT_GALLERY_IMAGE_REQUEST_TWO);
        }
    }

    protected void showVehicleListInDialog(ArrayList<VehicleType> optionList, Fragment sourceFragment,
                                           EnumConstants.TransactionType transactionScreen) {
        Bundle bundle = new Bundle();
        bundle.putString(INPUT_TYPE, TYPE_VEHICLE_TYPES);
        bundle.putParcelableArrayList(INPUT_DATA, optionList);
        FragmentManager fm = getActivity().getSupportFragmentManager();
        SimpleRecyclerDialogFragment optionAlertFragment =
                SimpleRecyclerDialogFragment.newInstance(transactionScreen,
                        EnumConstants.ParentComponentType.FRAGMENT,
                        bundle);
        optionAlertFragment.setTargetFragment(sourceFragment, 200);
        optionAlertFragment.show(fm, "fragment_edit_name");
    }

    private void getVehicleTypes() {
        progressBar.show(getActivity(), getString(R.string.progress_dialog_title));
        ((SignupActivity) getActivity()).signupViewModel.getVehicleTypes();
    }

    @Override
    public void onDialogFragmentListItemClick(Bundle bundle, EnumConstants.TransactionType requestedScreen) {
        VehicleType selectedData = bundle.getParcelable(SELECTED_ROW_DATA);
        textViewVehicleType.setText(selectedData.getVehicleTypeName());
        vehicleType = selectedData.getId();
    }

    @Override
    public void onDateSet(String date) {
        textExpiry.setText(date);
    }

    @Override
    public void onTimeSet(String time) {
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case SELECT_GALLERY_IMAGE_REQUEST_ONE:
                    registrationCertificate = getImagePath(data);
                    selectCertificate.setText(registrationCertificate);
                    break;
                case SELECT_GALLERY_IMAGE_REQUEST_TWO:
                    insuranceCertificate = getImagePath(data);
                    selectInsurance.setText(insuranceCertificate);
                    break;
            }
        }
    }

    private String getImagePath(Intent data) {
        String picturePath = null;
        if (data != null) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            if (selectedImage != null) {
                Cursor cursor = getContext().getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                if (cursor != null) {
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                     picturePath = cursor.getString(columnIndex);
                    cursor.close();
                }
            }
        }
        return picturePath;
    }

}
