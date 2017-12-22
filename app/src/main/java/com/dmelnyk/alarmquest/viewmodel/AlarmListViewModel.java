package com.dmelnyk.alarmquest.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Transformations;
import android.support.annotation.NonNull;

import com.dmelnyk.alarmquest.R;
import com.dmelnyk.alarmquest.application.AlarmQuestApplication;
import com.dmelnyk.alarmquest.db.entity.AlarmEntity;
import com.dmelnyk.alarmquest.ui.main.core.DayConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by d264 on 12/21/17.
 */

public class AlarmListViewModel extends AndroidViewModel {

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<List<AlarmEntity>> mObservableAlarms;

    public AlarmListViewModel(@NonNull Application application) {
        super(application);

        String[] days = application.getResources().getStringArray(R.array.days);

        mObservableAlarms = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        mObservableAlarms.setValue(null);

        LiveData<List<AlarmEntity>> rawAlarms =
                ((AlarmQuestApplication) application).getRepository().getAlarms();

        LiveData<List<AlarmEntity>> alarms = Transformations.map(rawAlarms, alarmList -> {
            List<AlarmEntity> transformedAlarmList = new ArrayList<>();
            for (AlarmEntity rawAlarm : alarmList) {
                AlarmEntity transformedAlarm = new AlarmEntity(rawAlarm);
                transformedAlarm.setDays(DayConverter.convertDayToWordView(days, rawAlarm.getDays()));
                transformedAlarmList.add(transformedAlarm);
            }

            return transformedAlarmList;
        });

        mObservableAlarms.addSource(alarms, mObservableAlarms::setValue);
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
