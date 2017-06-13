package com.dmelnyk.alarmquest.ui.navigation.di;

import android.content.Context;

import com.dmelnyk.alarmquest.data.navigation.fragments.alarmclock.ITimeRepository;
import com.dmelnyk.alarmquest.data.navigation.fragments.alarmclock.TimeRepository;
import com.dmelnyk.alarmquest.ui.navigation.Contract;
import com.dmelnyk.alarmquest.ui.navigation.NavUtil;
import com.dmelnyk.alarmquest.ui.navigation.NavigationPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by d264 on 6/11/17.
 */

@Module
public class NavigationModule {

    @Provides
    @NavigationScope
    ITimeRepository providesITimeRepository(Context context) {
        return new TimeRepository(context);
    }

    @Provides
    @NavigationScope
    Contract.INavigationPresenter providesINavigationPresenter(ITimeRepository repository) {
        return new NavigationPresenter(repository);
    }

    @Provides
    @NavigationScope
    NavUtil providesNavUtils(Context context) {
        return new NavUtil(context);
    }

}
