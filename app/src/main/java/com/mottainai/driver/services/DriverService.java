package com.mottainai.driver.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;

import com.mottainai.driver.R;

import static androidx.core.app.NotificationCompat.PRIORITY_DEFAULT;
import static androidx.core.app.NotificationCompat.PRIORITY_HIGH;

public class DriverService extends Service {

    private DriverServiceBinder driverServiceBinder;
    private static final int NOTIFICATION_ID = 199;

    public DriverService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        startForeground();
        driverServiceBinder = new DriverServiceBinder(this);
    }

    private void startForeground() {
        String CHANNEL_ID = DriverService.class.getName();
        String CHANNEL_NAME = DriverService.class.getName();

        NotificationChannel channel = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            channel = new NotificationChannel(CHANNEL_ID,
                    CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel(channel);
        }

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setCategory(Notification.CATEGORY_SERVICE)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(getString(R.string.driver_status_online))
                .setPriority(PRIORITY_DEFAULT)
                .build();
        startForeground(NOTIFICATION_ID, notification);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return driverServiceBinder;
    }

    @Override
    public void onDestroy() {
        driverServiceBinder.onDestroy();
        super.onDestroy();
    }
}
