package com.dmelnyk.alarmquest.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

/**
 * Created by d264 on 6/14/17.
 */

public class AlarmClockUtil {
    private Context mContext;
    private AlarmManager alarmManager;
    private PendingIntent alarmIntent;

    public AlarmClockUtil(Context mContext) {
        this.mContext = mContext;
        alarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(mContext, AlarmReceiver.class);
        alarmIntent = PendingIntent.getBroadcast(mContext, 0, intent, 0);
    }


    /**
     * Creates pending alarm using given time
     *
     * @param time The alarm's time in format 'hours:minutes'
     */
    public void startAlarmClock(String time) {
        String[] hourMinute = time.split(":"); // hourMinute[0]

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hourMinute[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(hourMinute[1]));

//        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
//                AlarmManager.INTERVAL_DAY, alarmIntent);
    }

    /**
     * Cancels pending alarm intent
     */
    public void stopAlarmClock() {
        if (alarmManager != null) {
            alarmManager.cancel(alarmIntent);
        }
    }
}
