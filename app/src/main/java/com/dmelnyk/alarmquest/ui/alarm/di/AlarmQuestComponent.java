package com.dmelnyk.alarmquest.ui.alarm.di;

import com.dmelnyk.alarmquest.ui.alarm.AlarmQuestActivity;

import dagger.Subcomponent;

/**
 * Created by d264 on 6/7/17.
 */

@Subcomponent(modules = AlarmQuestModule.class)
@AlarmQuestScope
public interface AlarmQuestComponent {
    void inject(AlarmQuestActivity activity);
}
