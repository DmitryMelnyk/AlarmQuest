package com.dmelnyk.alarmquest.ui.main.alarm.presenter;

import com.dmelnyk.alarmquest.inject.PerFragment;

import dagger.Binds;
import dagger.Module;

/**
 * Created by d264 on 12/23/17.
 */

@Module
public abstract class AlarmPresenterModule {

    @Binds
    @PerFragment
    abstract AlarmPresenter alarmPresenter(AlarmPresenterImpl alarmPresenter);
}
