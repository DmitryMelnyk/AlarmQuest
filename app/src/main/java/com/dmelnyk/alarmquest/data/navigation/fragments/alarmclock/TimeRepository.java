package com.dmelnyk.alarmquest.data.navigation.fragments.alarmclock;

import android.content.Context;

/**
 * Created by d264 on 6/13/17.
 */

public class TimeRepository implements ITimeRepository {

    private static final String PREF_KEY = "preference key";
    private static final String ALARM_TIME_KEY = "alarm time";
    private static final String ALARM_ENABLE_STATE = "alarm enable";

    private Context mContext;

    public TimeRepository(Context mContext) {
        this.mContext = mContext;
    }

    public void saveAlarmTime(String time) {
        mContext.getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE).edit()
                .putString(ALARM_TIME_KEY, time).commit();
    }

    public String getSavedAlarm() {
        return mContext.getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE)
                .getString(ALARM_TIME_KEY, "6:00");
    }

    @Override
    public void saveAlarmEnableSate(boolean state) {
        mContext.getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE).edit()
                .putBoolean(ALARM_ENABLE_STATE, state)
                .commit();
    }

    @Override
    public boolean getSavedAlarmEnableState() {
        return mContext.getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE)
                .getBoolean(ALARM_ENABLE_STATE, false);
    }
}
