package com.dmelnyk.alarmquest.ui.alarm.business.alarm;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.dmelnyk.alarmquest.ui.alarm.business.QuestInteractor;
import com.dmelnyk.alarmquest.ui.alarm.business.QuestInteractorImpl;
import com.dmelnyk.alarmquest.model.QuestionData;
import com.dmelnyk.alarmquest.data.DataUtil;
import com.dmelnyk.alarmquest.ui.questfragment.repository.QuestionRepository;
import com.dmelnyk.alarmquest.ui.questfragment.repository.QuestionRepositoryImpl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by d264 on 6/7/17.
 */

@RunWith(AndroidJUnit4.class)
public class QuestInteractorTest {

    QuestInteractor interactor;

    @Before
    public void setUp() {
        Context context = InstrumentationRegistry.getContext();
        QuestionRepository repository = new QuestionRepositoryImpl(new DataUtil(context));
        interactor = new QuestInteractorImpl(repository);
    }

    @Test
    public void getQuestions() {
        int count = 10;
        QuestionData[] questions = interactor.getQuestions(count);
        Assert.assertEquals(questions.length,  count);
    }
}