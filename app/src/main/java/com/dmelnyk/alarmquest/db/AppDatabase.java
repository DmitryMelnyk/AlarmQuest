package com.dmelnyk.alarmquest.db;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.dmelnyk.alarmquest.db.converter.DataConverter;
import com.dmelnyk.alarmquest.db.dao.AlarmDao;
import com.dmelnyk.alarmquest.db.entity.AlarmEntity;

import java.util.List;

/**
 * Created by d264 on 12/21/17.
 */

@Database(entities = {AlarmEntity.class}, version = 1)
@TypeConverters(DataConverter.class)
public abstract class AppDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "basic-db";
    private static AppDatabase sInstance;

    public abstract AlarmDao alarmDao();

    private final MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();

    public static AppDatabase getInstance(final Context context, AppExecutors executors) {
        if (sInstance == null) {
            synchronized (AppDatabase.class) {
                if (sInstance == null) {
                    sInstance = buildDatabase(context.getApplicationContext(), executors);
                    sInstance.updateDatabaseCreated(context.getApplicationContext());
                }
            }
        }
        return sInstance;
    }

    /**
     * Build the database. {@link Builder#build()} only sets up the database configuration and
     * creates a new instance of the database.
     * The SQLite database is only created when it's accessed for the first time.
     */
    private static AppDatabase buildDatabase(final Context appContext, AppExecutors executors) {
        Log.e("!!!", "building db");
        return Room.databaseBuilder(appContext, AppDatabase.class, DATABASE_NAME)
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        executors.diskIo().execute(() -> {
                            // Add a delay to simulate a long-running operation
                            addDelay();
                            // Generate the data for pre-population
                            AppDatabase database = AppDatabase.getInstance(appContext, executors);
                            List<AlarmEntity> alarms = DataGenerator.generateAlarms();
                            Log.e("!!!", "AppDatabase. Generated alarms=" + alarms.toString());

                            // remove this data
//                            insertData(database, alarms);
                            // notify that the database was created and it's ready to be used
                            database.setDatabaseCreated();
                        });
                    }
                }).build();

    }



    /**
     * Check whether the database already exists and expose it via {@link #getDatabaseCreated()}
     */
    private void updateDatabaseCreated(final Context context) {
        if (context.getDatabasePath(DATABASE_NAME).exists()) {
            setDatabaseCreated();
        }
    }

    private void setDatabaseCreated(){
        mIsDatabaseCreated.postValue(true);
    }

    private static void insertData(final AppDatabase database, final List<AlarmEntity> alarms) {
        database.runInTransaction(() -> {
            database.alarmDao().insertAll(alarms);
        });
    }

    private static void addDelay() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {
        }
    }

    public LiveData<Boolean> getDatabaseCreated() {
        return mIsDatabaseCreated;
    }
}
