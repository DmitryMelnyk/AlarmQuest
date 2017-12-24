package com.dmelnyk.alarmquest.ui.common.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.dmelnyk.alarmquest.inject.PerFragment;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Created by d264 on 12/23/17.
 */

@Module
public abstract class BaseFragmentModule {

    public static final String FRAGMENT = "BaseFragmentModule.Fragment";

    static final String CHILD_FRAGMENT_MANAGER = "BaseFragmentModule.childFragmentManager";

    @Provides
    @Named(CHILD_FRAGMENT_MANAGER)
    @PerFragment
    static FragmentManager childFragmentManager(@Named(FRAGMENT) Fragment fragment) {
        return fragment.getChildFragmentManager();
    }
}
