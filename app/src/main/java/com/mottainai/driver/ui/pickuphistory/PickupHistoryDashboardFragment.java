package com.mottainai.driver.ui.pickuphistory;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mottainai.driver.R;
import com.mottainai.driver.data.model.pickuphistory.dashboard.Wastestream;
import com.mottainai.driver.ui.dialogs.CustomProgressBar;
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

public class PickupHistoryDashboardFragment extends Fragment implements View.OnClickListener, DialogFragmentListener {

    private LinearLayout selectMonthYear;
    private TextView textMonthYear, textPickupList;
    private RecyclerView recyclerViewPickupDetails;
    private CustomProgressBar progressBar;

    private PickupHistoryActivity pickupHistoryActivity;
    private PickupDetailsAdapter pickupDetailsAdapter;
    private List<Wastestream> wastestreamList = new ArrayList<>();

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        pickupHistoryActivity = ((PickupHistoryActivity) context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pickup_history_dashboard, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View rootView) {
        selectMonthYear = rootView.findViewById(R.id.select_month_year);
        textMonthYear = rootView.findViewById(R.id.text_month_year);
        textPickupList = rootView.findViewById(R.id.text_pickup_list);
        recyclerViewPickupDetails = rootView.findViewById(R.id.recycler_view_pickup_details);
        recyclerViewPickupDetails.setLayoutManager(new GridLayoutManager(getContext(), 2));
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.view_right_margin_small);
        recyclerViewPickupDetails.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        pickupDetailsAdapter = new PickupDetailsAdapter(wastestreamList);
        recyclerViewPickupDetails.setAdapter(pickupDetailsAdapter);
        progressBar = new CustomProgressBar();
        selectMonthYear.setOnClickListener(this);
        textPickupList.setOnClickListener(this);

        pickupHistoryActivity.pickupHistoryViewModel.getPickupHistoryDashboardResponse().observe(getViewLifecycleOwner(), pickupHistoryResponse -> {
            progressBar.dismiss();
            wastestreamList.clear();
            if (pickupHistoryResponse.getStatus()) {
                Wastestream wastestream = new Wastestream();
                wastestream.setWasteStreamName("Pickups");
                wastestream.setQuantityinkg(pickupHistoryResponse.getResponse().get(0).getTotalPickups().toString());
                wastestreamList.add(wastestream);
                wastestreamList.addAll(pickupHistoryResponse.getResponse().get(0).getWastestream());
            } else {
                Toast.makeText(getContext(), pickupHistoryResponse.getMessage(), Toast.LENGTH_SHORT).show();
            }
            pickupDetailsAdapter.notifyDataSetChanged();
        });
    }

    @Override
    public void onClick(View v) {
        if (v == selectMonthYear) {
            String[] month = getResources().getStringArray(R.array.month);
            showMonthListInDialog(new ArrayList<>(Arrays.asList(month)), this, EnumConstants.TransactionType.SELECT_MONTH);
        } else if (v == textPickupList) {
            pickupHistoryActivity.addFragmentToStack(new PickupHistoryFragment(), PickupHistoryFragment.class.getName());
        }
    }

    protected void showMonthListInDialog(ArrayList<String> optionList, Fragment sourceFragment,
                                         EnumConstants.TransactionType transactionScreen) {
        Bundle bundle = new Bundle();
        bundle.putString(INPUT_TYPE, TYPE_MONTH);
        bundle.putStringArrayList(INPUT_DATA, optionList);
        FragmentManager fm = getParentFragmentManager();
        SimpleRecyclerDialogFragment optionAlertFragment =
                SimpleRecyclerDialogFragment.newInstance(transactionScreen,
                        EnumConstants.ParentComponentType.FRAGMENT,
                        bundle);
        optionAlertFragment.setTargetFragment(sourceFragment, 200);
        optionAlertFragment.show(fm, "fragment_edit_name");
    }

    private void getPickupHistory(int month) {
        progressBar.show(getContext(), getString(R.string.progress_dialog_title));
        pickupHistoryActivity.pickupHistoryViewModel.getPickupHistoryDashboard(pickupHistoryActivity.driverId, month, 2020);
    }

    @Override
    public void onDialogFragmentListItemClick(Bundle bundle, EnumConstants.TransactionType requestedScreen) {
        if (requestedScreen == EnumConstants.TransactionType.SELECT_MONTH) {
            String selectedData = bundle.getString(SELECTED_ROW_DATA);
            textMonthYear.setText(selectedData);
            int position = bundle.getInt(SELECTED_ROW_POSITION);
            getPickupHistory(++position);
        }
    }

    public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
        private int space;

        public SpacesItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.top = space;
        }
    }
}
