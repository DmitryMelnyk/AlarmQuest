package com.dmelnyk.alarmquest.ui.main;


import android.support.v7.app.AppCompatActivity;

import com.dmelnyk.alarmquest.inject.PerActivity;
import com.dmelnyk.alarmquest.inject.PerFragment;
import com.dmelnyk.alarmquest.ui.common.BaseActivityModule;
import com.dmelnyk.alarmquest.ui.common.view.BaseFragmentModule;
import com.dmelnyk.alarmquest.ui.main.demo.view.DemoQuestFragment;
import com.dmelnyk.alarmquest.ui.main.demo.view.DemoQuestFragmentModule;
import com.dmelnyk.alarmquest.ui.main.alarm.view.AlarmListFragment;
import com.dmelnyk.alarmquest.ui.main.alarm.view.AlarmListFragmentModule;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by d264 on 12/23/17.
 */

@Module(includes = {
        BaseActivityModule.class,
         BaseFragmentModule.class
})
public abstract class MainActivityModule {

    @PerFragment
    @ContributesAndroidInjector(modules = AlarmListFragmentModule.class)
    abstract AlarmListFragment alarmListFragment();

    @PerFragment
    @ContributesAndroidInjector(modules = DemoQuestFragmentModule.class)
    abstract DemoQuestFragment demoQuestFragment();

    @PerActivity
    @Binds
    abstract AppCompatActivity bindMainActivity(MainActivity mainActivity);
}
