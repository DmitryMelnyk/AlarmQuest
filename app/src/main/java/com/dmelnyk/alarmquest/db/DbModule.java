package com.dmelnyk.alarmquest.db;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * Created by d264 on 1/5/18.
 */

@Module
public class DbModule {

    @Provides
    AppDatabase providesAppDatabase(Context context, AppExecutors executors) {
        return AppDatabase.getInstance(context, executors);
    }
}