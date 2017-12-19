package com.dmelnyk.alarmquest.ui.navigation.fragments.alarmclock;

import com.dmelnyk.alarmquest.business.navigation.fragments.alarmclock.IAlarmFragmentInteractor;
import com.dmelnyk.alarmquest.utils.AlarmClockUtil;

/**
 * Created by d264 on 6/11/17.
 */

public class AlarmFragmentPresenter implements Contract.IAlarmFragmentPresenter {

    private Contract.IAlarmFragmentView view;
    private IAlarmFragmentInteractor interactor;
    private AlarmClockUtil alarmClockUtil;

    public AlarmFragmentPresenter(IAlarmFragmentInteractor interactor, AlarmClockUtil alarmClockUtil) {
        this.interactor = interactor;
        this.alarmClockUtil = alarmClockUtil;
    }

    @Override
    public void bindView(Contract.IAlarmFragmentView view) {
        this.view = view;

        // Setting current time and date
        view.setTimeAndDate(interactor.getCurrentTime(), interactor.getCurrentDate());
        // Restoring alarm time
        String savedAlarmTime = interactor.getSavedAlarm();
        boolean alarmIsEnable = interactor.getAlarmEnableState();

        view.restoreAlarmTime(savedAlarmTime);
        if (alarmIsEnable) {
            view.enableAlarmTimer(interactor.getAlarmEnableState(), savedAlarmTime);
        } else {
            view.enableAlarmTimer(interactor.getAlarmEnableState(), "-");
        }
    }

    @Override
    public void unbindView() {
        view = null;
    }

    @Override
    public void setAlarm(String time) {
        interactor.saveAlarm(time);
        alarmClockUtil.startAlarmClock(time);
    }

    @Override
    public void changeSwitcherState(boolean checked) {
        interactor.saveAlarmEnableState(checked);
        if (checked) {
            view.enableAlarmTimer(checked, interactor.getSavedAlarm());
            // TODO: start AlarmManager
            alarmClockUtil.startAlarmClock(interactor.getSavedAlarm());
        } else {
            view.enableAlarmTimer(checked, "-");
            // TODO: disable AlarmManager
            alarmClockUtil.stopAlarmClock();
        }
    }
}
