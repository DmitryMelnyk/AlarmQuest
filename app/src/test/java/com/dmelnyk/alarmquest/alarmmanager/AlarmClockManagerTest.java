package com.dmelnyk.alarmquest.alarmmanager;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by d264 on 12/25/17.
 */
public class AlarmClockManagerTest {

    TimeCalcUtil manager;

    @Before
    public void setUp() {
        manager = new TimeCalcUtil();
    }

    @Test
    public void getCurrentTime() throws Exception {

    }

    @Test
    public void getAlarmTime() throws Exception {
    }

    @Test
    public void getTimeToAlarm() throws Exception {
        String time = manager.timeToAlarm("6:00");
        System.out.println(time);
    }
}