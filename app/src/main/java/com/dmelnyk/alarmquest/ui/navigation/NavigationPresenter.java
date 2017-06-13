package com.dmelnyk.alarmquest.ui.navigation;

import com.dmelnyk.alarmquest.data.navigation.fragments.alarmclock.ITimeRepository;
import com.dmelnyk.alarmquest.ui.navigation.Contract.INavigationView;

/**
 * Created by d264 on 6/14/17.
 */

public class NavigationPresenter implements Contract.INavigationPresenter {

    private ITimeRepository timeRepository;
    private Contract.INavigationView view;

    public NavigationPresenter(ITimeRepository timeRepository) {
        this.timeRepository = timeRepository;
    }

    @Override
    public void bindView(INavigationView view) {
        this.view = view;
        if (view != null) {
            if (timeRepository.getSavedAlarmEnableState()) {
                view.restoreAlarmTime(timeRepository.getSavedAlarm());
            } else {
                view.restoreAlarmTime("-");
            }
        }
    }

    @Override
    public void unbindView() {
        view = null;
    }

    @Override
    public void alarmEnabled() {
        view.restoreAlarmTime(timeRepository.getSavedAlarm());
    }
}
