package com.dmelnyk.alarmquest.business.alarm;

import com.dmelnyk.alarmquest.business.alarm.model.QuestionData;

/**
 * Created by dmitry on 23.05.17.
 */
public interface QuestInteractor {
    QuestionData[] getQuestions(int count);
}
