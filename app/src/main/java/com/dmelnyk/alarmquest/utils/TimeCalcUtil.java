package com.dmelnyk.alarmquest.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by d264 on 12/25/17.
 */

@Singleton
public class TimeCalcUtil {
    private final TimeConverterUtil timeConverterUtil;

    // Time format "7:40"

    @Inject
    public TimeCalcUtil(TimeConverterUtil timeConverterUtil) {
        this.timeConverterUtil = timeConverterUtil;
    }

    protected Calendar getCurrentTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        return calendar;
    }

    protected Calendar getAlarmTime(String time) {
        Calendar calendar = getCurrentTime();
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
        int hoursToAlarm = (int) (getTimeToAlarmMillis(time) / 3600000);
        int minutesToAlarm = (int) (getTimeToAlarmMillis(time) % 3600000 / 60000);
        String formattedTime = timeConverterUtil.getFormattedTime(hoursToAlarm, minutesToAlarm);
        return formattedTime;
    }

    public boolean isAlarmToday(String time) {
        return getAlarmTime(time).getTimeInMillis() > getCurrentTime().getTimeInMillis();
    }
}
