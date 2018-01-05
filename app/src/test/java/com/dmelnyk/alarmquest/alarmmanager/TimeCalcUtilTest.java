package com.dmelnyk.alarmquest.alarmmanager;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.inject.Inject;

import static org.junit.Assert.*;

/**
 * Created by d264 on 1/4/18.
 */
public class TimeCalcUtilTest {

    @Inject
    TimeCalcUtil timeCalcUtil;

    @Before
    public void setTime() {
//        afterMidnight = timeCalcUtil.getAlarmTime("1:30");
//        beforeMidnight = timeCalcUtil.getAlarmTime("1:30");
//        timeNow = timeCalcUtil.getCurrentTime().getTimeInMillis();
    }

    @Test
    public void isAlarmToday() throws Exception {
    }

}