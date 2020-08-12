package com.mottainai.driver.ui.signup;

import android.os.Bundle;
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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;

import com.mottainai.driver.R;
import com.mottainai.driver.data.model.signup.CityResponse;
import com.mottainai.driver.data.model.signup.Country;
import com.mottainai.driver.data.model.signup.CountryResponse;
import com.mottainai.driver.data.model.signup.SignupResponse;
import com.mottainai.driver.data.model.signup.StateCity;
import com.mottainai.driver.data.model.signup.StateResponse;
import com.mottainai.driver.ui.dialogs.CustomProgressBar;
import com.mottainai.driver.ui.dialogs.DialogFragmentListener;
import com.mottainai.driver.ui.dialogs.SimpleRecyclerDialogFragment;
import com.mottainai.driver.utils.EnumConstants;

import java.util.ArrayList;

import static com.mottainai.driver.ui.dialogs.DialogViewHolder.INPUT_DATA;
import static com.mottainai.driver.ui.dialogs.DialogViewHolder.INPUT_TYPE;
import static com.mottainai.driver.ui.dialogs.DialogViewHolder.SELECTED_ROW_DATA;
import static com.mottainai.driver.ui.dialogs.DialogViewHolder.TYPE_COUNTRY;
import static com.mottainai.driver.ui.dialogs.DialogViewHolder.TYPE_STATE_CITY;

public class IndividualDetailsFragment extends Fragment implements View.OnClickListener, DialogFragmentListener {

    private AppCompatEditText editFirstName;
    private AppCompatEditText editMiddleName;
    private AppCompatEditText editLastName;
    private AppCompatEditText editPhone;
    private AppCompatEditText editAddressLineOne;
    private AppCompatEditText editAddressLineTwo;
    private AppCompatEditText editPincode;
    LinearLayout selectCountry, selectState, selectCity;
    TextView textViewCountry, textViewState, textViewCity;
    private Button next;
    private CustomProgressBar progressBar;

