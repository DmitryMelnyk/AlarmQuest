package com.dmelnyk.alarmquest.application;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.squareup.leakcanary.LeakCanary;

import timber.log.Timber;

/**
 * Created by dmitry on 23.05.17.
 */

public class AlarmQuestApplication extends Application {

    // Dagger2 AppComponent
    @NonNull
    private AppComponent appComponent;

    @NonNull
    public AppComponent getAppComponent() {
        return appComponent;
    }

    @NonNull
    public static AlarmQuestApplication get(@NonNull Context context) {
        return (AlarmQuestApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Timber.plant(new Timber.DebugTree() {
            @Override
            protected String createStackElementTag(StackTraceElement element) {
                return element.getLineNumber() + ": " + element;
            }
        });

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);

        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }
}
