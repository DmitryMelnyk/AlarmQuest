package com.dmelnyk.alarmquest.data.navigation.fragments.alarmclock;

import org.jetbrains.annotations.NotNull;

/**
 * Created by d264 on 6/13/17.
 */

public interface ITimeRepository {

    /**
     * Saves alarm time to SharedPreferences
     *
     * @param time alarm time
     */
    void saveAlarmTime(String time);

    /**
     * Restore saved time from SharedPreferences or 7:00 as default
     *
     * @return saved time or '6:00' as default
     */
    @NotNull
    String getSavedAlarm();


    /**
     * Restore saved alarm enable state from SharedPreferences
     * @return saved state or false
     */
    @NotNull
    boolean getSavedAlarmEnableState();

    /**
     * Saves alarm enable state to SharedPreferences
     */
    void saveAlarmEnableSate(boolean state);
}
