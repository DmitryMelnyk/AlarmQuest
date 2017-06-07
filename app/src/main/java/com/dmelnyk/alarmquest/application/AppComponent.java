package com.dmelnyk.alarmquest.application;

import com.dmelnyk.alarmquest.ui.alarm.di.AlarmQuestComponent;
import com.dmelnyk.alarmquest.ui.alarm.di.AlarmQuestModule;
import com.dmelnyk.alarmquest.ui.alarm.questfragment.di.QuestComponent;
import com.dmelnyk.alarmquest.ui.alarm.questfragment.di.QuestModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by dmitry on 29.04.17.
 */

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {

    AlarmQuestComponent add(AlarmQuestModule module);
}
