package com.dmelnyk.alarmquest;

import android.content.Context;
import android.test.AndroidTestCase;

import com.dmelnyk.alarmquest.business.alarm.IQuestInteractor;
import com.dmelnyk.alarmquest.business.alarm.QuestInteractor;
import com.dmelnyk.alarmquest.business.alarm.model.QuestionData;
import com.dmelnyk.alarmquest.data.DataUtil;
import com.dmelnyk.alarmquest.data.question.IQuestionRepository;
import com.dmelnyk.alarmquest.data.question.QuestionRepository;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

@RunWith(MockitoJUnitRunner.class)
public class ExampleUnitTest extends AndroidTestCase {
    IQuestInteractor interactor;

    @Mock Context context;

    @Before
    public void setUp() {
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