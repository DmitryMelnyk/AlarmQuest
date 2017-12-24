package com.dmelnyk.alarmquest.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.NonNull;

import com.dmelnyk.alarmquest.application.App;
import com.dmelnyk.alarmquest.db.entity.AlarmEntity;

import java.util.List;

/**
 * Created by d264 on 12/21/17.
 */

public class AlarmListViewModel extends AndroidViewModel {

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<List<AlarmEntity>> mObservableAlarms;

    public AlarmListViewModel(@NonNull Application application) {
        super(application);

        mObservableAlarms = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        mObservableAlarms.setValue(null);

        LiveData<List<AlarmEntity>> rawAlarms =
                ((App) application).getRepository().getAlarms();

        mObservableAlarms.addSource(rawAlarms, mObservableAlarms::setValue);
    }

    /**
     * Expose the LiveData Alarms query so the UI can observe it.
     */
    public LiveData<List<AlarmEntity>> getAlarms() {
        return mObservableAlarms;
    }

    public void updateAlarm(AlarmEntity alarm) {

    }
}
