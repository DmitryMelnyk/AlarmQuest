package com.dmelnyk.alarmquest.ui.main.settings.viewmodel;

import android.net.Uri;

/**
 * Created by d264 on 1/8/18.
 */

public interface SettingsViewModel {

    void saveMelody(Uri uri);

    void saveAutoTurnOf(int minutes);

    void saveUseVibration(boolean enable);

    void saveReducedVolumeLevel(int percentage);

    void saveReduceVolumeEnable(boolean enable);

    void saveQuestionsCount(int count);
}
