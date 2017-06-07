package com.dmelnyk.alarmquest.ui.alarm;

import com.dmelnyk.alarmquest.business.alarm.model.QuestionData;

/**
 * Created by d264 on 6/7/17.
 */

public class Contract {

    public interface IAlarmQuestView {
        void updateLeftToAnswerQuestionsCounter(String n);
        // Set/update question in QuestionFragment
        void setQuestion(QuestionData question);
        // Called after successfully answering for all questions
        void success();

        void setBubbleTitles(String[] titles);

        void showHasAnsweredMessage();
    }

    public interface IAlarmQuestPresenter {
        void bindView(IAlarmQuestView view);
        void unbindView();
        void pickQuestion(String questionTitle);
        void isAnswerCorrect(boolean isCorrect);
    }
}
