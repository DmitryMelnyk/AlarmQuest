package com.dmelnyk.alarmquest.application;

import android.app.Application;
import android.content.Context;

import dagger.Binds;
import dagger.Module;

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
