package com.dmelnyk.alarmquest.data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;

import com.dmelnyk.alarmquest.db.AppDatabase;
import com.dmelnyk.alarmquest.db.entity.AlarmEntity;
import com.dmelnyk.alarmquest.model.Alarm;

import java.util.List;

/**
 * Created by d264 on 12/21/17.
 */

/**
 * Repository handling the work with alarms.
 */
public class DataRepository {

    private static DataRepository sInstance;

    private final AppDatabase mDatabase;
    private MediatorLiveData<List<AlarmEntity>> mObservableAlarms;

    public DataRepository(final AppDatabase database) {
        mDatabase = database;
        mObservableAlarms = new MediatorLiveData<>();

        mObservableAlarms.addSource(mDatabase.alarmDao().loadAllAlarms(),
                alarmEntities -> {
                    if (mDatabase.getDatabaseCreated().getValue() != null) {
                        mObservableAlarms.postValue(alarmEntities);
                    }
                }
        );
    }

    public static DataRepository getInstance(final AppDatabase database) {
        if (sInstance == null) {
            synchronized (DataRepository.class) {
                if (sInstance == null) {
                    sInstance = new DataRepository(database);
                }
            }
        }
        return sInstance;
    }

    /**
     * Get the list of alarms from the database and get notified when the data changes.
     */
    public LiveData<List<AlarmEntity>> getAlarms() {
        return mObservableAlarms;
    }

    public LiveData<AlarmEntity> loadAlarm(final int alarmId) {
        return mDatabase.alarmDao().loadAlarm(alarmId);
    }

    /**
     * Updates alarm with days in number look ("0 1 6")
     * @param alarm
     */
     public void updateAlarm(final Alarm alarm) {
        Runnable task = () -> mDatabase.alarmDao().update((AlarmEntity) alarm);
        new Thread(task).start();
    }

    public void removeAlarm(Alarm alarm) {
        Runnable task = () -> mDatabase.alarmDao().delete((AlarmEntity) alarm);
        new Thread(task).start();
    }

    public void addAlarm(Alarm alarm) {
        Runnable task = () -> mDatabase.alarmDao().add((AlarmEntity) alarm);
        new Thread(task).start();
    }
}
