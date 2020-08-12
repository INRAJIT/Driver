package com.mottainai.driver.ui.pickuphistory;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mottainai.driver.R;
import com.mottainai.driver.data.model.pickuphistory.history.PickupHistory;
import com.mottainai.driver.ui.dialogs.CustomProgressBar;
import com.mottainai.driver.ui.dialogs.DateAndTimePickerDialogs;
import com.mottainai.driver.ui.dialogs.DateTimeSetListener;
import com.mottainai.driver.ui.dialogs.DialogFragmentListener;
import com.mottainai.driver.ui.dialogs.SimpleRecyclerDialogFragment;
import com.mottainai.driver.utils.EnumConstants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.mottainai.driver.ui.dialogs.DialogViewHolder.INPUT_DATA;
import static com.mottainai.driver.ui.dialogs.DialogViewHolder.INPUT_TYPE;
import static com.mottainai.driver.ui.dialogs.DialogViewHolder.SELECTED_ROW_DATA;
import static com.mottainai.driver.ui.dialogs.DialogViewHolder.SELECTED_ROW_POSITION;
import static com.mottainai.driver.ui.dialogs.DialogViewHolder.TYPE_MONTH;

public class PickupHistoryFragment extends Fragment implements View.OnClickListener, DateTimeSetListener {

    private LinearLayout selectDate;
    private TextView textDate;
    private RecyclerView recyclerViewPickupHistory;
    private PickupHistoryAdapter pickupHistoryAdapter;
    private List<PickupHistory> pickupHistoryList = new ArrayList<>();
    private CustomProgressBar progressBar;

    private PickupHistoryActivity pickupHistoryActivity;
    private DateAndTimePickerDialogs dateAndTimePickerDialogs;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        pickupHistoryActivity = ((PickupHistoryActivity) context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pickup_history, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View rootView) {
        selectDate = rootView.findViewById(R.id.select_date);
        textDate = rootView.findViewById(R.id.text_date);
        recyclerViewPickupHistory = rootView.findViewById(R.id.recycler_view_pickup_history);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerViewPickupHistory.setLayoutManager(layoutManager);
        pickupHistoryAdapter = new PickupHistoryAdapter(pickupHistoryList);
        recyclerViewPickupHistory.setAdapter(pickupHistoryAdapter);
        progressBar = new CustomProgressBar();
        dateAndTimePickerDialogs = new DateAndTimePickerDialogs(getContext(), this);
        selectDate.setOnClickListener(this);

        pickupHistoryActivity.pickupHistoryViewModel.getPickupHistoryResponse().observe(getViewLifecycleOwner(), pickupHistoryResponse -> {
            progressBar.dismiss();
            pickupHistoryList.clear();
            if(pickupHistoryResponse.getStatus()) {
                pickupHistoryList.addAll(pickupHistoryResponse.getResponse());
            } else {
                Toast.makeText(getContext(), pickupHistoryResponse.getMessage(), Toast.LENGTH_SHORT).show();
            }
            pickupHistoryAdapter.notifyDataSetChanged();
        });
    }

    @Override
    public void onClick(View v) {
        if(v == selectDate) {
            dateAndTimePickerDialogs.showDatePickerDialog(false);
        }
    }

    private void getPickupHistory(String date) {
        progressBar.show(getContext(), getString(R.string.progress_dialog_title));
        pickupHistoryActivity.pickupHistoryViewModel.getPickupHistory(pickupHistoryActivity.driverId, 1, "C", date);
    }

    @Override
    public void onDateSet(String date) {
        textDate.setText(date);
        getPickupHistory(date);
    }

    @Override
    public void onTimeSet(String time) {

    }
}