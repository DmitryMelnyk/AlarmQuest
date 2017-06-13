package com.dmelnyk.alarmquest.ui.navigation;

/**
 * Created by d264 on 6/14/17.
 */

public class Contract {

    public interface INavigationView {
        void restoreAlarmTime(String time);
    }

    public interface INavigationPresenter {
        void bindView(INavigationView view);
        void unbindView();

        void alarmEnabled();
    }
}
