package com.dmelnyk.alarmquest.ui.main.core;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.widget.TextView;

import com.dmelnyk.alarmquest.R;

import javax.inject.Inject;

/**
 * Created by d264 on 12/23/17.
 */

public final class DataBinder {

    private DataBinder() {
        /* NOP */
    }

    @BindingAdapter("daysText")
    public static void setDateText(TextView textView, String rawDays) {
        Context context = textView.getContext();
        String[] numericDays = context.getResources().getStringArray(R.array.days);

        String convertedText = null;
        if (rawDays.length() == 13) { // "0 1 ... 6" -> length = 7 numbers + 6 spaces
            convertedText = numericDays[7]; // repeat alarm 'every day'
        } else if (rawDays.contains("-1")){ // today
            convertedText = numericDays[8];
        } else if (rawDays.contains("-2")) {// tomorrow
            convertedText = numericDays[9];
        } else {
            convertedText = DayConverterUtil.convertDayToWordView(numericDays, rawDays);
        }

        textView.setText(convertedText);
    }
}
