package com.dmelnyk.alarmquest.business.navigation.fragments.alarmclock;

/**
 * Created by d264 on 6/13/17.
 */

public interface IAlarmFragmentInteractor {
    void saveAlarm(String alarm);
    String getSavedAlarm();
    String getCurrentTime();
    String getCurrentDate();

    void saveAlarmEnableState(boolean checked);
    boolean getAlarmEnableState();
}
