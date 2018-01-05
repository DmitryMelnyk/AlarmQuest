package com.dmelnyk.alarmquest.alarmmanager;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.dmelnyk.alarmquest.application.App;
import com.dmelnyk.alarmquest.db.AppDatabase;
import com.dmelnyk.alarmquest.db.entity.AlarmEntity;
import com.dmelnyk.alarmquest.model.Alarm;
import com.dmelnyk.alarmquest.utils.AudioService;
import com.dmelnyk.alarmquest.utils.DayConverterUtil;
import com.evernote.android.job.Job;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * Created by d264 on 12/25/17.
 */

public class AlarmWakeJob extends Job {

    public static final String JOB_KEY = "AlarmWakeJob";
    private String tag = getClass().getSimpleName();
    private final long DAY_IN_MILLIS = 86400 * 1000;

    @Override
    @NonNull
    protected Result onRunJob(Params params) {
        Timber.d("Alarm with id=" + params.getId() + ", tag=" + params.getTag() + " started.");

        Alarm alarm = App.get(getContext()).getRepository().getAlarm(params.getTag());
        Log.e("!!!!", "alarm=" + alarm);
        if (alarm.getDays().isEmpty()) {
            startAlarm();
            alarm.setEnable(false);
            App.get(getContext()).getRepository().updateAlarm(alarm);
        } else {
            int today = DayConverterUtil.getCurrentDay(); // return 0 - 6
            // : starts alarm music
            if (alarm.getDays().contains(String.valueOf(today))) {
                startAlarm();
            }
            createRepeatingAlarm(alarm.getId());
        }

        return Result.SUCCESS;
    }

    private void createRepeatingAlarm(String tag) {
        AlarmJobCreator.scheduleAlarmAtTime(DAY_IN_MILLIS, tag);
    }

    private void startAlarm() {
        Intent startIntent = new Intent(getContext(), AudioService.class);
        startIntent.setAction(AudioService.PLAY);
        getContext().startService(startIntent);
    }

    @Override
    protected void onReschedule(int newJobId) {
        // the rescheduled job has a new ID
        // override job id in database
    }
}
