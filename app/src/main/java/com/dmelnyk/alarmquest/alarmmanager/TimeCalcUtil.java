package com.dmelnyk.alarmquest.alarmmanager;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by d264 on 12/25/17.
 */

public class TimeCalcUtil {

    // Time format "7:40"

    protected Calendar getCurrentTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        return calendar;
    }

    protected Calendar getAlarmTime(String time) {
        Calendar calendar = Calendar.getInstance();
        int hour = Integer.valueOf(time.split(":")[0]);
        int minute = Integer.valueOf(time.split(":")[1]);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        return calendar;
    }

    public long getTimeToAlarmMillis(String time) {
        long timeBeforeAlarm;
        if (getCurrentTime().before(getAlarmTime(time))) {
            timeBeforeAlarm = getAlarmTime(time).getTimeInMillis()
                    - getCurrentTime().getTimeInMillis();
        } else {
            timeBeforeAlarm = TimeUnit.DAYS.toMillis(1)
                    - (getCurrentTime().getTimeInMillis() - getAlarmTime(time).getTimeInMillis() );
        }

        return timeBeforeAlarm;
    }

    public String timeToAlarm(String time) {
        double hoursToAlarm = getTimeToAlarmMillis(time) / 3600000;
        double minutesToAlarm = getTimeToAlarmMillis(time) % 3600000 / 60000;
        String timeToAlarm = "Hours=" + hoursToAlarm + ", minutes=" + minutesToAlarm;
        return timeToAlarm;
    }
}
