package com.dmelnyk.alarmquest.ui.main.core;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import com.dmelnyk.alarmquest.db.entity.AlarmEntity;
import com.dmelnyk.alarmquest.model.Alarm;
import com.dmelnyk.alarmquest.ui.main.fragments.alarm.AlarmAdapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by d264 on 12/19/17.
 */

public class TimePickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {

    private static final String TIME_PATTERN = "HH:mm";
    public static final String ARG_ALARM_TIME = "ARG_ALARM_TIME";
    public static final String ARG_ALARM = "ARG_ALARM";

    private SimpleDateFormat timeFormat;
    private OnTimeSelectedListener mListener;

    private String mPreviousTime = null;
    private Alarm mPreviousAlarm;

    public void setCallbackListener(OnTimeSelectedListener listener) {
        this.mListener = listener;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        int hour = -1;
        int minute = -1;
        String beforeTime = null;
        if (getArguments() == null) {
            final Calendar c = Calendar.getInstance();
            hour = c.get(Calendar.HOUR_OF_DAY);
            minute = c.get(Calendar.MINUTE);
        } else {
            mPreviousAlarm = getArguments().getParcelable(ARG_ALARM);
            mPreviousTime = mPreviousAlarm.getTime();
//            mPreviousTime = getArguments().getString(ARG_ALARM_TIME);
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
        if (isCallbackListenerImplemented()) {
            mListener.onTimeSelected(mPreviousAlarm, newAlarmTime);
            // prevents memory leaking
            mListener = null;
        }
    }

    private boolean isCallbackListenerImplemented() {
        if (mListener == null) {
            throw new ClassCastException(TimePickerFragment.OnTimeSelectedListener.class + " not implemented");
        } else return true;
    }

    public interface OnTimeSelectedListener {
        void onTimeSelected(@Nullable Alarm previousAlarm, String newAlarmTime);
    }
}