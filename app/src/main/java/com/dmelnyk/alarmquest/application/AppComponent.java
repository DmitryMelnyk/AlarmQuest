package com.dmelnyk.alarmquest.application;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjector;

/**
 * Created by dmitry on 29.04.17.
 */
/**
 * Injects application dependencies.
 */
@Singleton
@Component(modules = AppModule.class)
public interface AppComponent extends AndroidInjector<App> {

    @Component.Builder
    abstract class Builder extends AndroidInjector.Builder<App> {
    }
}
