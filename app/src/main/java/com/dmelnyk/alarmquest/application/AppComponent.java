package com.dmelnyk.alarmquest.application;

import com.dmelnyk.alarmquest.db.DbModule;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * Created by dmitry on 29.04.17.
 */
/**
 * Injects application dependencies.
 */
@Singleton
@Component(modules = {
        AppModule.class,
        ActivityBindingModule.class,
        AndroidSupportInjectionModule.class,
        DbModule.class,
        ViewModelModule.class
})
public interface AppComponent extends AndroidInjector<App> {

    @Component.Builder
    abstract class Builder extends AndroidInjector.Builder<App> {
    }

//    @Component.Builder
//    interface Builder {
//
//        @BindsInstance
//        AppComponent.Builder application(Application application);
//
//        AppComponent build();
//    }
}
