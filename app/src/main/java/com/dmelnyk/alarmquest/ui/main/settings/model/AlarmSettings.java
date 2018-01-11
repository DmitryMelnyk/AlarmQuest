package com.dmelnyk.alarmquest.ui.main.settings.model;

import android.net.Uri;

import com.ironz.binaryprefs.serialization.serializer.persistable.Persistable;
import com.ironz.binaryprefs.serialization.serializer.persistable.io.DataInput;
import com.ironz.binaryprefs.serialization.serializer.persistable.io.DataOutput;

/**
 * Created by d264 on 1/8/18.
 */

public final class AlarmSettings implements Persistable {

    public static final String KEY = "AlarmSettings.KEY";

    private static final int DEFAULT_VOLUME_LEVEL = 80;
    private static final int DEFAULT_REDUCE_SOUND = 30;
    private static final boolean DEFAULT_REDUCE_SOUND_ENABLE = true;
    private static final int DEFAULT_QUESTIONS_COUNT = 2;
    private static final boolean DEFAULT_VIBRO = false;
    private static final int DEFAULT_AUTO_TURN_OFF = 5; // 5 minutes

    private int volumeLevel;
    private int reduceSoundLevel;
    private boolean isReduceSoundEnable;
    private int questionsCount;
    private boolean isVibroEnable;
    private String soundUri;
    public String soundTitle;
    private int autoTurnOfTime;

    public void setVolumeLevel(int volumeLevel) {
        this.volumeLevel = volumeLevel;
    }

    public void setReduceSoundLevel(int reduceSoundLevel) {
        this.reduceSoundLevel = reduceSoundLevel;
    }

    public void setReduceSoundEnable(boolean reduceSoundEnable) {
        isReduceSoundEnable = reduceSoundEnable;
    }

    public void setQuestionsCount(int questionsCount) {
        this.questionsCount = questionsCount;
    }

    public void setVibroEnable(boolean vibroEnable) {
        isVibroEnable = vibroEnable;
    }

    public void setSoundUri(Uri uri) {
        this.soundUri = uri == null ? null : uri.toString();
    }

    public void setSoundTitle(String title) {
        this.soundTitle = title;
    }

    public void setAutoTurnOfTime(int autoTurnOfTime) {
        this.autoTurnOfTime = autoTurnOfTime;
    }


    public int getVolumeLevel() {
        return volumeLevel;
    }

    public int getReduceSoundLevel() {
        return reduceSoundLevel;
    }

    public boolean isReduceSoundEnable() {
        return isReduceSoundEnable;
    }

    public int getQuestionsCount() {
        return questionsCount;
    }

    public boolean isVibroEnable() {
        return isVibroEnable;
    }

    public String getSoundUri() {
        return soundUri;
    }

    public String getSoundTitle() {
        return soundTitle;
    }

    public int getAutoTurnOfTime() {
        return autoTurnOfTime;
    }

    @Override
    public void writeExternal(DataOutput dataOutput) {
        dataOutput.writeInt(volumeLevel);
        dataOutput.writeInt(reduceSoundLevel);
        dataOutput.writeBoolean(isReduceSoundEnable);
        dataOutput.writeInt(questionsCount);
        dataOutput.writeBoolean(isVibroEnable);
        dataOutput.writeString(soundUri);
        dataOutput.writeString(soundTitle);
        dataOutput.writeInt(autoTurnOfTime);
    }

    @Override
    public void readExternal(DataInput dataInput) {
        volumeLevel = dataInput.readInt();
        reduceSoundLevel = dataInput.readInt();
        isReduceSoundEnable = dataInput.readBoolean();
        questionsCount = dataInput.readInt();
        isVibroEnable = dataInput.readBoolean();
        soundUri = dataInput.readString();
        soundTitle = dataInput.readString();
        autoTurnOfTime = dataInput.readInt();
    }

    @Override
    public Persistable deepClone() {
        AlarmSettings settings = new AlarmSettings();
        settings.setReduceSoundLevel(DEFAULT_REDUCE_SOUND);
        settings.setReduceSoundEnable(DEFAULT_REDUCE_SOUND_ENABLE);
        settings.setQuestionsCount(DEFAULT_QUESTIONS_COUNT);
        settings.setVibroEnable(DEFAULT_VIBRO);
        settings.setAutoTurnOfTime(DEFAULT_AUTO_TURN_OFF);
        settings.setSoundUri(null);
        settings.setSoundTitle(null);
        return settings;
    }

    public static AlarmSettings defaultInstance() {
        AlarmSettings settings = new AlarmSettings();
        settings.setVolumeLevel(DEFAULT_VOLUME_LEVEL);
        settings.setReduceSoundLevel(DEFAULT_REDUCE_SOUND);
        settings.setReduceSoundEnable(DEFAULT_REDUCE_SOUND_ENABLE);
        settings.setQuestionsCount(DEFAULT_QUESTIONS_COUNT);
        settings.setVibroEnable(DEFAULT_VIBRO);
        settings.setAutoTurnOfTime(DEFAULT_AUTO_TURN_OFF);
        settings.setSoundUri(null);
        settings.setSoundTitle(null);
        return settings;
    }


}
