package com.dmelnyk.alarmquest.ui.main.core;

import android.support.v4.app.DialogFragment;

/**
 * Created by d264 on 12/23/17.
 */

public abstract class BaseDialogFragment extends DialogFragment {
    public abstract void setCallback(BaseDialogCallback callback);
}
