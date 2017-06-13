package com.dmelnyk.alarmquest.application;

import com.dmelnyk.alarmquest.ui.alarm.di.AlarmQuestComponent;
import com.dmelnyk.alarmquest.ui.alarm.di.AlarmQuestModule;
import com.dmelnyk.alarmquest.ui.navigation.di.NavigationComponent;
import com.dmelnyk.alarmquest.ui.navigation.di.NavigationModule;
import com.dmelnyk.alarmquest.ui.navigation.fragments.alarmclock.di.AlarmFragmentComponent;
import com.dmelnyk.alarmquest.ui.navigation.fragments.alarmclock.di.AlarmFragmentModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by dmitry on 29.04.17.
 */

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {

    AlarmQuestComponent add(AlarmQuestModule module);

    NavigationComponent add(NavigationModule module);

    AlarmFragmentComponent add(AlarmFragmentModule module);
}
