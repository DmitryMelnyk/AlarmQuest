package com.dmelnyk.alarmquest.ui.common.presenter;

import android.os.Bundle;

import org.jetbrains.annotations.Nullable;

/**
 * Created by d264 on 12/23/17.
 */

public interface Presenter {


    /**
     * Starts the presentation. This should be called in the view's (Activity or Fragment)
     * onCreate() or onViewStatedRestored() method respectively.
     * @param savedInstanceState the saved instance state that contains state saved in
     *                           {@link #saveInstanceState(Bundle)}
     */
    void start(@Nullable Bundle savedInstanceState);

    /**
     * Resumes the presentation. This should be called in the view's (Activity or Fragment)
     * resume() method.
     */
    void resume();

    /**
     * Pauses the presentation. This should be called in the view's Activity or Fragment)
     * pause() method.
     */
    void pause();

    /**
     * Save the state of the presentation (if any). This should be called in the view's
     * (Activity or Fragment) saveInstanceState().
     *
     * @param outState the out state to save instance state
     */
    void saveInstanceState(Bundle outState);

    /**
     * Ends the presentation. This should be called in the view's (Activity or Fragment)
     * onDestroy() or onDestroyView() method respectively.
     */
    void end();
}
