package com.dmelnyk.alarmquest.ui.main.demo.viewmodel;

import android.arch.lifecycle.ViewModel;

import com.dmelnyk.alarmquest.ui.alarm.business.QuestInteractor;
import com.dmelnyk.alarmquest.ui.alarm.business.QuestInteractorImpl;
import com.dmelnyk.alarmquest.inject.PerFragment;
import com.dmelnyk.alarmquest.ui.questfragment.repository.QuestionRepository;
import com.dmelnyk.alarmquest.ui.questfragment.repository.QuestionRepositoryImpl;
import com.dmelnyk.alarmquest.ui.main.alarm_list.viewmodel.ViewModelKey;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

/**
 * Created by d264 on 12/24/17.
 */

@Module
public abstract class DemoQuestViewModelModule {

    @Binds
    @PerFragment
    abstract QuestionRepository bindQuestionRepository(QuestionRepositoryImpl repository);

    @Binds
    @PerFragment
    abstract QuestInteractor questInteractor(QuestInteractorImpl questInteractor);

    @Binds
    @IntoMap
    @ViewModelKey(DemoQuestViewModel.class)
    abstract ViewModel bindDemoQuestViewModel(DemoQuestViewModel demoQuestViewModel);
}
