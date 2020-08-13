package com.mottainai.driver.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.request.target.Target;
import com.google.gson.Gson;
import com.mottainai.driver.BaseActivity;
import com.mottainai.driver.DashboardActivity;
import com.mottainai.driver.R;
import com.mottainai.driver.data.model.home.WasteStreamUploadRequest;
import com.mottainai.driver.ui.caselog.CaseLogListAdapter;
import com.mottainai.driver.ui.dialogs.CustomProgressBar;
import com.mottainai.driver.utils.Constants;

import java.util.List;

public class PreviewPickupDetailsFragment extends Fragment implements View.OnClickListener {

    private ImageView imageCustomerImage;
    private TextView textCustomerName, textCustomerAddress;
    private RecyclerView recyclerView;
    private Button complete;
    private PreviewPickupListAdapter previewPickupListAdapter;
    private CustomProgressBar progressBar;

    private String customerImage, customerName, customerAddress;
    private WasteStreamUploadRequest wasteStreamUploadRequest;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_preview_pickup, container, false);
        init(view);
        return view;
    }

    private void init(View rootView) {
        getDataFromLastScreen();
        ((BaseActivity)getActivity()).setToolbarTitle(getString(R.string.preview_pickup_details_title));
        imageCustomerImage = rootView.findViewById(R.id.customer_image);
        textCustomerName = rootView.findViewById(R.id.customer_name);
        textCustomerAddress = rootView.findViewById(R.id.customer_address);
        recyclerView = rootView.findViewById(R.id.recycler_view);
        textCustomerName.setText(customerName);
        textCustomerAddress.setText(customerAddress);

       /* Glide.with(PreviewPickupDetailsFragment.this)
                .load(mcustomerprofile.getCustomerprofile().getImage().toString())
                .override(Target.SIZE_ORIGINAL)
                .thumbnail(0.25f)
                .format(DecodeFormat.PREFER_ARGB_8888)
                .centerCrop()
                .circleCrop()
                .fitCenter()
                .into(imageCustomerImage);*/


        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        previewPickupListAdapter = new PreviewPickupListAdapter(wasteStreamUploadRequest.getWasteStream());
        recyclerView.setAdapter(previewPickupListAdapter);
        complete = rootView.findViewById(R.id.complete);
        progressBar = new CustomProgressBar();

        complete.setOnClickListener(this);
      
    }

    private void getDataFromLastScreen() {
        Bundle bundle = getArguments();
        customerImage = bundle.getString(Constants.KEY_CUSTOMER_IMAGE);
        customerName = bundle.getString(Constants.KEY_CUSTOMER_NAME);
        customerAddress = bundle.getString(Constants.KEY_CUSTOMER_ADDRESS);
        String pickupJson = bundle.getString(Constants.KEY_PICKUP_JSON);
        wasteStreamUploadRequest = new Gson().fromJson(pickupJson, WasteStreamUploadRequest.class);
    }

    @Override
    public void onClick(View view) {

    }
}
