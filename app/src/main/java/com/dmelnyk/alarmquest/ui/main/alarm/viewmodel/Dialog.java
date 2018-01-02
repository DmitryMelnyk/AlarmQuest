package com.dmelnyk.alarmquest.ui.main.alarm.viewmodel;

import android.support.annotation.NonNull;

import com.dmelnyk.alarmquest.model.Alarm;

import org.jetbrains.annotations.Nullable;

/**
 * Created by d264 on 12/24/17.
 */

public class Dialog {
    private @NonNull DIALOG type;
    private @Nullable Alarm alarm;
    private @Nullable Integer[] days;

    private Dialog() {
        this.type = DIALOG.TIME_PICKER_NEW;
    }

    private Dialog(Alarm alarm) {
        this.type = DIALOG.TIME_PICKER_EDIT;
        this.alarm = alarm;
    }

    private Dialog(Alarm alarm, Integer[] days) {
        this.type = DIALOG.DATE_PICKER_EDIT;
        this.alarm = alarm;
        this.days = days;
    }

    public static Dialog newAlarmPicker() {
        return new Dialog();
    }

    public static Dialog editAlarmPicker(Alarm alarm) {
        return new Dialog(alarm);
    }

    public static Dialog editDayPicker(Alarm alarm, Integer[] days) {
        return new Dialog(alarm, days);
    }

    @NonNull
    public DIALOG getType() {
        return type;
    }

    @Nullable
    public Alarm getAlarm() {
        return alarm;
    }

    @Nullable
    public Integer[] getDays() {
        return days;
    }

    @Override
    public String toString() {
        return "Dialog{" +
                "type=" + type +
                '}';
    }
}
