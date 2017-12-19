package com.dmelnyk.alarmquest.ui.main;

import android.os.Bundle;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.dmelnyk.alarmquest.R;
import com.dmelnyk.alarmquest.ui.main.fragments.alarm.AlarmFragment;

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
                    Toast.makeText(this, "StopWatch", Toast.LENGTH_SHORT).show();
//                    ((MenuItem) findViewById(R.id.action_alarm)).setChecked(false);
                    break;
                case R.id.action_alarm:
                    Toast.makeText(this, "Alarm", Toast.LENGTH_SHORT).show();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.main_fragment_container, new AlarmFragment())
                            .commit();
                    break;
                case R.id.action_settings:
                    Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
                    break;
            }
            return true;
        });

        mBottmoNavigator.setSelectedItemId(R.id.action_alarm);
    }
}
