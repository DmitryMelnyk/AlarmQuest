package com.dmelnyk.alarmquest.ui.navigation.fragments.alarmclock;

import com.dmelnyk.alarmquest.business.navigation.fragments.alarmclock.IAlarmFragmentInteractor;

/**
 * Created by d264 on 6/11/17.
 */

public class AlarmFragmentPresenter implements Contract.IAlarmFragmentPresenter {

    private Contract.IAlarmFragmentView view;
    private IAlarmFragmentInteractor interactor;

    public AlarmFragmentPresenter(IAlarmFragmentInteractor interactor) {
        this.interactor = interactor;
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
    }

    @Override
    public void changeSwitcherState(boolean checked) {
        interactor.saveAlarmEnableState(checked);
        if (checked) {
            // TODO: start AlarmManager
            view.enableAlarmTimer(checked, interactor.getSavedAlarm());
        } else {
            // TODO: disable AlarmManager
            view.enableAlarmTimer(checked, "-");
        }
    }
}
