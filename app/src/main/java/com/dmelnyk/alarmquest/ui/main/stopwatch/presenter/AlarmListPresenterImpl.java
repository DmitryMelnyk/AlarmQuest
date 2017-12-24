package com.dmelnyk.alarmquest.ui.main.stopwatch.presenter;

import android.app.Application;
import android.content.Context;

import com.dmelnyk.alarmquest.application.App;
import com.dmelnyk.alarmquest.db.entity.AlarmEntity;
import com.dmelnyk.alarmquest.inject.PerFragment;
import com.dmelnyk.alarmquest.model.Alarm;
import com.dmelnyk.alarmquest.ui.common.presenter.BasePresenter;
import com.dmelnyk.alarmquest.ui.main.core.DayConverterUtil;
import com.dmelnyk.alarmquest.ui.main.stopwatch.alarmlistrepo.AlarmListRepository;
import com.dmelnyk.alarmquest.ui.main.stopwatch.view.AlarmListView;

import javax.inject.Inject;

/**
 * Created by d264 on 12/23/17.
 */

@PerFragment
class AlarmListPresenterImpl extends BasePresenter<AlarmListView> implements AlarmListPresenter {

    private static final String TOMORROW = "6"; // Sunday

    private final DayConverterUtil dayConverterUtil;

    private final AlarmListRepository repository;

    @Inject
    public AlarmListPresenterImpl(
            AlarmListView view,
            DayConverterUtil dayConverterUtil,
            AlarmListRepository repository) {
        super(view);
        this.dayConverterUtil = dayConverterUtil;
        this.repository = repository;
    }

    @Override
    public void onAddNewAlarmClicked() {
        view.showNewAlarmPicker();
    }

    @Override
    public void onEditAlarmClicked(Alarm alarm) {
        view.showEditAlarmPicker(alarm);
    }

    @Override
    public boolean onRemoveAlarmClicked(Alarm alarm) {
        repository.removeAlarm(alarm);
        return true;
    }

    @Override
    public void onEditDayClicked(Alarm alarm) {
        Integer[] numericDays = dayConverterUtil.convertDayToNumberView(alarm.getDays());
        view.showDayPicker(alarm, numericDays);
    }

    @Override
    public void onCheckedChanged(Alarm alarm) {
        Alarm newAlarm = new AlarmEntity(alarm);
        newAlarm.setEnable(!alarm.isEnable());
        repository.updateAlarm(newAlarm);
    }

    @Override
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

    @Override
    public void onTimeSelected(Alarm previousAlarm, String newAlarmTime) {
        Alarm newAlarm = null;
        if (previousAlarm == null) {
            newAlarm = new AlarmEntity();
            newAlarm.setEnable(true);
            newAlarm.setDays(TOMORROW);
            newAlarm.setTime(newAlarmTime);
            repository.addAlarm(newAlarm);
        } else {
            previousAlarm.setTime(newAlarmTime);
            repository.updateAlarm(previousAlarm);
        }
    }
}
