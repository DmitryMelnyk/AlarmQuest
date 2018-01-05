package com.dmelnyk.alarmquest.ui.main.alarm_list.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.util.Log;

import com.dmelnyk.alarmquest.alarmmanager.AlarmJobCreator;
import com.dmelnyk.alarmquest.db.entity.AlarmEntity;
import com.dmelnyk.alarmquest.model.Alarm;
import com.dmelnyk.alarmquest.ui.main.alarm_list.repository.AlarmListRepository;
import com.dmelnyk.alarmquest.utils.DayConverterUtil;
import com.dmelnyk.alarmquest.utils.TimeCalcUtil;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import timber.log.Timber;

/**
 * Created by d264 on 12/21/17.
 */

public class AlarmListViewModel extends ViewModel {

    private final DayConverterUtil dayConverterUtil;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<List<AlarmEntity>> mObservableAlarms;

    // For toast messages
    private final BehaviorSubject<String> mMsgSource = BehaviorSubject.create();

    // For creating dialogs
    private final BehaviorSubject<Dialog> mDialogSource = BehaviorSubject.create();

    private final AlarmListRepository repository;
    private final TimeCalcUtil timeUtil;

    private String tag = getClass().getSimpleName();

    @Inject
    public AlarmListViewModel(@NonNull final DayConverterUtil dayConverterUtil,
                              @NonNull final TimeCalcUtil timeUtil,
                              @NonNull final AlarmListRepository repository) {
        this.dayConverterUtil = dayConverterUtil;
        this.timeUtil = timeUtil;
        this.repository = repository;
        // initialize data here
        mObservableAlarms = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        mObservableAlarms.setValue(null);
        LiveData<List<AlarmEntity>> alarms = repository.getAlarms();

        mObservableAlarms.addSource(alarms, mObservableAlarms::setValue);
    }

    /**
     * Expose the LiveData Alarms query so the UI can observe it.
     */
    @NonNull
    public LiveData<List<AlarmEntity>> getAlarms() {
        return mObservableAlarms;
    }

    /**
     * Expose the Observable messages to inform about alarms changes.
     * @return
     */
    @NonNull
    public Observable<String> getToasts() {
        return mMsgSource.flatMap(Observable::just);
    }

    /**
     * Expose data to create special dialogs
     * @return
     */
    @NonNull
    public Observable<Dialog> getDialogs() {
        return mDialogSource.flatMap(Observable::just);
    }

    public void onDaySelected(Alarm alarm, Integer[] selectedDays) {
        String days = "";
        if (selectedDays != null) {
            for (Integer day : selectedDays) {
                days += day + " ";
            }
            if (days.length() > 1) {
                days = days.substring(0, days.length() - 1);
            }
        }
        Alarm newAlarm = new AlarmEntity(alarm);
        newAlarm.setDays(days);
        repository.updateAlarm(newAlarm);
        Timber.d("Updated days=" + days);
    }

    public void onTimeSelected(Alarm previousAlarm, String newAlarmTime) {
        Alarm newAlarm = null;
        if (previousAlarm == null) {
            // creating new alarm
            newAlarm = new AlarmEntity();
            newAlarm.setEnable(true);

            newAlarm.setTime(newAlarmTime);
            repository.addAlarm(newAlarm);
        } else {
            newAlarm = new AlarmEntity(previousAlarm);
            newAlarm.setTime(newAlarmTime);
            repository.updateAlarm(newAlarm);
        }

        if (newAlarm.isEnable()) {
            startNewAlarm(newAlarm);
            // displays time to alarm
            String nextTime = timeUtil.timeToAlarm(newAlarmTime);
            mMsgSource.onNext(nextTime);
        }
    }

    public void onAddNewAlarmClicked() {
        Log.d(tag, "onAddNewAlarmClicked");
        mDialogSource.onNext(Dialog.newAlarmPicker());
    }

    public void onEditAlarmClicked(Alarm alarm) {
        Log.d(tag, "onEditAlarmClicked");
        mDialogSource.onNext(Dialog.editAlarmPicker(alarm));
    }

    public boolean onRemoveAlarmClicked(Alarm alarm) {
        repository.removeAlarm(alarm);
        stopAlarm(alarm);
        return true;
    }

    public void onEditDayClicked(Alarm alarm) {
        Log.d(tag, "onEditDayClicked");
        Integer[] numericDays = alarm.getDays().isEmpty()
                ? null
                : dayConverterUtil.convertDayToNumberView(alarm.getDays());
        mDialogSource.onNext(Dialog.editDayPicker(alarm, numericDays));
    }

    public void onCheckedChanged(Alarm alarm) {
        Alarm newAlarm = new AlarmEntity(alarm);
        newAlarm.setEnable(!alarm.isEnable());
        repository.updateAlarm(newAlarm);
        if (newAlarm.isEnable()) {
            startNewAlarm(alarm);
        } else {
            stopAlarm(alarm);
        }
    }

    private void startNewAlarm(Alarm newAlarm) {
        long nextAlarmTime = timeUtil.getTimeToAlarmMillis(newAlarm.getTime());
        //starting alarm
        AlarmJobCreator.scheduleAlarmAtTime(nextAlarmTime, newAlarm.getId());
    }

    private void stopAlarm(Alarm alarm) {
        AlarmJobCreator.cancelJob(alarm.getId());
    }
}
