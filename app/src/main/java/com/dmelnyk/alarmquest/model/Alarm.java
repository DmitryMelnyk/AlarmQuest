package com.dmelnyk.alarmquest.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by d264 on 12/21/17.
 */

public interface Alarm extends Parcelable {
    String getId();
    String getTime();
    String getDays();
    boolean isEnable();

    void setTime(String time);
    void setDays(String days);
    void setEnable(boolean isEnable);

    @Override
    int describeContents();

    @Override
    void writeToParcel(Parcel parcel, int i);
}
