package com.dmelnyk.alarmquest.application;

import android.app.Application;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.dmelnyk.alarmquest.inject.PerActivity;
import com.dmelnyk.alarmquest.ui.alarm.AlarmQuestActivity;
import com.dmelnyk.alarmquest.ui.alarm.AlarmQuestActivityModule;
import com.dmelnyk.alarmquest.ui.alarm.AlarmQuestPresenterModule;
import com.dmelnyk.alarmquest.ui.main.MainActivity;
import com.dmelnyk.alarmquest.ui.main.MainActivityModule;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * Created by dmitry on 29.04.17.
 */

@Module
abstract class AppModule {

    @Binds
    abstract Application application(App app);

    @Binds
    abstract Context providesContext(App app);
}
