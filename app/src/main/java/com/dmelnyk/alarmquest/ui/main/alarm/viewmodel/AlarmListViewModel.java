package com.dmelnyk.alarmquest.ui.main.alarm.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.util.Log;

import com.dmelnyk.alarmquest.alarmmanager.AlarmJobCreator;
import com.dmelnyk.alarmquest.alarmmanager.TimeCalcUtil;
import com.dmelnyk.alarmquest.db.entity.AlarmEntity;
import com.dmelnyk.alarmquest.model.Alarm;
import com.dmelnyk.alarmquest.ui.main.core.DayConverterUtil;
import com.dmelnyk.alarmquest.ui.main.alarm.repository.AlarmListRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

/**
 * Created by d264 on 12/21/17.
 */

public class AlarmListViewModel extends ViewModel {

    private static final String TOMORROW = "6"; // Sunday

    private final DayConverterUtil dayConverterUtil;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<List<AlarmEntity>> mObservableAlarms;

    // For toast messages
    private final BehaviorSubject<String> mMsgSource = BehaviorSubject.create();

    // For creating dialogs
    private final BehaviorSubject<Dialog> mDialogSource = BehaviorSubject.create();

    private final AlarmListRepository repository;

    private String tag = getClass().getSimpleName();

    @Inject
    public AlarmListViewModel(@NonNull final DayConverterUtil dayConverterUtil,
                              @NonNull final AlarmListRepository repository) {
        this.dayConverterUtil = dayConverterUtil;
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
        /*todo: here in presenter remove Alarm,*/
        /*todo: then calc time to alarm (off, today, tomorrow, in a 3 day... and start Alarm,*/
        /*todo: write to db correct days. If selected Days is empty -> tomorrow or */

        String days = "";
        if (selectedDays != null) {
            for (Integer day : selectedDays) {
                days += day + " ";
            }
            if (days.length() > 1) {
                days = days.substring(0, days.length() - 1);
            }
        } else {
            days = TOMORROW;
        }
        Alarm newAlarm = new AlarmEntity(alarm);
        newAlarm.setDays(days);
        repository.updateAlarm(newAlarm);
    }

    public void onTimeSelected(Alarm previousAlarm, String newAlarmTime) {
        Alarm newAlarm = null;
        if (previousAlarm == null) {
            newAlarm = new AlarmEntity();
            newAlarm.setEnable(true);
            newAlarm.setDays(TOMORROW);
            newAlarm.setTime(newAlarmTime);
            repository.addAlarm(newAlarm);
        } else {
            newAlarm = new AlarmEntity(previousAlarm);
            newAlarm.setTime(newAlarmTime);
            repository.updateAlarm(newAlarm);
        }

        TimeCalcUtil manager = new TimeCalcUtil();
        String nextTime = manager.timeToAlarm(newAlarmTime);
        long nextAlarmTime = manager.getTimeToAlarmMillis(newAlarmTime);
        Log.e("!!!!!!!!!", "alarmId=" + newAlarm.getId());
        int alarmId = AlarmJobCreator.scheduleAlarmAtTime(nextAlarmTime, newAlarm.getId());
        mMsgSource.onNext(nextTime);
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
        return true;
    }

    public void onEditDayClicked(Alarm alarm) {
        Log.d(tag, "onEditDayClicked");
        Integer[] numericDays = dayConverterUtil.convertDayToNumberView(alarm.getDays());
        mDialogSource.onNext(Dialog.editDayPicker(alarm, numericDays));
    }

    public void onCheckedChanged(Alarm alarm) {
        Alarm newAlarm = new AlarmEntity(alarm);
        newAlarm.setEnable(!alarm.isEnable());
        repository.updateAlarm(newAlarm);
    }
}
