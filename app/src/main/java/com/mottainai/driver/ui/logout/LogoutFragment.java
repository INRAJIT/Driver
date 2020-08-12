package com.mottainai.driver.ui.logout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.mottainai.driver.DashBoardInterface;
import com.mottainai.driver.DashboardActivity;
import com.mottainai.driver.R;
import com.mottainai.driver.ui.dialogs.CustomProgressBar;
import com.mottainai.driver.ui.login.LoginActivity;
import com.mottainai.driver.utils.PrefManager;

import java.io.IOException;

public class LogoutFragment extends Fragment implements View.OnClickListener {

    private DashboardActivity dashboardActivity;
    private Button logout;
    private PrefManager prefManager;
    private CustomProgressBar progressBar;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        dashboardActivity = (DashboardActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_logout, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        logout = view.findViewById(R.id.logout);
        progressBar = new CustomProgressBar();
        prefManager = new PrefManager(getContext());
        logout.setOnClickListener(this);

        dashboardActivity.dashBoardViewModel.getDriverLogoutResponse().observe(getViewLifecycleOwner(), driverLogoutResponse -> {
            progressBar.dialog.dismiss();
            Toast.makeText(getContext(), driverLogoutResponse.getMessage(), Toast.LENGTH_SHORT).show();
            if (driverLogoutResponse.getStatus()) {
                deleteToken();
                prefManager.clearPreferences();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
    }

    @Override
    public void onClick(View view) {
        if(view == logout) {
            progressBar.show(getContext(), getString(R.string.progress_dialog_title));
            dashboardActivity.dashBoardViewModel.driverLogout(dashboardActivity.driverId);
        }
    }

    private void deleteToken() {
        try {
            FirebaseInstanceId.getInstance().deleteInstanceId();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}