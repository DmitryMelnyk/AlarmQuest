package com.dmelnyk.alarmquest;

import android.test.AndroidTestCase;

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
import org.mockito.junit.MockitoJUnitRunner;

import javax.inject.Inject;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

@RunWith(MockitoJUnitRunner.class)
public class ExampleUnitTest extends AndroidTestCase {
    QuestInteractor interactor;

    @Inject
    DataUtil dataUtil;

    @Before
    public void setUp() {
        QuestionRepository repository = new QuestionRepositoryImpl(dataUtil);
        interactor = new QuestInteractorImpl(repository);
    }

    @Test
    public void getQuestions() {
        int count = 10;
        QuestionData[] questions = interactor.getQuestions(count);
        Assert.assertEquals(questions.length,  count);
    }
}