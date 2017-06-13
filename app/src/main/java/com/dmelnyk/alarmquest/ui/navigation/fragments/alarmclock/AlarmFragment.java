package com.dmelnyk.alarmquest.ui.navigation.fragments.alarmclock;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.android.datetimepicker.time.RadialPickerLayout;
import com.android.datetimepicker.time.TimePickerDialog;
import com.dmelnyk.alarmquest.R;
import com.dmelnyk.alarmquest.application.AlarmQuestApplication;
import com.dmelnyk.alarmquest.ui.navigation.fragments.alarmclock.di.AlarmFragmentModule;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import timber.log.Timber;

/**
 * Created by d264 on 6/11/17.
 */

public class AlarmFragment extends Fragment implements Contract.IAlarmFragmentView, TimePickerDialog.OnTimeSetListener, CompoundButton.OnCheckedChangeListener {

    private static final String TIME_PATTERN = "HH:mm";

    private Calendar calendar;
    private SimpleDateFormat timeFormat;

    public OnAlarmSetListener callbackListener;
    private String alarm; // saved alarm time

    public interface OnAlarmSetListener {
        void onAlarmSelected(String time);
    }

    @Inject
    Contract.IAlarmFragmentPresenter presenter;

    @BindView(R.id.nav_f_a_current_time)
    TextView currentTime;
    @BindView(R.id.nav_f_a_alarm_time)
    TextView alarmTime;
    @BindView(R.id.nav_f_a_date)
    TextView currentDate;
    @BindView(R.id.nav_f_a_switcher)
    Switch alarmSwitcher;
    Unbinder unbinder;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the host activity has implemented the callback interface
        // If not, it throws an exception
        try {
            callbackListener = (OnAlarmSetListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    " must implement OnAlarmSetListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AlarmQuestApplication.get(getContext()).getAppComponent()
                .add(new AlarmFragmentModule()).inject(this);

        calendar = Calendar.getInstance();
        timeFormat = new SimpleDateFormat(TIME_PATTERN, Locale.getDefault());
    }

    @Override
    public void setTimeAndDate(String time, String date) {
        currentTime.setText(time);
        currentDate.setText(date);
    }

    @Override
    public void restoreAlarmTime(String time) {
        alarmTime.setText(time);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.navigation_fragment_alarm, container, false);

        unbinder = ButterKnife.bind(this, view);
        alarmSwitcher.setOnCheckedChangeListener(this);
        presenter.bindView(this);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({ R.id.nav_f_a_alarm_time})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.nav_f_a_alarm_time:
                TimePickerDialog.newInstance(this, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true)
                        .show(getActivity().getFragmentManager(), "timePicker");
                break;

        }
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);

        // selected alarm time:
        alarm = timeFormat.format(calendar.getTime());

        alarmTime.setText(alarm);
        presenter.setAlarm(alarm);
        callbackListener.onAlarmSelected(alarm);
    }

    @Override
    public void enableAlarmTimer(boolean enable, String time) {
        alarmTime.setEnabled(enable);
        alarmSwitcher.setChecked(enable);
        callbackListener.onAlarmSelected(time);
        if (enable) {
            alarmTime.setTextColor(ContextCompat.getColor(getContext(), R.color.textSelected));
        } else {
            alarmTime.setTextColor(ContextCompat.getColor(getContext(), R.color.text_color));
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        Timber.d(String.valueOf(isChecked));
        presenter.changeSwitcherState(isChecked);
    }
}
