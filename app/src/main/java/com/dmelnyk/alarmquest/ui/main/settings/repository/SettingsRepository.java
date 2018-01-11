package com.dmelnyk.alarmquest.ui.main.settings.repository;

import android.arch.lifecycle.LiveData;
import android.net.Uri;

import com.dmelnyk.alarmquest.ui.main.settings.model.AlarmSettings;

import java.util.Observable;

/**
 * Created by d264 on 1/8/18.
 */

public interface SettingsRepository {
//    int getReduceSoundLevel();
//    boolean isReduceSoundEnable();
//    int getQuestionsCount();
//    boolean isVibroEnable();
//    String getSoundUri();
//    int getAutoTurnOfTime();

    void setVolumeLevel(int volume);
    void setReduceSoundLevel(int level);
    void setReduceSoundEnable(boolean isEnable);
    void setQuestionsCount(int count);
    void setVibroEnable(boolean isEnable);
    void setSoundUri(Uri uri);
    void setAutoTurnOfTime(int minutes);

    public Observable getSettings();

    AlarmSettings getAS();
}
