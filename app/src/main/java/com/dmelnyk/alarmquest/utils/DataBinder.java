package com.dmelnyk.alarmquest.utils;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.util.Log;
import android.widget.TextView;

import com.dmelnyk.alarmquest.R;
import com.dmelnyk.alarmquest.model.Alarm;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;


/**
 * Created by d264 on 12/23/17.
 */

public final class DataBinder {

    private DataBinder() {
        /* NOP */
    }

    @BindingAdapter("daysText")
    public static void setDateText(TextView textView, Alarm alarm) {
        Context context = textView.getContext();
        String pattern = "[[0-6]\\s]+";
        String[] numericDays = context.getResources().getStringArray(R.array.days);
        String convertedText = "ConvertedText";

        Log.e("!!!!", "base alarm days=" + alarm.getDays());
        if (alarm.getDays().isEmpty()) {
            convertedText = isAlarmToday(alarm.getTime())
                    ? numericDays[8]
                    : numericDays[9];
        } else {

            String rawDays = alarm.getDays();
            if (rawDays.contains("0 1 2 3 4 5 6")) { // "0 1 ... 6" -> length = 7 numbers + 6 spaces
                convertedText = numericDays[7]; // repeat alarm 'every day'
            } else if (Pattern.compile(pattern).matcher(rawDays).find()) {
                convertedText = DayConverterUtil.convertDayToWordView(numericDays, rawDays);
            } else {
                throw new Error("wrong day value");
            }
        }

        textView.setText(convertedText);
    }

    private static boolean isAlarmToday(String time) {
        return getAlarmTime(time).getTimeInMillis() > getCurrentTime().getTimeInMillis();
    }

    private static Calendar getAlarmTime(String time) {
        Calendar calendar = getCurrentTime();
        int hour = Integer.valueOf(time.split(":")[0]);
        int minute = Integer.valueOf(time.split(":")[1]);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        return calendar;
    }

    private static Calendar getCurrentTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        return calendar;
    }
}
