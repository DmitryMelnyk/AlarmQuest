package com.dmelnyk.alarmquest.ui.main.core;

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
            convertedResult += days[Integer.valueOf(numberDay)] + " ";
        }
        return convertedResult;
    }
}
