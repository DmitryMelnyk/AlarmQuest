package com.dmelnyk.alarmquest.ui.main.stopwatch.presenter;

import com.dmelnyk.alarmquest.inject.PerFragment;
import com.dmelnyk.alarmquest.ui.main.stopwatch.alarmlistrepo.AlarmListRepository;
import com.dmelnyk.alarmquest.ui.main.stopwatch.alarmlistrepo.AlarmListRepositoryImpl;

import dagger.Binds;
import dagger.Module;

/**
 * Created by d264 on 12/23/17.
 */

@Module
public abstract class AlarmListPresenterModule {

    @Binds
    @PerFragment
    abstract AlarmListRepository alarmListRepository(
            AlarmListRepositoryImpl alarmListRepository);

    @Binds
    @PerFragment
    abstract AlarmListPresenter alarmListPresenter(
            AlarmListPresenterImpl alarmListPresenter);
}
