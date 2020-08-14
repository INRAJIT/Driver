package com.mottainai.driver;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.mottainai.driver.mlkit.common.preference.SettingsActivity;
import com.mottainai.driver.mlkit.java.LivePreviewActivity;
import com.mottainai.driver.services.DriverService;
import com.mottainai.driver.services.DriverServiceBinderInterface;
import com.mottainai.driver.services.DriverServiceListener;
import com.mottainai.driver.ui.home.HomeFragment;
import com.mottainai.driver.ui.profile.ProfileActivity;
import com.mottainai.driver.ui.status.SelectStatusActivity;
import com.mottainai.driver.utils.Constants;
import com.mottainai.driver.utils.ImageLoaderUtils;
import com.mottainai.driver.utils.PrefManager;

public class DashboardActivity extends AppCompatActivity implements View.OnClickListener, ServiceConnection, DriverServiceListener, DashBoardInterface {

    private AppBarConfiguration mAppBarConfiguration;
    private DrawerLayout drawer;
    private LinearLayout selectStatus;
    private TextView textViewStatus;
    private ImageView statusIndicator;
    private DriverServiceBinderInterface driverServiceBinderInterface;
    private boolean isServiceBound;
    private HomeFragment homeFragment;

    public DashBoardViewModel dashBoardViewModel;
    private MediaPlayer player;
    public String driverId;
    private double latitude, longitude;
    private static final int REQUEST_SELECT_STATUS = 999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        init();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.nav_home);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void init() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        initNavHeader(navigationView, drawer);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_notification, R.id.nav_pickup_history, R.id.nav_case_log, R.id.nav_logout)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        // starting driver-service
        startDriverService();

        // get DashBoard View Model
        dashBoardViewModel = new ViewModelProvider(this).get(DashBoardViewModel.class);
    }

    private void initNavHeader(NavigationView navigationView, final DrawerLayout drawer) {
        PrefManager prefManager = new PrefManager(this);

        View header = navigationView.getHeaderView(0);
        ImageView profileImage = header.findViewById(R.id.profileImage);
        TextView name = header.findViewById(R.id.name);
        TextView email = header.findViewById(R.id.email);
        TextView licenceNo = header.findViewById(R.id.license_no);
        TextView profile = header.findViewById(R.id.profile);
        selectStatus = header.findViewById(R.id.select_status);
        statusIndicator = header.findViewById(R.id.status_indicator);
        textViewStatus = header.findViewById(R.id.status);
        profile.setOnClickListener(this);
        selectStatus.setOnClickListener(this);

        driverId = prefManager.getSharedPrefValue(PrefManager.ID);
        name.setText(prefManager.getSharedPrefValue(PrefManager.NAME));
        email.setText(prefManager.getSharedPrefValue(PrefManager.EMAIL));
        licenceNo.setText(prefManager.getSharedPrefValue(PrefManager.LICENCE_NO));

        updateDriverStatus(prefManager.getSharedPrefIntValue(PrefManager.DRIVER_STATUS));
        ImageLoaderUtils.getInstance().updateProfileImage(this, profileImage, prefManager.getSharedPrefValue(PrefManager.IMAGE));

        drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
                updateDriverStatus(prefManager.getSharedPrefIntValue(PrefManager.DRIVER_STATUS));
            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.profile) {
            drawer.closeDrawer(GravityCompat.START);
            Intent intent = new Intent(DashboardActivity.this, ProfileActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.select_status) {
            drawer.closeDrawer(GravityCompat.START);
            Intent intent = new Intent(DashboardActivity.this, SelectStatusActivity.class);
            startActivityForResult(intent, REQUEST_SELECT_STATUS);
        }
    }

    private void startDriverService() {
        Intent intent = new Intent(this, DriverService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent);
        } else {
            startService(intent);
        }
        bindService(intent, this, BIND_AUTO_CREATE);
    }

    private void stopDriverService() {
        Intent intent = new Intent(this, DriverService.class);
        stopService(intent);
        unbindService(this);
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        isServiceBound = true;
        driverServiceBinderInterface = (DriverServiceBinderInterface) service;
        driverServiceBinderInterface.setListener(this);
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        isServiceBound = false;
        driverServiceBinderInterface.removeListener();
    }

    @Override
    public void setHomeFragmentInterface(HomeFragment homeFragment) {
        this.homeFragment = homeFragment;
        if(latitude != 0.0 && longitude !=0.0) {
            onDriverLocationChanged(latitude, longitude);
        }
    }

    @Override
    public void onDriverLocationChanged(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        if (homeFragment != null) {
            homeFragment.updateDriverLocationOnMap(latitude, longitude);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_SELECT_STATUS && resultCode == RESULT_OK) {
            int status = data.getExtras().getInt(Constants.DRIVER_STATUS);
            updateDriverStatus(status);
        }
    }

    private void updateDriverStatus(int status) {
        if (status == Constants.STATUS_READY) {
            textViewStatus.setText(R.string.status_ready);
            statusIndicator.setBackground(getDrawable(R.drawable.status_indicator_green));
        } else if (status == Constants.STATUS_TRUCK_FULL) {
            textViewStatus.setText(R.string.status_truck_full);
            statusIndicator.setBackground(getDrawable(R.drawable.status_indiactor_light_red));
        } else if (status == Constants.STATUS_ON_BREAK) {
            textViewStatus.setText(R.string.status_on_break);
            statusIndicator.setBackground(getDrawable(R.drawable.status_indicator_yellow));
        } else if (status == Constants.STATUS_BUSY) {
            textViewStatus.setText(R.string.status_busy);
            statusIndicator.setBackground(getDrawable(R.drawable.status_indicator_red));
        }
    }

    @Override
    protected void onDestroy() {
        stopDriverService();
        stopRingTone();
        super.onDestroy();
    }

    //TODO - Need to remove this code
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dashboard_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
       // **for Settings in dashboard hidden
        
         if (item.getItemId() == R.id.settings) {
            Intent intent = new Intent(this, LivePreviewActivity.class); /// Nope
            startActivity(intent);  // Nope
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void playDefaultRingTone() {
        if (player == null) {
            player = MediaPlayer.create(this,
                    Settings.System.DEFAULT_RINGTONE_URI);
        }
        if(!player.isPlaying()) {
            player.start();
        }
    }

    public void stopRingTone() {
        if (player != null && player.isPlaying()) {
            player.stop();
        }
    }

}
