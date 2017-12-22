package com.dmelnyk.alarmquest.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.dmelnyk.alarmquest.db.entity.AlarmEntity;
import com.dmelnyk.alarmquest.model.Alarm;

import java.util.List;

/**
 * Created by d264 on 12/21/17.
 */

@Dao
public interface AlarmDao {
    @Query("SELECT * FROM alarms")
    LiveData<List<AlarmEntity>> loadAllAlarms();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<AlarmEntity> alarms);

    @Query("SELECT * from alarms WHERE id = :alarmId")
    LiveData<AlarmEntity> loadAlarm(int alarmId);

    @Query("SELECT * from alarms WHERE id = :alarmId")
    AlarmEntity loadAlarmSync(int alarmId);

    @Delete
    void delete(AlarmEntity alarm);

    @Update
    void update(AlarmEntity alarm);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void add(AlarmEntity... alarm);
}
