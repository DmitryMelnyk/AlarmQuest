package com.dmelnyk.alarmquest.application;

import android.app.Application;

import com.dmelnyk.alarmquest.inject.PerActivity;
import com.dmelnyk.alarmquest.ui.main.MainActivity;
import com.dmelnyk.alarmquest.ui.main.MainActivityModule;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * Created by dmitry on 29.04.17.
 */

@Module(includes = AndroidSupportInjectionModule.class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract Application application(App app);

    @PerActivity
    @ContributesAndroidInjector(modules = MainActivityModule.class)
    abstract MainActivity mainActivityInjector();

    // add other activities
}
