package com.dmelnyk.alarmquest.business.alarm;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.dmelnyk.alarmquest.business.alarm.model.QuestionData;
import com.dmelnyk.alarmquest.data.DataUtil;
import com.dmelnyk.alarmquest.data.question.IQuestionRepository;
import com.dmelnyk.alarmquest.data.question.QuestionRepository;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by d264 on 6/7/17.
 */

@RunWith(AndroidJUnit4.class)
public class QuestInteractorTest {

    IQuestInteractor interactor;

    @Before
    public void setUp() {
        Context context = InstrumentationRegistry.getContext();
        IQuestionRepository repository = new QuestionRepository(new DataUtil(context));
        interactor = new QuestInteractor(repository);
    }

    @Test
    public void getQuestions() {
        int count = 10;
        QuestionData[] questions = interactor.getQuestions(count);
        Assert.assertEquals(questions.length,  count);
    }
}