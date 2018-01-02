package com.dmelnyk.alarmquest.ui.alarm;

import com.dmelnyk.alarmquest.business.alarm.QuestInteractor;
import com.dmelnyk.alarmquest.business.alarm.QuestInteractorImpl;
import com.dmelnyk.alarmquest.ui.questfragment.repository.QuestionRepository;
import com.dmelnyk.alarmquest.ui.questfragment.repository.QuestionRepositoryImpl;
import com.dmelnyk.alarmquest.inject.PerActivity;
import com.dmelnyk.alarmquest.inject.PerFragment;

import dagger.Binds;
import dagger.Module;

/**
 * Created by d264 on 12/26/17.
 */

@Module
public abstract class AlarmQuestPresenterModule {

    @Binds
    @PerFragment
    abstract QuestionRepository providesIQuestionRepository(QuestionRepositoryImpl repository);

    @Binds
    @PerFragment
    abstract QuestInteractor providesIQuestInteractor(QuestInteractorImpl interactor);

    @Binds
    @PerActivity
    abstract Contract.IAlarmQuestPresenter alarmQuestPresenter(AlarmQuestPresenter presenter);
}
