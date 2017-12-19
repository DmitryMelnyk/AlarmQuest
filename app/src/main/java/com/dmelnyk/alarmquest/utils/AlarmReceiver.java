package com.dmelnyk.alarmquest.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import timber.log.Timber;

/**
 * Created by d264 on 6/14/17.
 */

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Timber.d("receiver starts!");

        // TODO: create new
//        PendingIntent pendingIntent = PendingIntent.getActivity(
//                context, 0, new Intent(context, AlarmQuestActivity.class), 0);
//
//        NotificationCompat.Builder mBuilder =
//                new NotificationCompat.Builder(context)
//                        .setSmallIcon(R.drawable.ic_alarm_notification)
//                        .setContentTitle("My notification")
//                        .setContentText("Hello World!")
//                        .setAutoCancel(true)
//                        .setContentIntent(pendingIntent);
//
//        int mId = 100001;
//        ((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE))
//                .notify(mId, mBuilder.build());

        // TODO: start alarm mysic
//        AudioService.startPlaying(context);
        Intent startIntent = new Intent(context, AudioService.class);
        startIntent.setAction(AudioService.PLAY);
        context.startService(startIntent);
    }
}
