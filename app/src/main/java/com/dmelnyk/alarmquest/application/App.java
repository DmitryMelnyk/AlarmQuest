package com.dmelnyk.alarmquest.application;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.dmelnyk.alarmquest.R;
import com.dmelnyk.alarmquest.alarmmanager.AlarmJobCreator;
import com.dmelnyk.alarmquest.data.DataRepository;
import com.dmelnyk.alarmquest.db.AppDatabase;
import com.dmelnyk.alarmquest.db.AppExecutors;
import com.evernote.android.job.JobManager;
import com.squareup.leakcanary.LeakCanary;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import timber.log.Timber;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by dmitry on 23.05.17.
 */

public class App extends Application implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> activityInjector;

    @Inject
    AppDatabase database;

    private AppExecutors mAppExecutors;

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return activityInjector;
    }

    public static App get(Context context) {
        return (App) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // to inject application Context
        DaggerAppComponent.builder().create(this).inject(this);
//         to use variant without injecting context uncomment this
//        DaggerAppComponent.create().inject(this);

        mAppExecutors = new AppExecutors();

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

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/fonts.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

        JobManager.create(this).addJobCreator(new AlarmJobCreator());
    }

    private AppDatabase getDatabase() {
        return AppDatabase.getInstance(this, mAppExecutors);
    }

    public DataRepository getRepository() {
        return DataRepository.getInstance(getDatabase());
    }
}
