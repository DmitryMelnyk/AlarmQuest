package com.dmelnyk.alarmquest.ui.questfragment;

import android.support.annotation.NonNull;

import com.dmelnyk.alarmquest.business.alarm.model.QuestionData;

/**
 * Created by dmitry on 23.05.17.
 */

public class Contract {

    public interface IQuestView {
        void setDataQuestion(@NonNull String question, @NonNull String[] answers);

        void enableAllButtons(boolean isEnabled);

        void showCorrectAnswer(int num);

        void showWrongAnswer(int num);

        void finishActivity();

        void callbackListen(boolean isCorrectAnswer);
    }

    public interface IQuestPresenter {
        void bindView(@NonNull IQuestView view);
        void unBindView();

        void onButtonClick(int answerNum);

        void takeQuestion(QuestionData question);
    }
}
