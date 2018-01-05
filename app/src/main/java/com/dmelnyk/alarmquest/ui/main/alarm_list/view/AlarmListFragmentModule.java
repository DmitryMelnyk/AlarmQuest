package com.dmelnyk.alarmquest.ui.main.alarm_list.view;

import android.support.v4.app.Fragment;

import com.dmelnyk.alarmquest.inject.PerFragment;
import com.dmelnyk.alarmquest.ui.common.view.BaseFragmentModule;
import javax.inject.Named;

import dagger.Binds;
import dagger.Module;

/**
 * Created by d264 on 12/23/17.
 */

@Module(includes = BaseFragmentModule.class)
public abstract class AlarmListFragmentModule {

    @Binds
    @Named(BaseFragmentModule.FRAGMENT)
    @PerFragment
    abstract Fragment fragment(AlarmListFragment alarmListFragment);
}
