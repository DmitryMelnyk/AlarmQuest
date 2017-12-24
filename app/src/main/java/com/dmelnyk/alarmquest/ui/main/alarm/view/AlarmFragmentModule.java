package com.dmelnyk.alarmquest.ui.main.alarm.view;

import android.support.v4.app.Fragment;

import com.dmelnyk.alarmquest.inject.PerFragment;
import com.dmelnyk.alarmquest.ui.common.view.BaseFragmentModule;
import com.dmelnyk.alarmquest.ui.main.alarm.presenter.AlarmPresenterModule;

import javax.inject.Named;

import dagger.Binds;
import dagger.Module;

/**
 * Created by d264 on 12/23/17.
 */

@Module(includes = {
        BaseFragmentModule.class,
        AlarmPresenterModule.class
})
public abstract class AlarmFragmentModule {

    @Binds
    @Named(BaseFragmentModule.FRAGMENT)
    @PerFragment
    abstract Fragment fragment(AlarmFragment alarmFragment);

    @Binds
    @PerFragment
    abstract AlarmView alarmView(AlarmFragment alarmFragment);
}
