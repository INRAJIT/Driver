package com.mottainai.driver.ui.signup;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.mottainai.driver.R;

public class SelectTypeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_select_type, container, false);
        init(view);
        return view;
    }

    private void init(View rootView) {
        CardView individual = rootView.findViewById(R.id.individual);
        CardView company = rootView.findViewById(R.id.company);

        individual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((SignupActivity)getActivity()).replaceFragment(new IndividualDetailsFragment(),
                        IndividualDetailsFragment.class.getName());
            }
        });

        company.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((SignupActivity)getActivity()).replaceFragment(new CompanyDetailsFragment(),
                        CompanyDetailsFragment.class.getName());
            }
        });

    }
}
