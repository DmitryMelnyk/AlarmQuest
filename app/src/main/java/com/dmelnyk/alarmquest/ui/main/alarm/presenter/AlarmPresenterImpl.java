package com.dmelnyk.alarmquest.ui.main.alarm.presenter;

import com.dmelnyk.alarmquest.inject.PerFragment;
import com.dmelnyk.alarmquest.ui.common.presenter.BasePresenter;
import com.dmelnyk.alarmquest.ui.main.alarm.view.AlarmView;

import javax.inject.Inject;

/**
 * Created by d264 on 12/23/17.
 */

@PerFragment
public class AlarmPresenterImpl extends BasePresenter<AlarmView> implements AlarmPresenter {

    @Inject
    protected AlarmPresenterImpl(AlarmView view) {
        super(view);
    }

    @Override
    public void onDoSomething() {
        view.showSomething(getClass().getCanonicalName());
    }
}
