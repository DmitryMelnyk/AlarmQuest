package com.dmelnyk.alarmquest.ui.questfragment.repository;

import com.dmelnyk.alarmquest.data.DataUtil;

import javax.inject.Inject;

/**
 * Created by dmitry on 23.05.17.
 */
public class QuestionRepositoryImpl implements QuestionRepository {

    private final DataUtil util;

    @Inject
    public QuestionRepositoryImpl(DataUtil util) {
        this.util = util;
    }

    @Override
    public QuestionBlock getQuestion() {
        return util.getRandomQuestion();
    }

    @Override
    public QuestionBlock[] getQuestions(int count) {
        return util.getQuestions(count);
    }
}
