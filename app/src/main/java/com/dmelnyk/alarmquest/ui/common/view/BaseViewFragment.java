package com.dmelnyk.alarmquest.ui.common.view;

import android.os.Bundle;

import com.dmelnyk.alarmquest.ui.common.presenter.Presenter;

import javax.inject.Inject;

/**
 * Created by d264 on 12/23/17.
 */

public abstract class BaseViewFragment/*<T extends Presenter>*/ extends BaseFragment
        implements MVPView{

//    @Inject
//    protected T presenter;

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        // Only start the presenter when the views have been bound.
        // See BaseFragment.onViewStateRestored
//        presenter.start(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
//        presenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
//        presenter.pause();
    }

    @Override
    public void onDestroyView() {
//        presenter.end();
        super.onDestroyView();
    }
}
