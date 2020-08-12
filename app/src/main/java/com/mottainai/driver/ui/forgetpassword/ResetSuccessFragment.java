package com.mottainai.driver.ui.forgetpassword;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mottainai.driver.BaseActivity;
import com.mottainai.driver.R;
import com.mottainai.driver.ui.login.LoginActivity;
import com.mottainai.driver.utils.PrefManager;

public class ResetSuccessFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reset_success, container, false);
        init(view);
        return view;
    }

    private void init(View rootView) {
        ((BaseActivity)getActivity()).hideToolBar();

        final Button login = rootView.findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Reset All Preference Data
                PrefManager prefManager = new PrefManager(getContext());
                prefManager.clearPreferences();

                Intent intent = new Intent(getActivity(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                getActivity().finish();
            }
        });
    }
}
