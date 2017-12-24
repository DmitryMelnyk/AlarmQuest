package com.dmelnyk.alarmquest.ui.common.presenter;

import android.os.Bundle;

import com.dmelnyk.alarmquest.ui.common.view.MVPView;

import org.jetbrains.annotations.Nullable;

/**
 * Created by d264 on 12/23/17.
 */

public abstract class BasePresenter<T extends MVPView> implements Presenter {

    protected final T view;

    protected BasePresenter(T view) {
        this.view = view;
    }

    @Override
    public void start(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void saveInstanceState(Bundle outState) {

    }

    @Override
    public void end() {

    }
}
