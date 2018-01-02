package com.dmelnyk.alarmquest.ui.questfragment;

import com.dmelnyk.alarmquest.business.alarm.model.QuestionData;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * Created by dmitry on 23.05.17.
 */
public class QuestPresenter implements Contract.IQuestPresenter {

    private Contract.IQuestView view;
    private QuestionData questionData;
    private int correctAnswer;

    @Inject
    public QuestPresenter() {
    }

    @Override
    public void bindView(Contract.IQuestView view) {
        this.view = view;
    }

    @Override
    public void unBindView() {
        view = null;
    }

    @Override
    public void onButtonClick(int answerNum) {

//        boolean isCorrectAnswer = answerNum == questionData.getCorrectAnswer();
        boolean isCorrectAnswer = answerNum == correctAnswer;
        Timber.d("isCorrect = " + isCorrectAnswer);

        view.enableAllButtons(false);
        if (isCorrectAnswer) {
            view.showCorrectAnswer(answerNum);
        } else {
            view.showWrongAnswer(answerNum);
            view.showCorrectAnswer(questionData.getCorrectAnswer());
        }

        view.callbackListen(isCorrectAnswer);
    }

    @Override
    public void takeQuestion(QuestionData question) {
        questionData = question;
        correctAnswer = question.getCorrectAnswer();

        view.enableAllButtons(true);
        view.setDataQuestion(question.getQuestion(), question.getAnswers());
    }
}
