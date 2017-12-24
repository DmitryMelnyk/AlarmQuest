package com.dmelnyk.alarmquest.ui.main.stopwatch.view;

import android.support.annotation.Nullable;

import com.dmelnyk.alarmquest.model.Alarm;
import com.dmelnyk.alarmquest.ui.common.view.MVPView;

/**
 * Created by d264 on 12/23/17.
 */

public interface AlarmListView extends MVPView {

    void showDayPicker(@Nullable Alarm alarm, Integer[] selectedDays);

    void showNewAlarmPicker();

    void showEditAlarmPicker(Alarm alarm);
}
