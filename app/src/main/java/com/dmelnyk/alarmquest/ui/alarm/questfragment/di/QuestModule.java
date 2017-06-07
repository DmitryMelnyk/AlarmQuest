package com.dmelnyk.alarmquest.ui.alarm.questfragment.di;

import com.dmelnyk.alarmquest.business.alarm.IQuestInteractor;
import com.dmelnyk.alarmquest.ui.alarm.questfragment.Contract;
import com.dmelnyk.alarmquest.ui.alarm.questfragment.QuestPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by dmitry on 23.05.17.
 */

@Module
public class QuestModule {

    @Provides
    @QuestScope
    Contract.IQuestPresenter providesIQuestPresenter() {
        return new QuestPresenter();
    }
}
