package com.dmelnyk.alarmquest.ui.main.core;

import android.content.Context;
import android.util.Log;

import com.dmelnyk.alarmquest.R;
import com.dmelnyk.alarmquest.db.entity.AlarmEntity;
import com.dmelnyk.alarmquest.model.Alarm;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by d264 on 12/22/17.
 */

public class DayConverter {

//    private static DayConverter sInstance;
//
//    private static String[] sDays;
//
//    private DayConverter(Context context) {
//        sDays = context.getResources().getStringArray(R.array.days);
//    }
//
//    public static DayConverter getInstance(Context context) {
//        if (sInstance == null) {
//            sInstance = new DayConverter(context);
//        }
//
//        return sInstance;
//    }
//
//    public List<? extends Alarm> convertAlarmToView(List<? extends Alarm> alarms) {
//        for (Alarm alarm : alarms) {
//            alarm = convertAlarmToView(alarm);
//        }
//
//        return alarms;
//    }
//
//    public List<? extends Alarm> convertAlarmToDb(List<? extends Alarm> alarms) {
//        for (Alarm alarm : alarms) {
//            alarm = convertAlarmToDb(alarm);
//        }
//
//        return alarms;
//    }
//
//    public Alarm convertAlarmToDb(Alarm alarm) {
//        String days = alarm.getDays();
//        String rawDays = "";
//
//        for (int i = 0; i < sDays.length; i++) {
//            if (days.contains(sDays[i])) {
//                rawDays += i + " ";
//            }
//        }
//        rawDays = rawDays.substring(0, rawDays.length() -1);
//        alarm.setDays(rawDays);
//        return alarm;
//    }
//
//    public Alarm convertAlarmToView(Alarm alarm) {
//        String[] days = alarm.getDays().split(" ");
//        String newDays = "";
//        for (String day : days) {
//            newDays += sDays[Integer.valueOf(day)] + " ";
//        }
//
//        alarm.setDays(newDays);
//        return alarm;
//    }

    public static Integer[] convertDayToNumberView(String[] dayArray, String days) {
        List<Integer> numberArray = new ArrayList<>();
        for (int i =0 ; i < dayArray.length; i++) {
            if (days.contains(dayArray[i])){
                numberArray.add(i);
            }
        }
        return numberArray.toArray(new Integer[numberArray.size()]);
    }

    public static Alarm convertToDbLook(Alarm alarm, String[] dayArray) {
        Log.e("!!!", "convertToDbLook(). Alarm=" + alarm);
        Integer[] rawDaysArray = (
                convertDayToNumberView(dayArray, alarm.getDays()));

        String rawDays = "";
        for (Integer day : rawDaysArray) {
            rawDays += day + " ";
        }
        if (rawDays.length() > 1) {
            rawDays = rawDays.substring(0, rawDays.length() - 1); // removes last space (" ")
        }

        Alarm newAlarm = new AlarmEntity(alarm);
        newAlarm.setDays(rawDays);
        return newAlarm;
    }

    public static String convertDayToWordView(String[] days, String rawDays) {
        Log.e("!!!", "convertDayToWordView. rawDays=" + rawDays + "<");

        String[] numberDaysArr = rawDays.split(" ");
        String convertedResult = "";

        for (String numberDay : numberDaysArr) {
            convertedResult += days[Integer.valueOf(numberDay)] + " ";
        }
        return convertedResult;
    }
}