    private String firstName, middleName, lastName, phone, addressLineOne, addressLineTwo, pincode, country, state, city;
    private ArrayList<Country> countryList = new ArrayList<>();
    private ArrayList<StateCity> stateList = new ArrayList<>();
    private ArrayList<StateCity> cityList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_individual_details, container, false);
        init(view);
        return view;
    }

    private void init(View rootView) {
        editFirstName = rootView.findViewById(R.id.edit_first_name);
        editMiddleName = rootView.findViewById(R.id.edit_middle_name);
        editLastName = rootView.findViewById(R.id.edit_last_name);
        editPhone = rootView.findViewById(R.id.edit_phone);
        editAddressLineOne = rootView.findViewById(R.id.edit_address_line_one);
        editAddressLineTwo = rootView.findViewById(R.id.edit_address_line_two);
        editPincode = rootView.findViewById(R.id.edit_pincode);
        selectCountry = rootView.findViewById(R.id.select_country);
        selectState = rootView.findViewById(R.id.select_state);
        selectCity = rootView.findViewById(R.id.select_city);
        textViewCountry = rootView.findViewById(R.id.text_country);
        textViewState = rootView.findViewById(R.id.text_state);
        textViewCity = rootView.findViewById(R.id.text_city);
        next = rootView.findViewById(R.id.next);
        next.setOnClickListener(this);
        selectCountry.setOnClickListener(this);
        selectState.setOnClickListener(this);
        selectCity.setOnClickListener(this);
        progressBar = new CustomProgressBar();

        // get Country List
        getCountryList();

        ((SignupActivity) getActivity()).signupViewModel.getCountryResponse().observe(getViewLifecycleOwner(), new Observer<CountryResponse>() {
            @Override
            public void onChanged(CountryResponse countryResponse) {
                progressBar.dialog.dismiss();
                countryList = (ArrayList<Country>) countryResponse.getCountries();
            }
        });

        ((SignupActivity) getActivity()).signupViewModel.getStateResponse().observe(getViewLifecycleOwner(), new Observer<StateResponse>() {
            @Override
            public void onChanged(StateResponse stateResponse) {
                progressBar.dialog.dismiss();
                stateList = (ArrayList<StateCity>) stateResponse.getStates();
            }
        });

        ((SignupActivity) getActivity()).signupViewModel.getCityResponse().observe(getViewLifecycleOwner(), new Observer<CityResponse>() {
            @Override
            public void onChanged(CityResponse cityResponse) {
                progressBar.dialog.dismiss();
                cityList = (ArrayList<StateCity>) cityResponse.getCities();
            }
        });

        ((SignupActivity) getActivity()).signupViewModel.getSignupFormState().observe(getViewLifecycleOwner(), new Observer<SignupFormState>() {
            @Override
            public void onChanged(SignupFormState signupFormState) {
                if (signupFormState.isDataValid()) {
                    progressBar.show(getActivity(), getString(R.string.progress_dialog_title));
                    ((SignupActivity) getActivity()).signupViewModel.signupIndividualDetails(
                            "1", firstName, middleName, lastName, phone,
                            addressLineOne + " " + addressLineTwo, country, state, city, pincode);
                } else {
                    Toast.makeText(getActivity(), signupFormState.getError(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        ((SignupActivity) getActivity()).signupViewModel.getSignupResponse().observe(getViewLifecycleOwner(), new Observer<SignupResponse>() {
            @Override
            public void onChanged(SignupResponse signupResponse) {
                progressBar.dialog.dismiss();
                if (signupResponse.getStatus()) {
                    Toast.makeText(getActivity(), "Account Id: " + signupResponse.getAccountId(), Toast.LENGTH_SHORT).show();

                    Bundle bundle = new Bundle();
                    bundle.putString("accountId", signupResponse.getAccountId());
                    bundle.putString("phone", phone);

                    PhoneVerificationFragment phoneVerificationFragment = new PhoneVerificationFragment();
                    phoneVerificationFragment.setArguments(bundle);
                    ((SignupActivity) getActivity()).replaceFragment(phoneVerificationFragment,
                            PhoneVerificationFragment.class.getName());
                } else {
                    Toast.makeText(getActivity(), signupResponse.getMessage(), Toast.LENGTH_SHORT).show();
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
            phone = editPhone.getText().toString();
            addressLineOne = editAddressLineOne.getText().toString();
            addressLineTwo = editAddressLineTwo.getText().toString();
            pincode = editPincode.getText().toString();

            ((SignupActivity) getActivity()).signupViewModel.signupIndividualDataChanged(firstName, middleName,
                    lastName, phone, addressLineOne, addressLineTwo, pincode, country, state, city);
        } else if (v == selectCountry) {
            showCountryListInDialog(countryList, this, EnumConstants.TransactionType.SELECT_COUNTRY);
        } else if (v == selectState) {
            showListInDialog(stateList, this, EnumConstants.TransactionType.SELECT_STATE);
        } else if (v == selectCity) {
            showListInDialog(cityList, this, EnumConstants.TransactionType.SELECT_CITY);
        }
    }

    protected void showCountryListInDialog(ArrayList<Country> optionList, Fragment sourceFragment,
                                    EnumConstants.TransactionType transactionScreen) {
        Bundle bundle = new Bundle();
        bundle.putString(INPUT_TYPE, TYPE_COUNTRY);
        bundle.putParcelableArrayList(INPUT_DATA, optionList);
        FragmentManager fm = getActivity().getSupportFragmentManager();
        SimpleRecyclerDialogFragment optionAlertFragment =
                SimpleRecyclerDialogFragment.newInstance(transactionScreen,
                        EnumConstants.ParentComponentType.FRAGMENT,
                        bundle);
        optionAlertFragment.setTargetFragment(sourceFragment, 200);
        optionAlertFragment.show(fm, "fragment_edit_name");
    }

    protected void showListInDialog(ArrayList<StateCity> optionList, Fragment sourceFragment,
                                    EnumConstants.TransactionType transactionScreen) {
        Bundle bundle = new Bundle();
        bundle.putString(INPUT_TYPE, TYPE_STATE_CITY);
        bundle.putParcelableArrayList(INPUT_DATA, optionList);
        FragmentManager fm = getActivity().getSupportFragmentManager();
        SimpleRecyclerDialogFragment optionAlertFragment =
                SimpleRecyclerDialogFragment.newInstance(transactionScreen,
                        EnumConstants.ParentComponentType.FRAGMENT,
                        bundle);
        optionAlertFragment.setTargetFragment(sourceFragment, 200);
        optionAlertFragment.show(fm, "fragment_edit_name");
    }

    @Override
    public void onDialogFragmentListItemClick(Bundle bundle, EnumConstants.TransactionType requestedScreen) {
        if(requestedScreen == EnumConstants.TransactionType.SELECT_COUNTRY) {
            Country selectedData = bundle.getParcelable(SELECTED_ROW_DATA);
            country = selectedData.getId();
            textViewCountry.setText(selectedData.getCountryName());
            textViewState.setText(R.string.select_state);
            textViewCity.setText(R.string.select_city);
            state = null;
            city = null;
            getStateList(country);
        } else if (requestedScreen == EnumConstants.TransactionType.SELECT_STATE) {
            StateCity selectedData = bundle.getParcelable(SELECTED_ROW_DATA);
            state = selectedData.getId();
            textViewState.setText(selectedData.getName());
            textViewCity.setText(R.string.select_city);
            city = null;
            getCityList(state);
        } else {
            StateCity selectedData = bundle.getParcelable(SELECTED_ROW_DATA);
            city = selectedData.getId();
            textViewCity.setText(selectedData.getName());
        }
    }

    private void getCountryList() {
        progressBar.show(getActivity(), getString(R.string.progress_dialog_title));
        ((SignupActivity) getActivity()).signupViewModel.getCountryList();
    }

    private void getStateList(String countryId) {
        progressBar.show(getActivity(), getString(R.string.progress_dialog_title));
        ((SignupActivity) getActivity()).signupViewModel.getStateList(countryId);
    }

    private void getCityList(String stateId) {
        progressBar.show(getActivity(), getString(R.string.progress_dialog_title));
        ((SignupActivity) getActivity()).signupViewModel.getCityList(stateId);
    }
}
