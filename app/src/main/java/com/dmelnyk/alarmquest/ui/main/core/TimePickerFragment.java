package com.dmelnyk.alarmquest.ui.main.core;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import com.dmelnyk.alarmquest.model.Alarm;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by d264 on 12/19/17.
 */

public class TimePickerFragment extends BaseDialogFragment
        implements TimePickerDialog.OnTimeSetListener {

    private static final String TIME_PATTERN = "HH:mm";
    public static final String ARG_ALARM_TIME = "ARG_ALARM_TIME";
    public static final String ARG_ALARM = "ARG_ALARM";

    private SimpleDateFormat timeFormat;

    private String mPreviousTime = null;
    private Alarm mPreviousAlarm;

    private OnTimeSelectedListener callback;

    @Override
    public void setCallback(BaseDialogCallback callback) {
        this.callback = (OnTimeSelectedListener) callback;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        int hour = -1;
        int minute = -1;
        if (getArguments() == null) {
            final Calendar c = Calendar.getInstance();
            hour = c.get(Calendar.HOUR_OF_DAY);
            minute = c.get(Calendar.MINUTE);
        } else {
            mPreviousAlarm = getArguments().getParcelable(ARG_ALARM);
            mPreviousTime = mPreviousAlarm.getTime();
            hour = Integer.parseInt(mPreviousTime.split(":")[0]);
            minute = Integer.parseInt(mPreviousTime.split(":")[1]);
        }

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getContext(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);

        timeFormat = new SimpleDateFormat(TIME_PATTERN, Locale.getDefault());
        // selected alarm time:
        String newAlarmTime = timeFormat.format(calendar.getTime());
        callback.onTimeSelected(mPreviousAlarm, newAlarmTime);
    }

    public interface OnTimeSelectedListener extends BaseDialogCallback {
        void onTimeSelected(@Nullable Alarm previousAlarm, String newAlarmTime);
    }
}