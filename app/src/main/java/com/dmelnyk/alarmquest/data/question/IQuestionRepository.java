package com.dmelnyk.alarmquest.data.question;

/**
 * Created by dmitry on 23.05.17.
 */
public interface IQuestionRepository {

    QuestionBlock getQuestion();
    QuestionBlock[] getQuestions(int count);
}
