package com.dmelnyk.alarmquest.application;

import com.dmelnyk.alarmquest.inject.PerActivity;
import com.dmelnyk.alarmquest.ui.alarm.AlarmQuestActivity;
import com.dmelnyk.alarmquest.ui.alarm.AlarmQuestActivityModule;
import com.dmelnyk.alarmquest.ui.main.MainActivity;
import com.dmelnyk.alarmquest.ui.main.MainActivityModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by d264 on 1/2/18.
 */

@Module
abstract class ActivityBindingModule {

    @PerActivity
    @ContributesAndroidInjector(modules = MainActivityModule.class)
    abstract MainActivity mainActivity();

    @PerActivity
    @ContributesAndroidInjector(modules = AlarmQuestActivityModule.class)
    abstract AlarmQuestActivity alarmQuestActivity();
}
