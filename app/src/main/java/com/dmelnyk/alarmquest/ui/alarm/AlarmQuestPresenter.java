package com.dmelnyk.alarmquest.ui.alarm;

import com.dmelnyk.alarmquest.business.alarm.IQuestInteractor;
import com.dmelnyk.alarmquest.business.alarm.model.QuestionData;

import java.util.ArrayList;

import timber.log.Timber;

/**
 * Created by d264 on 6/7/17.
 */

public class AlarmQuestPresenter implements Contract.IAlarmQuestPresenter {

    private final IQuestInteractor questInteractor;
    private Contract.IAlarmQuestView view;
    private final int questionsCount = 20; // default question's count
    private int questionsToSolveCounters = 1;
    private QuestionData[] quesions;

    // Database of questions already answered
    private ArrayList<String> answeredQuestions = new ArrayList<>();
    private String askedQuestionTitle = "";

    public AlarmQuestPresenter(IQuestInteractor questInteractor) {
        this.questInteractor = questInteractor;
    }

    @Override
    public void bindView(Contract.IAlarmQuestView view) {
        this.view = view;
        if (view != null) {
            quesions = questInteractor.getQuestions(questionsCount);
            String[] titles = getTitles(quesions);
            askedQuestionTitle = quesions[0].getQuestion();
            view.setBubbleTitles(titles);
            view.setQuestion(quesions[0]);
        }
    }

    private String[] getTitles(QuestionData[] questions) {
        String[] titles = new String[questions.length];
        for (int i = 0; i < questions.length; i++) {
            titles[i] = questions[i].getQuestion();
        }

        return titles;
    }

    @Override
    public void unbindView() {
        view = null;
    }

    @Override
    public void pickQuestion(String questionTitle) {
        if (!answeredQuestions.contains(questionTitle)) {
            QuestionData newQuestion = getQuestionWithTitle(questionTitle);
            askedQuestionTitle = newQuestion.getQuestion();
            view.setQuestion(newQuestion);
        } else {
            view.showHasAnsweredMessage();
        }
    }

    private QuestionData getQuestionWithTitle(String questionTitle) {
        for (int i = 0; i < questionsCount; i++) {
            if (questionTitle.equals(quesions[i].getQuestion())) {
                return quesions[i];
            }
        }
        return null;
    }

    @Override
    public void isAnswerCorrect(boolean isCorrect) {
        // add question to database of answered questions
        answeredQuestions.add(askedQuestionTitle);
        Timber.d(answeredQuestions.toString());
        if (isCorrect) {
            questionsToSolveCounters--;
            view.updateLeftToAnswerQuestionsCounter("" + questionsToSolveCounters);
        }

        if (questionsToSolveCounters == 0) {
            view.success();
        }

    }
}
