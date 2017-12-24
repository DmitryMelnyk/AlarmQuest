package com.dmelnyk.alarmquest.ui.main;

import android.support.v7.app.AppCompatActivity;

import com.dmelnyk.alarmquest.inject.PerActivity;
import com.dmelnyk.alarmquest.inject.PerFragment;
import com.dmelnyk.alarmquest.ui.common.BaseActivityModule;
import com.dmelnyk.alarmquest.ui.main.alarm.view.AlarmFragment;
import com.dmelnyk.alarmquest.ui.main.alarm.view.AlarmFragmentModule;
import com.dmelnyk.alarmquest.ui.main.stopwatch.view.AlarmListFragment;
import com.dmelnyk.alarmquest.ui.main.stopwatch.view.AlarmListFragmentModule;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by d264 on 12/23/17.
 */

@Module(includes = BaseActivityModule.class)
public abstract class MainActivityModule {

    @PerFragment
    @ContributesAndroidInjector(modules = AlarmListFragmentModule.class)
    abstract AlarmListFragment alarmListFragment();

    @PerFragment
    @ContributesAndroidInjector(modules = AlarmFragmentModule.class)
    abstract AlarmFragment alarmFragmentModule();

    @Binds
    @PerActivity
    abstract AppCompatActivity appCompatActivity(MainActivity mainActivity);
}
