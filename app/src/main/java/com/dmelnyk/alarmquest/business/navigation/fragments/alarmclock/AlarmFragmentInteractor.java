package com.dmelnyk.alarmquest.business.navigation.fragments.alarmclock;

import com.dmelnyk.alarmquest.data.navigation.fragments.alarmclock.ITimeRepository;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by d264 on 6/13/17.
 */

public class AlarmFragmentInteractor implements IAlarmFragmentInteractor {
    private static final String TIME_PATTERN = "HH:mm";

    private ITimeRepository repository;
    private Calendar calendar;
    private DateFormat dateFormat;
    private SimpleDateFormat timeFormat;

    public AlarmFragmentInteractor(ITimeRepository repository) {
        this.repository = repository;
        calendar = Calendar.getInstance();
        dateFormat = DateFormat.getDateInstance();
        timeFormat = new SimpleDateFormat(TIME_PATTERN, Locale.getDefault());
    }


    @Override
    public void saveAlarm(String alarm) {
        repository.saveAlarmTime(alarm);
    }

    @Override
    public String getSavedAlarm() {
        return repository.getSavedAlarm();
    }

    @Override
    public String getCurrentTime() {
        return timeFormat.format(calendar.getTime());
    }

    @Override
    public String getCurrentDate() {
        return dateFormat.format(calendar.getTime());
    }

    @Override
    public void saveAlarmEnableState(boolean checked) {
        repository.saveAlarmEnableSate(checked);
    }

    @Override
    public boolean getAlarmEnableState() {
        return repository.getSavedAlarmEnableState();
    }
}
