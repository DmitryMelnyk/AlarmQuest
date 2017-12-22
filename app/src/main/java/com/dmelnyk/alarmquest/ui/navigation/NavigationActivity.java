package com.dmelnyk.alarmquest.ui.navigation;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.dmelnyk.alarmquest.R;
import com.dmelnyk.alarmquest.application.AlarmQuestApplication;
import com.dmelnyk.alarmquest.ui.navigation.Contract.INavigationPresenter;
import com.dmelnyk.alarmquest.ui.navigation.di.NavigationModule;
import com.dmelnyk.alarmquest.ui.navigation.fragments.alarmclock.AlarmFragment;
import com.dmelnyk.alarmquest.ui.navigation.menu.DrawerAdapter;
import com.dmelnyk.alarmquest.ui.navigation.menu.DrawerItem;
import com.dmelnyk.alarmquest.ui.navigation.menu.SimpleItem;
import com.dmelnyk.alarmquest.ui.navigation.menu.SpaceItem;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;

import java.util.Arrays;

import javax.inject.Inject;

import butterknife.BindView;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class NavigationActivity extends AppCompatActivity implements
        DrawerAdapter.OnItemSelectedListener, AlarmFragment.OnAlarmSetListener, Contract.INavigationView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.container)
    FrameLayout container;

    private TextView nearestAlarm;

    @Inject
    INavigationPresenter presenter;
    @Inject
    NavUtil navUtil;

    private static final int NAV_ALARM_POSITION = 0;
    private static final int NAV_INFO_POSITION = 1;
    private static final int NAV_ABOUT_POSITION = 2;
    private static final int NAV_SETTINGS_POSITION = 3;
    private static final int NAV_EXIT_POSITION = 5;

    private String[] screenTitles;
    private Drawable[] screenIcons;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        // initialize dagger2
        AlarmQuestApplication.get(this).getAppComponent()
                .add(new NavigationModule()).inject(this);

        new SlidingRootNavBuilder(this)
//                .withToolbarMenuToggle(toolbar)
                .withMenuOpened(true)
                .withSavedState(savedInstanceState)
                .withMenuLayout(R.layout.menu_drawer)
                .inject();

        nearestAlarm = (TextView) findViewById(R.id.menu_nearest_alarm);

        screenTitles = navUtil.getNavTitles();
        screenIcons = navUtil.getNavIcons();

        DrawerAdapter adapter = new DrawerAdapter(Arrays.asList(
                createItemFor(NAV_ALARM_POSITION).setChecked(true),
                createItemFor(NAV_INFO_POSITION),
                createItemFor(NAV_ABOUT_POSITION),
                createItemFor(NAV_SETTINGS_POSITION),
                new SpaceItem(48),
                createItemFor(NAV_EXIT_POSITION)));

        adapter.setListener(this);

        RecyclerView list = (RecyclerView) findViewById(R.id.list);
        list.setNestedScrollingEnabled(false);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter);

        adapter.setSelected(NAV_ALARM_POSITION);

        presenter.bindView(this);
    }

    @Override
    public void onItemSelected(int position) {
        AlarmFragment fragment = null;

        switch (position) {
            case NAV_EXIT_POSITION:
                finish();
                break;
            case NAV_ALARM_POSITION:
                fragment = new AlarmFragment();
                break;
            case NAV_INFO_POSITION:
                // TODO
                break;
            case NAV_ABOUT_POSITION:
                // TODO
                break;
            case NAV_SETTINGS_POSITION:
                // TODO
                break;
        }
        // TODO: remove string below after implementing TODOs above
        fragment = new AlarmFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }

    private DrawerItem createItemFor(int position) {
        return new SimpleItem(screenIcons[position], screenTitles[position])
                .withIconTint(color(R.color.gray))
                .withTextTint(color(R.color.textColorPrimary))
                .withSelectedIconTint(color(R.color.textSelected))
                .withSelectedTextTint(color(R.color.textSelected));
    }

    @ColorInt
    private int color(@ColorRes int res) {
        return ContextCompat.getColor(this, res);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onAlarmSelected(String time) {
        nearestAlarm.setText(time);
//        presenter.alarmEnabled();
    }

    @Override
    public void restoreAlarmTime(String time) {
        nearestAlarm.setText(time);
    }

    @Override
    protected void onDestroy() {
        presenter.unbindView();
        super.onDestroy();
    }
}
