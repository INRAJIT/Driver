package com.mottainai.driver.services;

import android.content.Context;
import android.os.Binder;
import android.os.Handler;

import com.mottainai.driver.data.model.service.DriverLocation;
import com.mottainai.driver.data.model.service.Location;
import com.mottainai.driver.networking.APIInterface;
import com.mottainai.driver.networking.RetrofitService;
import com.mottainai.driver.utils.PrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DriverServiceBinder extends Binder implements DriverServiceBinderInterface {

    private DriverServiceListener driverServiceListener;
    private Context mContext;
    private PrefManager prefManager;

    private APIInterface apiInterface;
    private Handler fetchLocationHandler = new Handler();

    private static final int FETCH_LOCATION_INTERVAL = 60000;
    private String driverID;

    private Runnable fetchLocationRunnable = new Runnable() {
        @Override
        public void run() {
            getDriverCurrentLocation(driverID);
            fetchLocationHandler.postDelayed(this, FETCH_LOCATION_INTERVAL);
        }
    };

    public DriverServiceBinder(Context context) {
        this.mContext = context;
        this.apiInterface = RetrofitService.createService(APIInterface.class);
        prefManager = new PrefManager(mContext);
        driverID = prefManager.getSharedPrefValue(PrefManager.ID);
        fetchLocationHandler.post(fetchLocationRunnable);
    }

    @Override
    public void setListener(DriverServiceListener driverServiceListener) {
        this.driverServiceListener = driverServiceListener;
    }

    @Override
    public void removeListener() {
        this.driverServiceListener = null;
    }

    public void getDriverCurrentLocation(String driverId) {
        apiInterface.getDriverCurrentLocation(driverId).
                enqueue(new Callback<DriverLocation>() {
                    @Override
                    public void onResponse(Call<DriverLocation> call, Response<DriverLocation> response) {
                        if (response.isSuccessful()) {
                            Location location = response.body().getLocation();
                            if(driverServiceListener != null) {
                                driverServiceListener.onDriverLocationChanged(
                                        Double.parseDouble(location.getLat()), Double.parseDouble(location.getLong()));
                                prefManager.setSharedPrefValue(PrefManager.DRIVER_STATUS, location.getStatusCode());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<DriverLocation> call, Throwable t) {
                    }
                });
    }

    @Override
    public void onDestroy() {
        fetchLocationHandler.removeCallbacks(fetchLocationRunnable);
    }

}
