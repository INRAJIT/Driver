package com.mottainai.driver.ui.dialogs;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.mottainai.driver.R;
import com.mottainai.driver.data.model.caselog.CaseCategory;
import com.mottainai.driver.data.model.signup.Country;
import com.mottainai.driver.data.model.signup.StateCity;
import com.mottainai.driver.data.model.signup.VehicleType;
import com.mottainai.driver.utils.EnumConstants;

import java.util.ArrayList;

import static com.mottainai.driver.ui.dialogs.DialogViewHolder.INPUT_DATA;
import static com.mottainai.driver.ui.dialogs.DialogViewHolder.INPUT_TYPE;
import static com.mottainai.driver.ui.dialogs.DialogViewHolder.SELECTED_ROW_DATA;
import static com.mottainai.driver.ui.dialogs.DialogViewHolder.SELECTED_ROW_POSITION;
import static com.mottainai.driver.ui.dialogs.DialogViewHolder.SIMPLE_TEXT_VIEW_LAYOUT;
import static com.mottainai.driver.ui.dialogs.DialogViewHolder.TYPE_CASE_CATEGORY;
import static com.mottainai.driver.ui.dialogs.DialogViewHolder.TYPE_COUNTRY;
import static com.mottainai.driver.ui.dialogs.DialogViewHolder.TYPE_MONTH;
import static com.mottainai.driver.ui.dialogs.DialogViewHolder.TYPE_STATE_CITY;
import static com.mottainai.driver.ui.dialogs.DialogViewHolder.TYPE_VEHICLE_TYPES;

public class SimpleRecyclerDialogFragment extends CustomDialogFragment {

    private ArrayList<Country> countryList;
    private ArrayList<StateCity> stateCityList;
    private ArrayList<VehicleType> vehicleTypeList;
    private ArrayList<CaseCategory> caseCategoryList;
    private ArrayList<String> simpleList;
    private DialogRecyclerAdapter adapter;

