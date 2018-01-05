package com.dmelnyk.alarmquest.utils;

import android.content.Context;

import com.dmelnyk.alarmquest.R;

import javax.inject.Inject;

/**
 * Created by d264 on 1/3/18.
 */

public class TimeConverterUtil {
    private Context mContext;

    @Inject
    public TimeConverterUtil(Context context) {
        mContext = context;
    }

    public String getFormattedTime(int hours, int minutes) {
        if (hours < 1) {
            return String.format(
                    mContext.getString(R.string.time_to_alarm_minutes), minutes);
        } else return String.format(
                mContext.getString(R.string.time_to_alarm_hours_minutes), hours, minutes);
    }
}
