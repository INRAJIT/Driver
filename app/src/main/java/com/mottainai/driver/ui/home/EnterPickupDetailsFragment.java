package com.mottainai.driver.ui.home;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.mottainai.driver.BaseActivity;
import com.mottainai.driver.R;
import com.mottainai.driver.data.model.home.Bag;
import com.mottainai.driver.data.model.home.Container;
import com.mottainai.driver.data.model.home.ContainerRequest;
import com.mottainai.driver.data.model.home.WasteStream;
import com.mottainai.driver.data.model.home.WasteStreamUpload;
import com.mottainai.driver.data.model.home.WasteStreamUploadRequest;
import com.mottainai.driver.mlkit.java.LivePreviewActivity;
import com.mottainai.driver.ui.dialogs.CustomProgressBar;
import com.mottainai.driver.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class EnterPickupDetailsFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = EnterPickupDetailsFragment.class.getName();

    private PickupActivity pickupActivity;
    private LinearLayout wasteStreamLayout;
    private LinearLayout trashLayout;
    private Button submit;
    private LinearLayout openCamera;
    private TextView reportProblem;
    private CustomProgressBar progressBar;
    private List<Integer> pickupIdList;
    private TextView textViewScanned;
    private String pikcupJson;

    private static final int REQUEST_BAR_CODE = 101;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        pickupActivity = (PickupActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_enter_pickup, container, false);
        init(view);
        return view;
    }

    private void init(View rootView) {
        pickupIdList = getArguments().getIntegerArrayList(Constants.KEY_PICKUP_IDS);
        wasteStreamLayout = rootView.findViewById(R.id.waste_stream_layout);
        trashLayout = rootView.findViewById(R.id.trash_layout);
        submit = rootView.findViewById(R.id.submit);
        reportProblem = rootView.findViewById(R.id.report_problem);
        progressBar = new CustomProgressBar();

        submit.setOnClickListener(this);
        reportProblem.setOnClickListener(this);

        pickupActivity.dashBoardViewModel.getPickupWasteStreamResponse().observe(getViewLifecycleOwner(),
                pickupWasteStreamResponse -> {
                    progressBar.dialog.dismiss();
                    if (pickupWasteStreamResponse.getStatus()) {
                        submit.setVisibility(View.VISIBLE);
                        List<WasteStream> wasteStreamList = pickupWasteStreamResponse.getWasteStream();
                        addWasteStreamLayout(wasteStreamList);
                        addTrashLayout(wasteStreamList);
                    }
                });

        // get pickup waste-streams
        getPickupWasteSteams();
    }

    private void getPickupWasteSteams() {
        progressBar.show(getContext(), getString(R.string.progress_dialog_title));
        pickupActivity.dashBoardViewModel.getPickupWasteStreams(pickupIdList);
    }

    private void addWasteStreamLayout(List<WasteStream> wasteStreamList) {
        for (int i = 0; i < wasteStreamList.size(); i++) {
            WasteStream wasteStream = wasteStreamList.get(i);
            if (!wasteStream.getWasteStreamName().equalsIgnoreCase("Trash")) {
                View wasteStreamView = LayoutInflater.from(getContext()).inflate(R.layout.row_waste_stream, wasteStreamLayout, false);
                TextView wasteStreamName = wasteStreamView.findViewById(R.id.waste_stream_name);
                EditText editQuantity = wasteStreamView.findViewById(R.id.quantity);
                LinearLayout bagLayout = wasteStreamView.findViewById(R.id.bag_layout);
                wasteStreamName.setText(wasteStream.getWasteStreamName());
                wasteStreamView.setTag(wasteStream.getWasteStreamNameId());
                //added ...
                wasteStreamView.setTag(wasteStream.getPickupId());

                editQuantity.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        if (!editable.toString().isEmpty()) {
                            int box = Integer.parseInt(editable.toString());
                            bagLayout.removeAllViews();
                            for (int j = 0; j < box; j++) {
                                View bagView = LayoutInflater.from(getContext()).inflate(R.layout.row_bag_details, bagLayout, false);
                                TextView bagName = bagView.findViewById(R.id.bag_name);
                                TextView scanBarcode = bagView.findViewById(R.id.scan_barcode);
                                int no = j + 1;
                                bagName.setText("Bag " + no);
                                scanBarcode.setOnClickListener(view -> {
                                    textViewScanned = scanBarcode;
                                    Intent intent = new Intent(getContext(), LivePreviewActivity.class);
                                    startActivityForResult(intent, REQUEST_BAR_CODE);
                                });
                                bagLayout.addView(bagView);
                            }
                        } else {
                            bagLayout.removeAllViews();
                        }
                    }
                });
                wasteStreamLayout.addView(wasteStreamView);
            }
        }
    }

    private void addTrashLayout(List<WasteStream> wasteStreamList) {
        for (int i = 0; i < wasteStreamList.size(); i++) {
            WasteStream wasteStream = wasteStreamList.get(i);
            if (wasteStream.getWasteStreamName().equalsIgnoreCase("Trash")) {
                LinearLayout trashDetails = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.row_trash_details,
                        trashLayout, false);
                trashDetails.setTag(wasteStream.getWasteStreamNameId());
                trashLayout.addView(trashDetails);
                openCamera = trashDetails.findViewById(R.id.open_camera);
                openCamera.setOnClickListener(this);

                LinearLayout containerLayout = trashDetails.findViewById(R.id.layout_container);
                List<Container> containerList = wasteStream.getContainer();
                for (int j = 0; j < containerList.size(); j++) {
                    Container container = containerList.get(j);
                    View trashBin = LayoutInflater.from(getContext()).inflate(R.layout.row_trash_bin, trashDetails, false);
                    TextView containerName = trashBin.findViewById(R.id.container_name);
                    containerName.setText(container.getContainerName());
                    trashBin.setTag(container.getId());
                    containerLayout.addView(trashBin);
                }
            }
        }

    }

    @Override
    public void onClick(View view) {
        if (view == submit) {
            if (doWasteStreamValidation() && doTrashValidation()) {
                PreviewPickupDetailsFragment previewPickupDetailsFragment = new PreviewPickupDetailsFragment();
                Bundle bundle = new Bundle();
                bundle.putString(Constants.KEY_CUSTOMER_IMAGE, pickupActivity.customerImage);
                bundle.putString(Constants.KEY_CUSTOMER_NAME, pickupActivity.customerName);
                bundle.putString(Constants.KEY_CUSTOMER_ADDRESS, pickupActivity.customerAddress);
                // for pickup id ...
               // bundle.putIntegerArrayList(Constants.KEY_PICKUP_IDS, (ArrayList<Integer>) pickupIdList);

                bundle.putString(Constants.KEY_PICKUP_JSON, pikcupJson);
                previewPickupDetailsFragment.setArguments(bundle);
                ((BaseActivity) getActivity()).replaceFragment(previewPickupDetailsFragment,
                        PreviewPickupDetailsFragment.class.getName());
            }
        } else if (view == openCamera) {
            ((BaseActivity) getActivity()).addFragmentToStack(new BeforeAfterPickupFragment(),
                    BeforeAfterPickupFragment.class.getName());
        } else if (view == reportProblem) {
            Bundle bundle = new Bundle();
            bundle.putIntegerArrayList(Constants.KEY_PICKUP_IDS, (ArrayList<Integer>) pickupIdList);
            ReportProblemFragment reportProblemFragment = new ReportProblemFragment();
            reportProblemFragment.setArguments(bundle);
            ((BaseActivity) getActivity()).addFragmentToStack(reportProblemFragment, ReportProblemFragment.class.getName());
        }
    }

    private boolean doWasteStreamValidation() {
        WasteStreamUploadRequest wasteStreamUploadRequest = new WasteStreamUploadRequest();
        List<WasteStreamUpload> wasteStreamUploadList = new ArrayList<>();

        for (int i = 0; i < wasteStreamLayout.getChildCount(); i++) {
            WasteStreamUpload wasteStreamUpload = new WasteStreamUpload();
            View wasteStreamView = wasteStreamLayout.getChildAt(i);
            TextView wasteStreamName = wasteStreamView.findViewById(R.id.waste_stream_name);
            EditText editQuantity = wasteStreamView.findViewById(R.id.quantity);
            wasteStreamUpload.setWasteStreamNameId((Integer) wasteStreamView.getTag());
            wasteStreamUpload.setWasteStreamName(wasteStreamName.getText().toString());
            wasteStreamUploadList.add(wasteStreamUpload);

            // no of bag validation
            String bagsText = editQuantity.getText().toString();
            if (bagsText.isEmpty()) {
                Toast.makeText(getContext(), R.string.invalid_bags, Toast.LENGTH_SHORT).show();
                return false;
            }

            // bag validation kg/gm
            LinearLayout bagLayout = wasteStreamView.findViewById(R.id.bag_layout);
            List<Bag> bagList = new ArrayList<>();
            for (int j = 0; j < bagLayout.getChildCount(); j++) {
                Bag bag = new Bag();
                View bagView = bagLayout.getChildAt(j);
                TextView scanBarcode = bagView.findViewById(R.id.scan_barcode);
                EditText editWeightKg = bagView.findViewById(R.id.weight_in_kg);
                EditText ediWeightGm = bagView.findViewById(R.id.weight_in_gm);

                String kgText = editWeightKg.getText().toString();
                String gmText = ediWeightGm.getText().toString();

                if (kgText.isEmpty() || gmText.isEmpty()) {
                    Toast.makeText(getContext(), R.string.invalid_weight, Toast.LENGTH_SHORT).show();
                    return false;
                } else if (scanBarcode.getText().toString().equals(getString(R.string.scan_barcode))) {
                    Toast.makeText(getContext(), R.string.invalid_barcode, Toast.LENGTH_SHORT).show();
                    return false;
                }
                int no = j + 1;
                bag.setBagsTitle("Bag " + no);
                bag.setBagsWeightKg(Integer.parseInt(kgText));
                bag.setBagsWeightGm(Integer.parseInt(gmText));
                bag.setBarCode(scanBarcode.getText().toString());
                bagList.add(bag);
                wasteStreamUpload.setBags(bagList);
            }
            wasteStreamUploadRequest.setWasteStream(wasteStreamUploadList);
        }
        Gson gson = new Gson();
        pikcupJson = gson.toJson(wasteStreamUploadRequest, WasteStreamUploadRequest.class);
        Log.d(TAG, pikcupJson);
        return true;
    }

    private boolean doTrashValidation() {
        WasteStreamUploadRequest wasteStreamUploadRequest = new WasteStreamUploadRequest();
        List<WasteStreamUpload> wasteStreamUploadList = new ArrayList<>();

        for (int i = 0; i < trashLayout.getChildCount(); i++) {
            WasteStreamUpload wasteStreamUpload = new WasteStreamUpload();
            View trashDetails = trashLayout.getChildAt(i);
            wasteStreamUpload.setWasteStreamNameId((Integer) trashDetails.getTag());
            wasteStreamUpload.setWasteStreamName("Trash");
            wasteStreamUploadList.add(wasteStreamUpload);

            LinearLayout containerLayout = trashDetails.findViewById(R.id.layout_container);
            List<ContainerRequest> containerRequestList = new ArrayList<>();
            for (int j = 0; j < containerLayout.getChildCount(); j++) {
                ContainerRequest containerRequest = new ContainerRequest();
                View trashBin = containerLayout.getChildAt(j);
                TextView containerName = trashBin.findViewById(R.id.container_name);
                EditText editContainerQuantity = trashBin.findViewById(R.id.container_quantity);
                String quantityText = editContainerQuantity.getText().toString();
                if (quantityText.isEmpty()) {
                    String invalidContainer = getString(R.string.invalid_trash_container, containerName.getText().toString());
                    Toast.makeText(getContext(), invalidContainer, Toast.LENGTH_SHORT).show();
                    return false;
                }
                containerRequest.setId((Integer) trashBin.getTag());
                containerRequest.setContainerName(containerName.getText().toString());
                containerRequest.setContainerQuantity(Integer.parseInt(quantityText));
                containerRequestList.add(containerRequest);
                wasteStreamUpload.setContainer(containerRequestList);
            }
            wasteStreamUploadRequest.setWasteStream(wasteStreamUploadList);
            Gson gson = new Gson();
            pikcupJson = gson.toJson(wasteStreamUploadRequest, WasteStreamUploadRequest.class);
            Log.d(TAG, pikcupJson);
        }
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_BAR_CODE && resultCode == getActivity().RESULT_OK) {
            String barcode = data.getStringExtra(Constants.KEY_BARCODE);
            textViewScanned.setText(barcode);
            textViewScanned.setPaintFlags(textViewScanned.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        }
    }
}
