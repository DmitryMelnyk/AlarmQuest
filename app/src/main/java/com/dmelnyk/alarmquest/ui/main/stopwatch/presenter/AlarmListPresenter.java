package com.dmelnyk.alarmquest.ui.main.stopwatch.presenter;

import com.dmelnyk.alarmquest.model.Alarm;
import com.dmelnyk.alarmquest.ui.common.presenter.Presenter;

/**
 * Created by d264 on 12/23/17.
 */

public interface AlarmListPresenter extends Presenter {

    void onAddNewAlarmClicked();

    void onEditAlarmClicked(Alarm alarm);

    boolean onRemoveAlarmClicked(Alarm alarm);

    void onEditDayClicked(Alarm alarm);

    void onCheckedChanged(Alarm alarm);

    void onTimeSelected(Alarm previousAlarm, String newAlarmTime);

    void onDaySelected(Alarm alarm, Integer[] selectedDays);
}
