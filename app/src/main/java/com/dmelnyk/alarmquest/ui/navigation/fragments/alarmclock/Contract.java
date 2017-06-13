package com.dmelnyk.alarmquest.ui.navigation.fragments.alarmclock;

/**
 * Created by d264 on 6/11/17.
 */

public class Contract {

    public interface IAlarmFragmentView {

        void setTimeAndDate(String time, String date);

        void restoreAlarmTime(String time);

        void enableAlarmTimer(boolean enable, String savedAlarmTime);
    }

    public interface IAlarmFragmentPresenter {
        void bindView(IAlarmFragmentView view);
        void unbindView();

        void setAlarm(String time);

        void changeSwitcherState(boolean checked);
    }
}
