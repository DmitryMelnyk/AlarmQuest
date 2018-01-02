package com.dmelnyk.alarmquest.ui.main.alarm.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import com.dmelnyk.alarmquest.application.App;
import com.dmelnyk.alarmquest.db.entity.AlarmEntity;
import com.dmelnyk.alarmquest.model.Alarm;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by d264 on 12/24/17.
 */

@Singleton
public class AlarmListRepositoryImpl implements AlarmListRepository {

    final App app;

    @Inject
    public AlarmListRepositoryImpl(Application context) {
        app = (App) context;
    }

    @Override
    public LiveData<List<AlarmEntity>> getAlarms() {
        return app.getRepository().getAlarms();
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
