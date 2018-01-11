package com.dmelnyk.alarmquest.ui.main.settings.view;

import android.os.RecoverySystem;
import android.widget.SeekBar;

/**
 * Created by d264 on 1/9/18.
 */

public abstract class CustomSeekBarListener implements SeekBar.OnSeekBarChangeListener {
    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        /* NOP */
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        /* NOP */
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        onProgressChanged(progress);
    }

    public abstract void onProgressChanged(int progress);

}
