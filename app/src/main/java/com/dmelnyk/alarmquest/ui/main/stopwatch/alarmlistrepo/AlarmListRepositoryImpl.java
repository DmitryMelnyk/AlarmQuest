package com.dmelnyk.alarmquest.ui.main.stopwatch.alarmlistrepo;

import android.app.Application;

import com.dmelnyk.alarmquest.application.App;
import com.dmelnyk.alarmquest.model.Alarm;

import javax.inject.Inject;

/**
 * Created by d264 on 12/24/17.
 */

public class AlarmListRepositoryImpl implements AlarmListRepository {

    final App app;

    @Inject
    public AlarmListRepositoryImpl(Application context) {
        app = (App) context;
    }

    @Override
    public void addAlarm(Alarm alarm) {
        app.getRepository().addAlarm(alarm);
    }

    @Override
    public void updateAlarm(Alarm alarm) {
        app.getRepository().updateAlarm(alarm);
    }

    @Override
    public void removeAlarm(Alarm alarm) {
        app.getRepository().removeAlarm(alarm);
    }
}
