package com.dmelnyk.alarmquest.db.converter;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

/**
 * Created by d264 on 12/21/17.
 */

public class DataConverter {
    @TypeConverter
    public static Date toDate(Long timestamp) {
        return timestamp == null ? null : new Date(timestamp);
    }

    @TypeConverter
    public static Long toTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }


}
