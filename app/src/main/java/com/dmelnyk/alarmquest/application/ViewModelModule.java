package com.dmelnyk.alarmquest.application;

import com.dmelnyk.alarmquest.ui.main.demo.viewmodel.DemoQuestViewModelModule;
import com.dmelnyk.alarmquest.ui.main.alarm.viewmodel.AlarmListViewModelModule;

import dagger.Module;

/**
 * Created by d264 on 12/24/17.
 */

@Module(includes = {
        AlarmListViewModelModule.class,
        DemoQuestViewModelModule.class
})
public class ViewModelModule {
}
