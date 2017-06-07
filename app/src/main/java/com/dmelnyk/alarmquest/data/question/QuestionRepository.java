package com.dmelnyk.alarmquest.data.question;

import com.dmelnyk.alarmquest.data.DataUtil;

/**
 * Created by dmitry on 23.05.17.
 */
public class QuestionRepository implements IQuestionRepository {

    private DataUtil util;
    public QuestionRepository(DataUtil util) {
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
