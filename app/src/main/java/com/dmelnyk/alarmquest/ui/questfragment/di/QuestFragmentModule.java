package com.dmelnyk.alarmquest.ui.questfragment.di;

import android.support.v4.app.Fragment;

import com.dmelnyk.alarmquest.inject.PerFragment;
import com.dmelnyk.alarmquest.ui.questfragment.Contract;
import com.dmelnyk.alarmquest.ui.questfragment.QuestFragment;
import com.dmelnyk.alarmquest.ui.questfragment.QuestPresenter;
import com.dmelnyk.alarmquest.ui.common.view.BaseChildFragmentModule;

import javax.inject.Named;

import dagger.Binds;
import dagger.Module;

/**
 * Created by d264 on 6/7/17.
 */

@Module(includes = {
        BaseChildFragmentModule.class}
)
public abstract class QuestFragmentModule {

    @Binds
    @Named(BaseChildFragmentModule.CHILD_FRAGMENT)
    @PerFragment
    abstract Fragment fragment(QuestFragment fragment);

    @Binds
    @PerFragment
    abstract Contract.IQuestPresenter questPresenter(QuestPresenter presenter);
}
