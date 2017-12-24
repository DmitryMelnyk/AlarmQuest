package com.dmelnyk.alarmquest.ui.main.stopwatch;

import android.view.View;

import com.dmelnyk.alarmquest.model.Alarm;

/**
 * Created by d264 on 12/21/17.
 */

public interface OnAlarmClickCallback {
    void editedAlarm(Alarm alarm);
    boolean removedAlarm(View view, Alarm alarm);
    void onEditedDays(Alarm alarm);
    void onCheckedChanged(Alarm alarm);
//    void onCheckedChanged(SwitchButton view, Alarm alarm);
}
