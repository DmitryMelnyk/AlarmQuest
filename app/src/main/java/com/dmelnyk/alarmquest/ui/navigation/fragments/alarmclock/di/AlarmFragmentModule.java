package com.dmelnyk.alarmquest.ui.navigation.fragments.alarmclock.di;

import android.content.Context;

import com.dmelnyk.alarmquest.business.navigation.fragments.alarmclock.AlarmFragmentInteractor;
import com.dmelnyk.alarmquest.business.navigation.fragments.alarmclock.IAlarmFragmentInteractor;
import com.dmelnyk.alarmquest.data.navigation.fragments.alarmclock.ITimeRepository;
import com.dmelnyk.alarmquest.data.navigation.fragments.alarmclock.TimeRepository;
import com.dmelnyk.alarmquest.ui.navigation.fragments.alarmclock.AlarmFragmentPresenter;
import com.dmelnyk.alarmquest.ui.navigation.fragments.alarmclock.Contract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by d264 on 6/11/17.
 */

@Module
public class AlarmFragmentModule {

    @Provides
    @AlarmFragmentScope
    ITimeRepository providesITimeRepository(Context context) {
        return new TimeRepository(context);
    }

    @Provides
    @AlarmFragmentScope
    IAlarmFragmentInteractor providesIAlarmFragmentInteractor(ITimeRepository repository) {
        return new AlarmFragmentInteractor(repository);
    }

    @Provides
    @AlarmFragmentScope
    Contract.IAlarmFragmentPresenter providesIAlarmQuestPresenter(IAlarmFragmentInteractor interactor) {
        return new AlarmFragmentPresenter(interactor);
    }
}