package com.dmelnyk.alarmquest.ui.main;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;

import com.dmelnyk.alarmquest.R;
import com.dmelnyk.alarmquest.ui.main.fragments.alarm.AlarmFragment;
import com.dmelnyk.alarmquest.ui.main.fragments.stopwatch.AlarmListFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.bn_navigation)
    BottomNavigationView mBottmoNavigator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setupViews();
    }

    private void setupViews() {
        mBottmoNavigator.setOnNavigationItemSelectedListener(menu -> {
            menu.setEnabled(true);

            switch (menu.getItemId()) {
                case R.id.action_stop_watch:
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.main_fragment_container, new AlarmListFragment())
                            .commit();
                    break;
                case R.id.action_alarm:
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.main_fragment_container, new AlarmFragment())
                            .commit();
                    break;
                case R.id.action_settings:
                    break;
            }
            return true;
        });

        mBottmoNavigator.setSelectedItemId(R.id.action_alarm);
    }
}
