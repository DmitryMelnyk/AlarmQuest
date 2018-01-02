package com.dmelnyk.alarmquest.business.alarm;

import com.dmelnyk.alarmquest.business.alarm.model.QuestionData;
import com.dmelnyk.alarmquest.ui.questfragment.repository.QuestionRepository;
import com.dmelnyk.alarmquest.ui.questfragment.repository.QuestionBlock;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * Created by dmitry on 23.05.17.
 */
public class QuestInteractorImpl implements QuestInteractor {

    private final QuestionRepository questionRepositrory;

    private int correctAnswer;
    private QuestionBlock questionBlock;

    @Inject
    public QuestInteractorImpl(QuestionRepository repository) {
        questionRepositrory = repository;
    }

    protected QuestionData convertToQuestionData(QuestionBlock questionBlock) {

        Timber.d("question = " + questionBlock);
        return new QuestionData().Builder()
                .setQuestion(questionBlock.getQuestion())
                .addAnswer1(questionBlock.getAnswer0())
                .addAnswer2(questionBlock.getAnswer1())
                .addAnswer3(questionBlock.getAnswer2())
                .addAnswer4(questionBlock.getAnswer3())
                .addCorrectAnswer(questionBlock.getCorrectAnswer())
                .addLevel(questionBlock.getLevel())
                .build();
    }

    @Override
    public QuestionData[] getQuestions(int count) {
        QuestionData[] questions = new QuestionData[count];
        QuestionBlock[] rawQuestions = questionRepositrory.getQuestions(count);
        for (int i = 0; i < count; i++) {
            questions[i] = convertToQuestionData(rawQuestions[i]);
        }

        return questions;
    }
}
