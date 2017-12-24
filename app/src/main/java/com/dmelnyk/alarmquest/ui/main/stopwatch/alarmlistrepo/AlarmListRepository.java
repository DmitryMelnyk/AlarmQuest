package com.dmelnyk.alarmquest.ui.main.stopwatch.alarmlistrepo;

import com.dmelnyk.alarmquest.model.Alarm;

/**
 * Created by d264 on 12/24/17.
 */

public interface AlarmListRepository {
    void addAlarm(Alarm alarm);
    void updateAlarm(Alarm alarm);
    void removeAlarm(Alarm alarm);
}
