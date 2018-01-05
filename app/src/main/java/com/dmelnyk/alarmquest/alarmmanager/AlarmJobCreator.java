package com.dmelnyk.alarmquest.alarmmanager;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobCreator;
import com.evernote.android.job.JobManager;
import com.evernote.android.job.JobRequest;

/**
 * Created by d264 on 12/25/17.
 */

public class AlarmJobCreator implements JobCreator {

    public static final String DefaultTaskTag = "DefaultTaskTag";
    public static final String tag = "AlarmJobCreator";
    private static final long DAY_LONG = 86400000;

    @Nullable
    @Override
    public Job create(@NonNull String tag) {
        return new AlarmWakeJob();
    }

    public static int scheduleAlarmAtTime(long atTime, String tag) {
        if (atTime == 0) atTime = DAY_LONG;
        int jobId = new JobRequest.Builder(tag)
                .setExact(atTime)
                .setUpdateCurrent(true)
                .build()
                .schedule();

        Log.d(tag, "Job with id=" + jobId + " created!");
        return jobId;
    }

    public static void cancelJob(int id) {
        JobManager.instance().cancel(id);
    }

    public static void cancelJob(String tag) {
        JobManager.instance().cancelAllForTag(tag);
    }
}
