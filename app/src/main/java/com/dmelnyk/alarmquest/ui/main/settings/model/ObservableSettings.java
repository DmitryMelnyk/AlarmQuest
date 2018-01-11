package com.dmelnyk.alarmquest.ui.main.settings.model;

import android.content.SharedPreferences;
import android.util.Log;

import com.ironz.binaryprefs.Preferences;

import java.util.Observable;

/**
 * Created by d264 on 1/9/18.
 */

public class ObservableSettings extends Observable
        implements SharedPreferences.OnSharedPreferenceChangeListener {

    private AlarmSettings settings;

    private final Preferences preferences;

    public ObservableSettings(Preferences preferences, AlarmSettings settings) {
        this.preferences = preferences;
        preferences.registerOnSharedPreferenceChangeListener(this);
        this.settings = settings;
    }

    public AlarmSettings getSettings() {
        return settings;
    }

    public void unregisterObservables() {
        preferences.unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Log.e("!!!", "onSharedPreferenceChanged");
        settings = preferences.getPersistable(AlarmSettings.KEY, null);
        setChanged();
        notifyObservers();
    }
}
