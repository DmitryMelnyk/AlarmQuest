package com.dmelnyk.alarmquest.db;

import android.util.Log;

import com.dmelnyk.alarmquest.db.entity.AlarmEntity;
import com.dmelnyk.alarmquest.model.Alarm;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by d264 on 12/21/17.
 */

public class DataGenerator {
    private static final String[] FIRST = new String[]{"16:30", "0 1 4", "true"};
    private static final String[] SECOND = new String[]{"8:00", "1", "true"};
    private static final String[] THIRD = new String[]{"7:40", "0 5 6", "false"};


    public static List<AlarmEntity> generateAlarms() {
        List<AlarmEntity> alarms = new ArrayList<>();

        alarms.add(createAlarm(FIRST));
        alarms.add(createAlarm(SECOND));
        alarms.add(createAlarm(THIRD));

        Log.e("!!!", "Alarm generated=" + alarms);
        return alarms;
    }

    private static AlarmEntity createAlarm(String[] data) {
        AlarmEntity alarm = new AlarmEntity();
        alarm.setTime(data[0]);
        alarm.setDays(data[1]);
        alarm.setEnable(Boolean.valueOf(data[2]));

        return alarm;
    }
}
