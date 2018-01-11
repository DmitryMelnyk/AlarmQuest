package com.dmelnyk.alarmquest.ui.main.settings.view;

import android.support.v4.app.Fragment;

import com.dmelnyk.alarmquest.inject.PerFragment;
import com.dmelnyk.alarmquest.ui.common.view.BaseFragmentModule;

import javax.inject.Named;

import dagger.Binds;
import dagger.Module;

/**
 * Created by d264 on 1/9/18.
 */

@Module(includes = BaseFragmentModule.class)
public abstract class SettingsFragmentModule {

    @Binds
    @Named(BaseFragmentModule.FRAGMENT)
    @PerFragment
    abstract Fragment fragment(SettingsFragment fragment);
}
