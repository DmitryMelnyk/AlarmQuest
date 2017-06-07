package com.dmelnyk.alarmquest.ui.alarm.questfragment.di;

import com.dmelnyk.alarmquest.ui.alarm.questfragment.QuestFragment;

import dagger.Component;
import dagger.Subcomponent;

/**
 * Created by dmitry on 23.05.17.
 */

@Component(modules = QuestModule.class)
@QuestScope
public interface QuestComponent {
    void inject(QuestFragment questFragment);
}
