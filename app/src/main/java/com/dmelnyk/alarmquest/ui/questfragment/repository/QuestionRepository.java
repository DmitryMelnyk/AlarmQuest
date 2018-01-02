package com.dmelnyk.alarmquest.ui.questfragment.repository;

/**
 * Created by dmitry on 23.05.17.
 */
public interface QuestionRepository {

    QuestionBlock getQuestion();
    QuestionBlock[] getQuestions(int count);
}
