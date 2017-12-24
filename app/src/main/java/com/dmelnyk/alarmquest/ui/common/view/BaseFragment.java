package com.dmelnyk.alarmquest.ui.common.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.dmelnyk.alarmquest.ui.main.core.BaseDialogCallback;
import com.dmelnyk.alarmquest.ui.main.core.BaseDialogFragment;

import javax.inject.Inject;
import javax.inject.Named;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.AndroidSupportInjection;
import dagger.android.support.HasSupportFragmentInjector;

/**
 * Created by d264 on 12/23/17.
 */

public abstract class BaseFragment extends Fragment
        implements HasSupportFragmentInjector {

    @Inject
    protected Context activityContext;

    @Inject
    @Named(BaseFragmentModule.CHILD_FRAGMENT_MANAGER)
    protected FragmentManager childFragmentManager;

    // todo ?
    @Inject
    DispatchingAndroidInjector<Fragment> childFragmentInjector;

    String displayedDialogTag;
    public static final String EXT_DIALOG = "EXT_DIALOG";

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(EXT_DIALOG, displayedDialogTag);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        // if needed bind ButterKnife.bind(this, getView());
        if (savedInstanceState != null) {
            displayedDialogTag = savedInstanceState.getString(EXT_DIALOG);
        }
    }

    protected void setDialogCallback(BaseDialogCallback callback) {
        if (displayedDialogTag != null) {
            BaseDialogFragment fragment = (BaseDialogFragment)
                    childFragmentManager.findFragmentByTag(displayedDialogTag);
            if (fragment != null) {
                fragment.setCallback(callback);
            }
        }
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return childFragmentInjector;
    }

    protected final void addChildFragment(@IdRes int containerViewId, Fragment fragment) {
        childFragmentManager.beginTransaction()
                .add(containerViewId, fragment)
                .commit();
    }

    protected final void showDialog(DialogFragment dialog, String tag) {
        displayedDialogTag = tag;
        dialog.show(childFragmentManager, tag);
    }
}
