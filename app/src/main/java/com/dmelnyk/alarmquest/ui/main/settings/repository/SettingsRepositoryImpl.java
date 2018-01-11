package com.dmelnyk.alarmquest.ui.main.settings.repository;

import android.content.Context;
import android.database.Cursor;
import android.media.RingtoneManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.dmelnyk.alarmquest.ui.main.settings.model.AlarmSettings;
import com.dmelnyk.alarmquest.ui.main.settings.model.ObservableSettings;
import com.ironz.binaryprefs.BinaryPreferencesBuilder;
import com.ironz.binaryprefs.Preferences;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * Created by d264 on 1/8/18.
 */

public class SettingsRepositoryImpl implements SettingsRepository {

    private final Preferences preferences;
    private final ObservableSettings observableSettings;

    private AlarmSettings settings;
    private final Context mContext;

    @Override
    public ObservableSettings getSettings() {
        return observableSettings;
    }

    private AlarmSettings getAlarmSettings() {
        settings = preferences.getPersistable(AlarmSettings.KEY, null);
        return settings;
    }

    public AlarmSettings getAS() {
        AlarmSettings settings = AlarmSettings.defaultInstance();
        Uri DEFAULT_ALARM_URI = RingtoneManager
                .getActualDefaultRingtoneUri(mContext, RingtoneManager.TYPE_ALARM);
        Log.e("!!!!", "default alarm uri="+ DEFAULT_ALARM_URI);
        settings.setSoundUri(DEFAULT_ALARM_URI);
        settings.setSoundTitle("sound title");
        return settings;
    }

    @Inject
    public SettingsRepositoryImpl(Context context) {
        mContext = context;
        preferences = new BinaryPreferencesBuilder(context)
                .registerPersistable(AlarmSettings.KEY, AlarmSettings.class)
                .build();

        settings = preferences.getPersistable(AlarmSettings.KEY, null);
        if (settings == null) {
            settings = AlarmSettings.defaultInstance();
            Uri DEFAULT_ALARM_URI = RingtoneManager
                    .getActualDefaultRingtoneUri(context, RingtoneManager.TYPE_ALARM);
            Log.e("!!!!", "default alarm uri="+ DEFAULT_ALARM_URI);
            settings.setSoundUri(DEFAULT_ALARM_URI);
            saveData();
        }

        observableSettings = new ObservableSettings(preferences, settings);
    }

    @Override
    public void setVolumeLevel(int volume) {
        Timber.d("save volume=" + volume);
        getAlarmSettings().setVolumeLevel(volume);
        saveData();
    }

    @Override
    public void setReduceSoundLevel(int level) {
        Timber.d("save reduce sound level=" + level);
        getAlarmSettings().setReduceSoundLevel(level);
        saveData();
    }

    @Override
    public void setReduceSoundEnable(boolean isEnable) {
        getAlarmSettings().setReduceSoundEnable(isEnable);
        saveData();
    }

    @Override
    public void setQuestionsCount(int count) {
        getAlarmSettings().setQuestionsCount(count);
        saveData();
    }

    @Override
    public void setVibroEnable(boolean isEnable) {
        getAlarmSettings().setVibroEnable(isEnable);
        saveData();
    }

    @Override
    public void setSoundUri(Uri uri) {
        settings = getAlarmSettings();
        settings.setSoundUri(uri);
        // gets song title from mediaStore db
        Cursor cursor = mContext.getContentResolver().query(uri,
                new String[] {MediaStore.Audio.Media.TITLE},
                null, null, null);
        try {
            cursor.moveToFirst();
            String title = cursor.getString(0);
            settings.setSoundTitle(title);
            Timber.d("save sound uri=%s and title=%s", uri, title);
        } catch (Exception e) {
            Timber.e(e);
        } finally {
            cursor.close();
        }
        saveData();
    }

    @Override
    public void setAutoTurnOfTime(int minutes) {
        getAlarmSettings().setAutoTurnOfTime(minutes);
        saveData();
    }

    private void saveData() {
        preferences.edit()
                .putPersistable(AlarmSettings.KEY, settings)
                .apply();
    }
}
