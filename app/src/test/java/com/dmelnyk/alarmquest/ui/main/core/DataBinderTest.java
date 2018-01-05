package com.dmelnyk.alarmquest.ui.main.core;

import org.junit.Test;

import java.util.regex.Pattern;

import static org.junit.Assert.*;

/**
 * Created by d264 on 1/4/18.
 */
public class DataBinderTest {

    String pattern = "[[0-6]\\s]+";

    @Test
    public void checkPatterns() {
        String days = "0 2 3 6";
        assertTrue(days.matches(pattern));
    }

    @Test
    public void checkContainsPattern() {
        String days = "0 2";
        boolean contains = Pattern.compile(pattern).matcher(days).find();

        assertTrue(contains);
    }
}