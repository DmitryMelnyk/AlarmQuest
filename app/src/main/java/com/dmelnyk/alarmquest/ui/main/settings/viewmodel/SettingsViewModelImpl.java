package com.dmelnyk.alarmquest.ui.main.settings.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.net.Uri;

import com.dmelnyk.alarmquest.ui.main.settings.model.AlarmSettings;
import com.dmelnyk.alarmquest.ui.main.settings.repository.SettingsRepository;

import java.util.Observable;

import javax.inject.Inject;

/**
 * Created by d264 on 1/8/18.
 */

public class SettingsViewModelImpl extends ViewModel implements SettingsViewModel {


    private final SettingsRepository repository;

    @Inject
    public SettingsViewModelImpl(SettingsRepository repository) {
        this.repository = repository;
    }

    public Observable getSettings() {
        return repository.getSettings();
    }

    public AlarmSettings getAS() {
        return repository.getAS();
    }

    public void saveVolumeLevel(int volume) {
        repository.setVolumeLevel(volume);
    }

    @Override
    public void saveMelody(Uri uri) {
        repository.setSoundUri(uri);
    }

    @Override
    public void saveAutoTurnOf(int minutes) {}

    @Override
    public void saveUseVibration(boolean enable) {}

    @Override
    public void saveReducedVolumeLevel(int percentage) {}

    @Override
    public void saveReduceVolumeEnable(boolean enable) {}

    @Override
    public void saveQuestionsCount(int count) {}
}
