package com.dmelnyk.alarmquest.ui.alarm;

import com.dmelnyk.alarmquest.ui.alarm.business.QuestInteractor;
import com.dmelnyk.alarmquest.model.QuestionData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by d264 on 6/7/17.
 */

public class AlarmQuestPresenter implements Contract.IAlarmQuestPresenter {

    private final QuestInteractor questInteractor;
    private Contract.IAlarmQuestView view;
    private final int questionsCount = 100; // default question's count
    private int questionsToSolveCounters = 1;
    private QuestionData[] mQuestions;

    private String[] titles;
    private int mQuestionNumber;

    @Inject
    public AlarmQuestPresenter(QuestInteractor questInteractor) {
        this.questInteractor = questInteractor;
    }

    @Override
    public void bindView(Contract.IAlarmQuestView view, int questionToSolveCount) {
        this.view = view;
        if (view != null) {
            loadQuestions(view);
            questionsToSolveCounters = questionToSolveCount;
        }
    }

    private void loadQuestions(Contract.IAlarmQuestView view) {
        // todo
        mQuestions = questInteractor.getQuestions(questionsCount);
        titles = getTitles(mQuestions);
        List<String> list = new ArrayList(Arrays.asList(titles));

        view.setQuestions(list, mQuestions);
        // set first question
        mQuestionNumber = 0;
        view.setQuestion(mQuestions[0]);
    }

    private String[] getTitles(QuestionData[] questions) {
        String[] titles = new String[questions.length];
        for (int i = 0; i < questions.length; i++) {
            titles[i] = questions[i].getQuestion();
        }

        return titles;
    }

    @Override
    public void questionSwiped() {
        view.setQuestion(mQuestions[++mQuestionNumber]);
    }

    @Override
    public void unbindView() {
        view = null;
    }

    @Override
    public void isAnswerCorrect(boolean isCorrect) {
        // add question to database of answered mQuestions
        if (isCorrect) {
            view.updateLeftToAnswerQuestionsCounter("" + --questionsToSolveCounters);
        }

        if (questionsToSolveCounters == 0) {
            view.success();
        }

        // After answering to all mQuestions - stop alarm
        if (mQuestionNumber == questionsCount-1) {
            view.success();
        }
    }
}
