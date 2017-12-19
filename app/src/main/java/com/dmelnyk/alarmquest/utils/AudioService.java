package com.dmelnyk.alarmquest.utils;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.net.Uri;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import com.dmelnyk.alarmquest.R;
import com.dmelnyk.alarmquest.ui.alarm.AlarmQuestActivity;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.LoopingMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class AudioService extends Service {
    static final int NOTIFICATION_ID = 543;
    public static final String PLAY = "play";
    public static final String STOP = "stop";
    public static final String DECREASE_VOLUME = "decrease volume";

    public static boolean isServiceRunning = false;
    private SimpleExoPlayer player;

    @Override
    public void onCreate() {
        super.onCreate();
        startServiceWithNotification();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null && intent.getAction().equals(PLAY)) {
            startServiceWithNotification();
        } else if (intent != null
                && intent.getAction().equals(DECREASE_VOLUME)
                && player != null) {
            player.setVolume(0.3f);
        } else {
            stopMyService();
        }
        return START_STICKY;
    }

    // In case the service is deleted or crashes some how
    @Override
    public void onDestroy() {
        isServiceRunning = false;
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // Used only in case of bound services.
        return null;
    }


    void startServiceWithNotification() {
        if (isServiceRunning) return;
        isServiceRunning = true;

        Intent notificationIntent = new Intent(getApplicationContext(), AlarmQuestActivity.class);
        notificationIntent.setAction(PLAY);  // A string containing the action name
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent contentPendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);

        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle(getResources().getString(R.string.app_name))
                .setTicker(getResources().getString(R.string.app_name))
                .setContentText(getResources().getString(R.string.app_name))
                .setSmallIcon(R.drawable.ic_alarm_notification)
                .setLargeIcon(Bitmap.createScaledBitmap(icon, 128, 128, false))
                .setContentIntent(contentPendingIntent)
                .setOngoing(true)
//                .setDeleteIntent(contentPendingIntent)  // if needed
                .build();
        notification.flags = notification.flags | Notification.FLAG_NO_CLEAR;     // NO_CLEAR makes the notification stay when the user performs a "delete all" command
        startForeground(NOTIFICATION_ID, notification);

        playAudio();
    }

    private void playAudio() {
        Uri mediaUri = Uri.parse("asset:///sms.mp3");

        player = ExoPlayerFactory.newSimpleInstance(
                getApplicationContext(),
                new DefaultTrackSelector(),
                new DefaultLoadControl()
        );

        String userAgent = Util.getUserAgent(this, "ClassicalMusicQuiz");
        MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                this, userAgent), new DefaultExtractorsFactory(), null, null);

        LoopingMediaSource loopingMediaSource = new LoopingMediaSource(mediaSource, 100);
        player.setAudioStreamType(AudioManager.STREAM_ALARM);
        player.prepare(loopingMediaSource);
        player.setVolume(0.5f);
        player.setPlayWhenReady(true);
    }

    private void stopAudio() {
        if (player != null) {
            player.stop();
            player.release();
        }
    }

    void stopMyService() {
        stopAudio();
        stopForeground(true);
        stopSelf();
        isServiceRunning = false;
    }
}
