package com.dmelnyk.alarmquest.ui.navigation.fragments.alarmclock.di;

import com.dmelnyk.alarmquest.ui.navigation.fragments.alarmclock.AlarmFragment;

import dagger.Subcomponent;

/**
 * Created by d264 on 6/11/17.
 */


@Subcomponent(modules = AlarmFragmentModule.class)
@AlarmFragmentScope
public interface AlarmFragmentComponent {

    void inject(AlarmFragment fragment);
}
