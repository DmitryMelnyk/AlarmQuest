package com.dmelnyk.alarmquest.utils;

import java.util.Calendar;

import javax.inject.Inject;

/**
 * Created by d264 on 12/22/17.
 */

public class DayConverterUtil {

    @Inject
    public DayConverterUtil() {
    }

    public Integer[] convertDayToNumberView(String days) {
        String[] stringArray = days.split(" ");
        Integer[] numberArray = new Integer[stringArray.length];
        for (int i =0 ; i < stringArray.length; i++) {
            numberArray[i] = Integer.valueOf(stringArray[i]);
        }
        return numberArray;
    }

    public static String convertDayToWordView(String[] days, String rawDays) {
        String[] numberDaysArr = rawDays.split(" ");
        String convertedResult = "";

        for (String numberDay : numberDaysArr) {
            if (numberDay.equals("8") || numberDay.equals("9")) break;
            convertedResult += days[Integer.valueOf(numberDay)] + " ";
        }
        return convertedResult;
    }

    public static int getCurrentDay() {
        // there is shift by 2
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK) - 2;
        // Sunday = 1, but in our case = 6
        if (day == -1) day = 6;
        return day;
    }
}
