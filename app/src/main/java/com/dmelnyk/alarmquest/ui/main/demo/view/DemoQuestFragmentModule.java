package com.dmelnyk.alarmquest.ui.main.demo.view;

import android.support.v4.app.Fragment;

import com.dmelnyk.alarmquest.inject.PerChildFragment;
import com.dmelnyk.alarmquest.inject.PerFragment;
import com.dmelnyk.alarmquest.ui.questfragment.QuestFragment;
import com.dmelnyk.alarmquest.ui.questfragment.di.QuestFragmentModule;
import com.dmelnyk.alarmquest.ui.common.view.BaseFragmentModule;

import javax.inject.Named;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by d264 on 1/2/18.
 */

@Module(includes = BaseFragmentModule.class)
public abstract class DemoQuestFragmentModule {

    @PerChildFragment
    @ContributesAndroidInjector(modules = QuestFragmentModule.class)
    abstract QuestFragment questFragmentModuleInjectorFactory();

    @Binds
    @Named(BaseFragmentModule.FRAGMENT)
    @PerFragment
    abstract Fragment fragment(DemoQuestFragment fragment);
}
