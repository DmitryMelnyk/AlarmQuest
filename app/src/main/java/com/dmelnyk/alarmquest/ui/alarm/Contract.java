package com.dmelnyk.alarmquest.ui.alarm;

import com.dmelnyk.alarmquest.model.QuestionData;

import java.util.List;

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

        void setQuestions(List<String> titles, QuestionData[] questions);
    }

    public interface IAlarmQuestPresenter {
        void bindView(IAlarmQuestView view, int questionToSolveCount);
        void unbindView();
        void isAnswerCorrect(boolean isCorrect);

        void questionSwiped();
    }
}
