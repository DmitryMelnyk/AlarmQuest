package com.dmelnyk.alarmquest.ui;

import android.content.Context;
import android.content.Intent;

import com.dmelnyk.alarmquest.ui.main.MainActivity;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by d264 on 12/23/17.
 */

@Singleton
public final class Navigator {

    @Inject
    Navigator() { /* NOP */}

    public void toMainActivity(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }
}
