package com.dmelnyk.alarmquest.ui.main.settings.view;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatSeekBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.dmelnyk.alarmquest.R;
import com.dmelnyk.alarmquest.databinding.FragmentSettingsBinding;
import com.dmelnyk.alarmquest.ui.common.view.BaseFragment;
import com.dmelnyk.alarmquest.ui.main.settings.model.AlarmSettings;
import com.dmelnyk.alarmquest.ui.main.settings.model.ObservableSettings;
import com.dmelnyk.alarmquest.ui.main.settings.repository.SettingsRepositoryImpl;
import com.dmelnyk.alarmquest.ui.main.settings.viewmodel.SettingsViewModelImpl;

import javax.inject.Inject;

import timber.log.Timber;

import static android.app.Activity.RESULT_OK;

/**
 * Created by d264 on 1/8/18.
 */

public class SettingsFragment extends BaseFragment {

    private static final int MELODY_REQUEST_CODE = 123; // magic number
    private FragmentSettingsBinding mBinding;

    @Inject
    ViewModelProvider.Factory factory;

    private SettingsViewModelImpl viewModel;
    private AlarmSettings settings;

    private int mVolumeLevel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_settings, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this, factory)
                .get(SettingsViewModelImpl.class);

        mBinding.setCallback(mCallback);
        viewModel.getSettings().addObserver((observable, arg) -> {
            if (getContext() == null) return;
            Toast.makeText(getContext(), "Settings updated!", Toast.LENGTH_SHORT).show();
            settings = ((ObservableSettings) observable).getSettings();
            mBinding.setSettings(settings);
            Toast.makeText(getActivity(), "Melody=" + settings.getSoundTitle(), Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e("!!!", "onStop()");
//        ((ObservableSettings) viewModel.getSettings()).unregisterObservables();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == MELODY_REQUEST_CODE) {
            Uri uri = data.getData();
            if (uri != null) {
                viewModel.saveMelody(uri);
            }
            Timber.d("sound uri=" + uri);
            Log.e("!!!", "sound uri=" + uri);
        }
    }

    private SettingsCallback mCallback = new SettingsCallback() {
        @Override
        public void onVolumeClicked() {
            mVolumeLevel = settings == null ? 0 : settings.getVolumeLevel();

            MaterialDialog dialog = new MaterialDialog.Builder(getActivity())
                    .title(R.string.choose_volume)
                    .customView(R.layout.seek_bar, false)
                    .dismissListener(dialogDismissed -> {
                        viewModel.saveVolumeLevel(mVolumeLevel);
                    })
                    .show();

            ((AppCompatSeekBar) dialog.getCustomView()).setOnSeekBarChangeListener(new CustomSeekBarListener() {
                @Override
                public void onProgressChanged(int progress) {
                    mVolumeLevel = progress;
                }
            });
        }

        @Override
        public void onMelodyClicked() {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, MELODY_REQUEST_CODE, null);
        }

        @Override
        public void onAutoTurningOffClicked() {

        }

        @Override
        public void onVibroSwitched() {
            // todo remove
            mBinding.setSettings(viewModel.getAS());
        }

        @Override
        public void onSoundReduceClicked() {

        }

        @Override
        public void onSoundReduceChecked() {

        }

        @Override
        public void onQuestNumberClicked() {

        }
    };

    public interface SettingsCallback {
        void onVolumeClicked();
        void onMelodyClicked();
        void onAutoTurningOffClicked();
        void onVibroSwitched();
        void onSoundReduceClicked();
        void onSoundReduceChecked();
        void onQuestNumberClicked();
    }
}
