package com.dmelnyk.alarmquest.application;

import com.dmelnyk.alarmquest.ui.main.demo.viewmodel.DemoQuestViewModelModule;
import com.dmelnyk.alarmquest.ui.main.alarm_list.viewmodel.AlarmListViewModelModule;
import com.dmelnyk.alarmquest.ui.main.settings.viewmodel.SettingsViewModelModule;

import dagger.Module;

/**
 * Created by d264 on 12/24/17.
 */

@Module(includes = {
        AlarmListViewModelModule.class,
        DemoQuestViewModelModule.class,
        SettingsViewModelModule.class
})
public class ViewModelModule {
}
