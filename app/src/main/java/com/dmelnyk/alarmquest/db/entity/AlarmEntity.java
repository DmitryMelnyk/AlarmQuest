package com.dmelnyk.alarmquest.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.dmelnyk.alarmquest.model.Alarm;

import java.util.UUID;

/**
 * Created by d264 on 12/21/17.
 */

@Entity(tableName = "alarms",
        primaryKeys = {"id"}
)
public class AlarmEntity implements Alarm {

    @NonNull
    @ColumnInfo(name = "id")
    private String id;

    @ColumnInfo(name = "time")
    private String time;

    @NonNull
    @ColumnInfo(name = "days")
    private String days;

    @ColumnInfo(name = "isEnable")
    private boolean isEnable;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getTime() {
        return time;
    }

    @Override
    public String getDays() {
        return days;
    }

    @Override
    public boolean isEnable() {
        return isEnable;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public void setEnable(boolean enable) {
        isEnable = enable;
    }

    public AlarmEntity() {
        this.id = UUID.randomUUID().toString();
        this.days = "";
    }

    public AlarmEntity(String id, String time, String days, boolean isEnable) {
        this.id = id;
        this.time = time;
        this.days = days;
        this.isEnable = isEnable;
    }

    public AlarmEntity(Alarm alarm) {
        this.id = alarm.getId();
        this.time = alarm.getTime();
        this.days = alarm.getDays();
        this.isEnable = alarm.isEnable();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(time);
        parcel.writeString(days);
        parcel.writeInt(isEnable ? 1 : 0);
    }

    public static final Parcelable.Creator<AlarmEntity> CREATOR
            = new Parcelable.Creator<AlarmEntity>() {
        public AlarmEntity createFromParcel(Parcel in) {
            return new AlarmEntity(in);
        }

        public AlarmEntity[] newArray(int size) {
            return new AlarmEntity[size];
        }
    };

    private AlarmEntity(Parcel in) {
        id = in.readString();
        time = in.readString();
        days = in.readString();
        isEnable = in.readInt() == 1 ? true : false;
    }

    @Override
    public boolean equals(Object obj) {
        Alarm alarm = (Alarm) obj;
        return getId().equals(alarm.getId())
                && isEnable() == isEnable()
                && getDays().equals(alarm.getDays())
                && getTime().equals(alarm.getTime());
    }

    @Override
    public String toString() {
        return "AlarmEntity{" +
                "id='" + id + '\'' +
                ", time='" + time + '\'' +
                ", days='" + days + '\'' +
                '}';
    }
}
