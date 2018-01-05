package com.dmelnyk.alarmquest.ui.main.demo.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.dmelnyk.alarmquest.ui.alarm.business.QuestInteractor;
import com.dmelnyk.alarmquest.model.QuestionData;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * Created by d264 on 1/2/18.
 */

public class DemoQuestViewModel extends ViewModel {

    private final int questionsCount = 100; // default question's count
    public static int questionsToSolveCounters = 3; // todo: get from settings
    private QuestionData[] mQuestionsData;

    // Database of mQuestions already answered
    private int mQuestionNumber = 0;

    private final QuestInteractor interactor;

    private final MediatorLiveData<List<String>> mQuestions = new MediatorLiveData<>();
    private final MediatorLiveData<QuestionData> mAnswers = new MediatorLiveData<>();
    private final MediatorLiveData<Integer> mStillQuestions = new MediatorLiveData<>();
    private final MediatorLiveData<Boolean> mIsQuestSolved = new MediatorLiveData<>();

    @Inject
    public DemoQuestViewModel(@NonNull QuestInteractor questInteractor) {
        this.interactor = questInteractor;

        loadQuestions();
    }

    private void loadQuestions() {
        mQuestionsData = interactor.getQuestions(questionsCount);
        List<String> questions = getTitles(mQuestionsData);
        mQuestions.setValue(questions);
        mAnswers.setValue(mQuestionsData[mQuestionNumber]); // setting first question in fragment
        mStillQuestions.setValue(questionsToSolveCounters);
    }

    private List<String> getTitles(QuestionData[] questions) {
        List<String> titles = new ArrayList<>();
        for (int i = 0; i < questions.length; i++) {
            titles.add(questions[i].getQuestion());
        }

        return titles;
    }

    public LiveData<List<String>> getQuestions() {
        return mQuestions;
    }

    public LiveData<QuestionData> getAnswers() {
        return mAnswers;
    }

    public LiveData<Integer> getStillQuestionsToSlove() {
        return mStillQuestions;
    }

    public LiveData<Boolean> isSuccess() {
        return mIsQuestSolved;
    }

    public void isAnswerCorrect(boolean isCorrect) {
        Timber.d("Answer is correct=" + isCorrect);
        if (isCorrect) mStillQuestions.setValue(--questionsToSolveCounters);
        if (questionsToSolveCounters == 0) mIsQuestSolved.setValue(true); // show success dialog
        if (mQuestionNumber == questionsCount-1) mIsQuestSolved.setValue(false); // show lost dialog
    }

    public void refreshQuestions() {

    }

    public void onQuestionSwiped() {
        mAnswers.setValue(mQuestionsData[++mQuestionNumber]);
    }
}