    public static SimpleRecyclerDialogFragment newInstance(EnumConstants.TransactionType requestedScreen,
                                                           EnumConstants.ParentComponentType parentComponentType,
                                                           Bundle bundle) {
        SimpleRecyclerDialogFragment dialogFragment = new SimpleRecyclerDialogFragment();
        dialogFragment.requestedScreen = requestedScreen;
        dialogFragment.parentComponentType = parentComponentType;
        dialogFragment.setArguments(bundle);
        return dialogFragment;
    }

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return onCreateInit(R.layout.dialog_recycler);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView = bindFragmentWidgets(R.id.recyclerView);
        initRecyclerView(true);
        setupRecyclerData();
    }

    private void setupRecyclerData() {
        Bundle bundle = getArguments();
        String type = bundle.getString(INPUT_TYPE);
        if(type.equals(TYPE_STATE_CITY)) {
            stateCityList = bundle.getParcelableArrayList(INPUT_DATA);
            initStateCityAdapter();
        } else if(type.equals(TYPE_COUNTRY)){
            countryList = bundle.getParcelableArrayList(INPUT_DATA);
            initCountryAdapter();
        } else if(type.equals(TYPE_VEHICLE_TYPES)) {
            vehicleTypeList = bundle.getParcelableArrayList(INPUT_DATA);
            initVehicleTypeAdapter();
        } else if(type.equals(TYPE_CASE_CATEGORY)) {
            caseCategoryList = bundle.getParcelableArrayList(INPUT_DATA);
            initCaseCategoryListAdapter();
        } else if(type.equals(TYPE_MONTH)) {
            simpleList = bundle.getStringArrayList(INPUT_DATA);
            initSimpleListAdapter();
        }
    }

    private void initCountryAdapter() {
        adapter = new DialogRecyclerAdapter<Country>(countryList) {
            @Override
            public int getLayoutResId() {
                return SIMPLE_TEXT_VIEW_LAYOUT;
            }

            @Override
            public RecyclerView.ViewHolder setViewHolder(View view) {
                return new DialogViewHolder.SimpleTextView(view);
            }

            @Override
            public void onBindData(Country model, int position, RecyclerView.ViewHolder dataBinding) {
                DialogViewHolder.SimpleTextView holder = (DialogViewHolder.SimpleTextView) dataBinding;
                holder.title.setText(model.getCountryName());
            }

            @Override
            public void onItemClick(Country model, int position) {
                Bundle bundle = new Bundle();
                bundle.putParcelable(SELECTED_ROW_DATA, model);
                dialogFragmentListener.onDialogFragmentListItemClick(bundle, requestedScreen);
                SimpleRecyclerDialogFragment.this.dismiss();
            }
        };
        recyclerView.setAdapter(adapter);
    }

    private void initStateCityAdapter() {
        adapter = new DialogRecyclerAdapter<StateCity>(stateCityList) {
            @Override
            public int getLayoutResId() {
                return SIMPLE_TEXT_VIEW_LAYOUT;
            }

            @Override
            public RecyclerView.ViewHolder setViewHolder(View view) {
                return new DialogViewHolder.SimpleTextView(view);
            }

            @Override
            public void onBindData(StateCity model, int position, RecyclerView.ViewHolder dataBinding) {
                DialogViewHolder.SimpleTextView holder = (DialogViewHolder.SimpleTextView) dataBinding;
                holder.title.setText(model.getName());
            }

            @Override
            public void onItemClick(StateCity model, int position) {
                Bundle bundle = new Bundle();
                bundle.putParcelable(SELECTED_ROW_DATA, model);
                dialogFragmentListener.onDialogFragmentListItemClick(bundle, requestedScreen);
                SimpleRecyclerDialogFragment.this.dismiss();
            }
        };
        recyclerView.setAdapter(adapter);
    }

    private void initVehicleTypeAdapter() {
        adapter = new DialogRecyclerAdapter<VehicleType>(vehicleTypeList) {
            @Override
            public int getLayoutResId() {
                return SIMPLE_TEXT_VIEW_LAYOUT;
            }

            @Override
            public RecyclerView.ViewHolder setViewHolder(View view) {
                return new DialogViewHolder.SimpleTextView(view);
            }

            @Override
            public void onBindData(VehicleType model, int position, RecyclerView.ViewHolder dataBinding) {
                DialogViewHolder.SimpleTextView holder = (DialogViewHolder.SimpleTextView) dataBinding;
                holder.title.setText(model.getVehicleTypeName());
            }

            @Override
            public void onItemClick(VehicleType model, int position) {
                Bundle bundle = new Bundle();
                bundle.putParcelable(SELECTED_ROW_DATA, model);
                dialogFragmentListener.onDialogFragmentListItemClick(bundle, requestedScreen);
                SimpleRecyclerDialogFragment.this.dismiss();
            }
        };
        recyclerView.setAdapter(adapter);
    }

    private void initCaseCategoryListAdapter() {
        adapter = new DialogRecyclerAdapter<CaseCategory>(caseCategoryList) {
            @Override
            public int getLayoutResId() {
                return SIMPLE_TEXT_VIEW_LAYOUT;
            }

            @Override
            public RecyclerView.ViewHolder setViewHolder(View view) {
                return new DialogViewHolder.SimpleTextView(view);
            }

            @Override
            public void onBindData(CaseCategory model, int position, RecyclerView.ViewHolder dataBinding) {
                DialogViewHolder.SimpleTextView holder = (DialogViewHolder.SimpleTextView) dataBinding;
                holder.title.setText(model.getCategory());
            }

            @Override
            public void onItemClick(CaseCategory model, int position) {
                Bundle bundle = new Bundle();
                bundle.putParcelable(SELECTED_ROW_DATA, model);
                dialogFragmentListener.onDialogFragmentListItemClick(bundle, requestedScreen);
                SimpleRecyclerDialogFragment.this.dismiss();
            }
        };
        recyclerView.setAdapter(adapter);
    }

    private void initSimpleListAdapter() {
        adapter = new DialogRecyclerAdapter<String>(simpleList) {
            @Override
            public int getLayoutResId() {
                return SIMPLE_TEXT_VIEW_LAYOUT;
            }

            @Override
            public RecyclerView.ViewHolder setViewHolder(View view) {
                return new DialogViewHolder.SimpleTextView(view);
            }

            @Override
            public void onBindData(String name, int position, RecyclerView.ViewHolder dataBinding) {
                DialogViewHolder.SimpleTextView holder = (DialogViewHolder.SimpleTextView) dataBinding;
                holder.title.setText(name);
            }

            @Override
            public void onItemClick(String name, int position) {
                Bundle bundle = new Bundle();
                bundle.putString(SELECTED_ROW_DATA, name);
                bundle.putInt(SELECTED_ROW_POSITION, position);
                dialogFragmentListener.onDialogFragmentListItemClick(bundle, requestedScreen);
                SimpleRecyclerDialogFragment.this.dismiss();
            }
        };
        recyclerView.setAdapter(adapter);
    }

}
