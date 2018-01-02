package com.dmelnyk.alarmquest.alarmmanager;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.dmelnyk.alarmquest.utils.AudioService;
import com.evernote.android.job.Job;

/**
 * Created by d264 on 12/25/17.
 */

class AlarmWakeJob extends Job {

    private String tag = getClass().getSimpleName();

    @Override
    @NonNull
    protected Result onRunJob(Params params) {
        Log.d(tag, "Alarm with id=" + params.getId() + ", tag=" + params.getTag() + " started.");
        // todo: check


        // : starts alarm music
        Intent startIntent = new Intent(getContext(), AudioService.class);
        startIntent.setAction(AudioService.PLAY);
        getContext().startService(startIntent);
        return Result.SUCCESS;

        // something strange happened, try again later
//        return Result.RESCHEDULE;
    }

    @Override
    protected void onReschedule(int newJobId) {
        // the rescheduled job has a new ID
        // override job id in database
    }
}
