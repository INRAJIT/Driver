package com.mottainai.driver;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.mottainai.driver.ui.login.LoginActivity;
import com.mottainai.driver.utils.PrefManager;

public class SplashActivity extends AppCompatActivity {

    private static final String TAG = SplashActivity.class.getName();
    private PrefManager prefManager;
    private Handler mHandler = new Handler();

    private static final int SPLASH_TIME = 3000;
    private static final int REQUEST_PEMISSION = 198;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        prefManager = new PrefManager(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkPermissionStatus();
        } else {
            startNextScreen();
        }
    }

    private void startNextScreen() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (prefManager.isDriverLoggedIn()) {
                    Intent mainIntent = new Intent(SplashActivity.this, DashboardActivity.class);
                    startActivity(mainIntent);
                    finish();
                } else {
                    Intent mainIntent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(mainIntent);
                    finish();
                }
            }
        }, SPLASH_TIME);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void checkPermissionStatus() {
        // called in a standard activity, use  ContextCompat.checkSelfPermission for AppCompActivity

        int writeExternalStoragePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int locationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);

        if (writeExternalStoragePermission != PackageManager.PERMISSION_GRANTED ||
                locationPermission != PackageManager.PERMISSION_GRANTED) {
            // User may have declined earlier, ask Android if we should show him a reason
            // if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            // show an explanation to the user
            // Good practise: don't block thread after the user sees the explanation, try again to request the permission.
            //} else {
            // request the permission.
            // CALLBACK_NUMBER is a integer constants
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CAMERA}, REQUEST_PEMISSION);
            // The callback method gets the result of the request.
            // }
        } else {
            // got permission use it
            startNextScreen();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PEMISSION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length == 3 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED && grantResults[2] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, do your work....
                    startNextScreen();
                } else {
                    // permission denied
                    // Disable the functionality that depends on this permission.
                    finish();
                }
                return;
            }
        }
    }

}