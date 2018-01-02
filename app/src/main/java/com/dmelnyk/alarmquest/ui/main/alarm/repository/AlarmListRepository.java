package com.dmelnyk.alarmquest.ui.main.alarm.repository;

import android.arch.lifecycle.LiveData;

import com.dmelnyk.alarmquest.db.entity.AlarmEntity;
import com.dmelnyk.alarmquest.model.Alarm;

import java.util.List;

/**
 * Created by d264 on 12/24/17.
 */

public interface AlarmListRepository {
    void addAlarm(Alarm alarm);
    void updateAlarm(Alarm alarm);
    void removeAlarm(Alarm alarm);

    LiveData<List<AlarmEntity>> getAlarms();
}
