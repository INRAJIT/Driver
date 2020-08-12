package com.mottainai.driver.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.mottainai.driver.data.model.login.Driver;

/**
 * Class for Shared Preference
 */
public class PrefManager {

    private static final String PREF_NAME = "MottainaiDriverSharedPref";
    public static final String DEVICE_TOKEN = "device_token";
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String PHONE = "phone";
    public static final String EMAIL = "email";
    public static final String LICENCE_NO = "licence_no";
    public static final String DOB = "dob";
    public static final String IMAGE = "image";
    public static final String DRIVER_STATUS = "driver_status";
  //  public static final String PICKUP_ID = "pickup_id";

    private Context context;
    private SharedPreferences sharedPreferences;

    public PrefManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void saveDriverDetails(Driver driver) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(ID, driver.getId());
        editor.putString(NAME, driver.getName());
        editor.putString(PHONE, driver.getContactNo());
        editor.putString(EMAIL, driver.getEmail());
        editor.putString(LICENCE_NO, driver.getDrivingLicenseNumber());
        editor.putString(DOB, driver.getDOB());
        editor.putString(IMAGE, driver.getImage());
       // editor.putString(PICKUP_ID, driver.getPickup_id());
        editor.commit();
    }

    public void setSharedPrefValue(String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public String getSharedPrefValue(String key) {
        return sharedPreferences.getString(key, "");
    }

    public void setSharedPrefValue(String key, int value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public int getSharedPrefIntValue(String key) {
        return sharedPreferences.getInt(key, 1);
    }

    public boolean isDriverLoggedIn() {
        return (sharedPreferences.getString(ID, null) != null);
    }

    public void clearPreferences() {
       sharedPreferences.edit().clear().commit();
    }
}