package com.mottainai.driver.ui.dialogs;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateAndTimePickerDialogs {

    private final Context mContext;
    private DateTimeSetListener dateTimeSetListener;

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public DateAndTimePickerDialogs(Context context, DateTimeSetListener dateTimeSetListener) {
        this.mContext = context;
        this.dateTimeSetListener = dateTimeSetListener;
    }

    public void showDatePickerDialog(boolean isMinDate) {
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(mContext,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(year, monthOfYear, dayOfMonth);
                        dateTimeSetListener.onDateSet(simpleDateFormat.format(calendar.getTime()));
                    }
                }, mYear, mMonth, mDay);
        if(isMinDate) {
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        } else {
            datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000);
        }
        datePickerDialog.show();
    }

    public void showTimePickerDialog() {
        // Get Current Time
        final Calendar c = Calendar.getInstance();
        int mHour = c.get(Calendar.HOUR_OF_DAY);
        int mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(mContext,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        Calendar calendar = Calendar.getInstance();
                        dateTimeSetListener.onTimeSet("");
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }
}
