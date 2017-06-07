package com.dmelnyk.alarmquest.ui.alarm.di;

import android.content.Context;

import com.dmelnyk.alarmquest.business.alarm.IQuestInteractor;
import com.dmelnyk.alarmquest.business.alarm.QuestInteractor;
import com.dmelnyk.alarmquest.data.DataUtil;
import com.dmelnyk.alarmquest.data.question.IQuestionRepository;
import com.dmelnyk.alarmquest.data.question.QuestionRepository;
import com.dmelnyk.alarmquest.ui.alarm.AlarmQuestPresenter;
import com.dmelnyk.alarmquest.ui.alarm.Contract;
import com.dmelnyk.alarmquest.ui.alarm.Contract.IAlarmQuestPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by d264 on 6/7/17.
 */

@Module
public class AlarmQuestModule {

    @Provides
    @AlarmQuestScope
    DataUtil providesDataUtil(Context context) {
        return new DataUtil(context);
    }

    @Provides
    @AlarmQuestScope
    IQuestionRepository providesIQuestionRepository(DataUtil dataUtil) {
        return new QuestionRepository(dataUtil);
    }

    @Provides
    @AlarmQuestScope
    IQuestInteractor providesIQuestInteractor(IQuestionRepository repositrory) {
        return new QuestInteractor(repositrory);
    }

    @Provides
    @AlarmQuestScope
    IAlarmQuestPresenter providesIAlarmQuestPresenter(IQuestInteractor questInteractor) {
        return new AlarmQuestPresenter(questInteractor);
    }
}
