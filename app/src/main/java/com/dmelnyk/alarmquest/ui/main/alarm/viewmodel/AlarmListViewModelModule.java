package com.dmelnyk.alarmquest.ui.main.alarm.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.dmelnyk.alarmquest.inject.PerFragment;
import com.dmelnyk.alarmquest.ui.main.alarm.repository.AlarmListRepository;
import com.dmelnyk.alarmquest.ui.main.alarm.repository.AlarmListRepositoryImpl;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

/**
 * Created by d264 on 12/24/17.
 */

@Module
public abstract class AlarmListViewModelModule {

    @Binds
    @PerFragment
    abstract AlarmListRepository alarmListRepository(
            AlarmListRepositoryImpl alarmListRepository);

    @Binds
    @IntoMap
    @ViewModelKey(AlarmListViewModel.class)
    abstract ViewModel bindAlarmListModel(AlarmListViewModel alarmListViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(AlarmListViewModelFactory factory);
}
