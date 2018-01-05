package com.dmelnyk.alarmquest.utils;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

import static org.junit.Assert.*;

/**
 * Created by d264 on 1/4/18.
 */
public class DayConverterUtilTest {

    @Inject
    DayConverterUtil dayConverterUtil;

    @Test
    public void textCurrentDay() {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        System.out.print("day=" + day);

        assertTrue(day == 5);
    }
}