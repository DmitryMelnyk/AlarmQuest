package com.dmelnyk.alarmquest.ui;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.dmelnyk.alarmquest.R;

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
        mBottmoNavigator.setSelectedItemId(R.id.action_alarm);
        mBottmoNavigator.setOnClickListener(view -> {
            switch (view.getId()) {
                case R.id.action_stop_watch:
                    Toast.makeText(this, "StopWatch", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.action_alarm:
                    Toast.makeText(this, "Alarm", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.action_settings:
                    Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
