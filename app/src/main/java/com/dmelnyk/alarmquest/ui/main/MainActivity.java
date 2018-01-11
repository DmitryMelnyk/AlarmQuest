package com.dmelnyk.alarmquest.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;

import com.dmelnyk.alarmquest.R;
import com.dmelnyk.alarmquest.ui.common.BaseActivity;
import com.dmelnyk.alarmquest.ui.main.demo.view.DemoQuestFragment;
import com.dmelnyk.alarmquest.ui.main.alarm_list.view.AlarmListFragment;
import com.dmelnyk.alarmquest.ui.main.settings.view.SettingsFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends BaseActivity {

    @BindView(R.id.bn_navigation)
    BottomNavigationView mBottomNavigator;
    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);

        setupViews();

        if (savedInstanceState == null) {
            // set central fragment
            addFragment(R.id.main_fragment_container, new SettingsFragment());
//            mBottomNavigator.setSelectedItemId(R.id.action_alarm);
        }
    }

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }

    private void setupViews() {
        mBottomNavigator.setOnNavigationItemSelectedListener(menu -> {
            menu.setEnabled(true);

            switch (menu.getItemId()) {
                case R.id.action_stop_watch:
                    addFragment(R.id.main_fragment_container, new DemoQuestFragment());
                    break;
                case R.id.action_alarm:
                    addFragment(R.id.main_fragment_container, new AlarmListFragment());
                    break;
                case R.id.action_settings:
                    addFragment(R.id.main_fragment_container, new SettingsFragment());
                    break;
            }
            return true;
        });
    }
}
