package com.dmelnyk.alarmquest.ui.alarm.business;

import com.dmelnyk.alarmquest.model.QuestionData;

/**
 * Created by dmitry on 23.05.17.
 */
public interface QuestInteractor {
    QuestionData[] getQuestions(int count);
}
