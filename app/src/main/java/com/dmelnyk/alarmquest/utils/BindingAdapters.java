package com.dmelnyk.alarmquest.utils;

import android.databinding.BindingAdapter;
import android.view.View;

/**
 * Created by d264 on 12/21/17.
 */

public final class BindingAdapters {
    private BindingAdapters() {/* NOP */}

    @BindingAdapter("visibleGone")
    public static void showHide(View view, boolean show) {
        view.setVisibility(show ? View.VISIBLE : View.GONE);
    }
}
