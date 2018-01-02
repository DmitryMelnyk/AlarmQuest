package com.dmelnyk.alarmquest.ui.alarm;

import com.dmelnyk.alarmquest.inject.PerActivity;
import com.dmelnyk.alarmquest.inject.PerFragment;
import com.dmelnyk.alarmquest.ui.questfragment.QuestFragment;
import com.dmelnyk.alarmquest.ui.questfragment.di.QuestFragmentModule;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by d264 on 12/25/17.
 */

@Module
public abstract class AlarmQuestActivityModule {

//    @Binds
//    @PerActivity
//    abstract QuestionRepository providesIQuestionRepository(QuestionRepositoryImpl repository);

//    @Binds
//    @PerActivity
//    abstract QuestInteractor providesIQuestInteractor(QuestInteractorImpl interactor);

    @Binds
    @PerActivity
    abstract Contract.IAlarmQuestPresenter alarmQuestPresenter(AlarmQuestPresenter presenter);

    @PerFragment
    @ContributesAndroidInjector(modules = QuestFragmentModule.class)
    abstract QuestFragment questFragment();
}
