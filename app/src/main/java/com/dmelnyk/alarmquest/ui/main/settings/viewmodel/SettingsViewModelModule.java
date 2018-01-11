package com.dmelnyk.alarmquest.ui.main.settings.viewmodel;

import android.arch.lifecycle.ViewModel;

import com.dmelnyk.alarmquest.inject.PerFragment;
import com.dmelnyk.alarmquest.ui.main.alarm_list.viewmodel.ViewModelKey;
import com.dmelnyk.alarmquest.ui.main.settings.repository.SettingsRepository;
import com.dmelnyk.alarmquest.ui.main.settings.repository.SettingsRepositoryImpl;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

/**
 * Created by d264 on 1/9/18.
 */

@Module
public abstract class SettingsViewModelModule {

    @Binds
    @PerFragment
    abstract SettingsRepository bindsSettingsRepository(SettingsRepositoryImpl repository);

    @Binds
    @IntoMap
    @ViewModelKey(SettingsViewModelImpl.class)
    abstract ViewModel bindViewModel(SettingsViewModelImpl viewModel);
}
