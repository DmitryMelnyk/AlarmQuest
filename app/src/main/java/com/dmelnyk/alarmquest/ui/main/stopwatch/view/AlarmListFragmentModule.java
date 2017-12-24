package com.dmelnyk.alarmquest.ui.main.stopwatch.view;

import android.support.v4.app.Fragment;

import com.dmelnyk.alarmquest.inject.PerFragment;
import com.dmelnyk.alarmquest.ui.common.view.BaseFragmentModule;
import com.dmelnyk.alarmquest.ui.main.stopwatch.alarmlistrepo.AlarmListRepository;
import com.dmelnyk.alarmquest.ui.main.stopwatch.alarmlistrepo.AlarmListRepositoryImpl;
import com.dmelnyk.alarmquest.ui.main.stopwatch.presenter.AlarmListPresenterModule;

import javax.inject.Named;

import dagger.Binds;
import dagger.Module;

/**
 * Created by d264 on 12/23/17.
 */

@Module(includes = {
        BaseFragmentModule.class,
        AlarmListPresenterModule.class
})
public abstract class AlarmListFragmentModule {

    @Binds
    @Named(BaseFragmentModule.FRAGMENT)
    @PerFragment
    abstract Fragment fragment(AlarmListFragment alarmListFragment);

    @Binds
    @PerFragment
    abstract AlarmListView alarmListView(AlarmListFragment alarmListFragment);

}
