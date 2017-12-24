package com.dmelnyk.alarmquest.ui.main.alarm.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.dmelnyk.alarmquest.R;
import com.dmelnyk.alarmquest.model.Alarm;
import com.dmelnyk.alarmquest.ui.common.view.BaseViewFragment;
import com.dmelnyk.alarmquest.ui.main.alarm.AlarmAdapter;
import com.dmelnyk.alarmquest.ui.main.alarm.presenter.AlarmPresenter;
import com.dmelnyk.alarmquest.ui.main.core.RecyclerViewModified;
import com.dmelnyk.alarmquest.ui.main.core.TimePickerFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.dmelnyk.alarmquest.ui.main.core.TimePickerFragment.ARG_ALARM_TIME;

/**
 * Created by d264 on 12/18/17.
 */

public class AlarmFragment extends BaseViewFragment<AlarmPresenter>
        implements AlarmView, TimePickerFragment.OnTimeSelectedListener {

    private static final String ALARM_PICKER_TAG = "ALARM_PICKER_TAG";
    @BindView(R.id.fab_add)
    FloatingActionButton mFabAdd;
    @BindView(R.id.rv_alarm_list)
    RecyclerViewModified mRecyclerViewAlarms;
    Unbinder unbinder;
    @BindView(R.id.ib_add)
    ImageView mImageViewAdd;
    @BindView(R.id.empty_view)
    LinearLayout mEmptyView;

    List<String> alarmDataSet;
    private AlarmAdapter mAdapter;

    @Inject
    public AlarmFragment() {
    }

    @Override
    public void showSomething(String something) {
        Toast.makeText(activityContext, something, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        alarmDataSet = new ArrayList<>();
        alarmDataSet.add("8:10");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alarm, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerViewAlarms.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activityContext);
        mRecyclerViewAlarms.setLayoutManager(linearLayoutManager);

        mRecyclerViewAlarms.setEmptyView(mEmptyView);

        mAdapter = new AlarmAdapter(alarmDataSet);
        mAdapter.setAlarmChangeListener(new AlarmAdapter.OnAlarmChangeListener() {
            @Override
            public void editedAlarm(String alarm) {
                createEditedAlarmPicker(alarm);
            }

            @Override
            public void removedAlarm(String time) {
                alarmDataSet.remove(time);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onEditDays(String days) {
                createEditDayPicker(days);
            }
        });
        mRecyclerViewAlarms.setAdapter(mAdapter);
    }

    private void createEditDayPicker(@Nullable String days) {
        List<String> daysListString = Arrays.asList(days.split(" "));
        List<Integer> daysListInt = new ArrayList<>();

        String[] dayArr = getResources().getStringArray(R.array.days);
        for (int i = 0; i < dayArr.length; i++) {
            if (daysListString.contains(dayArr[i])) {
                daysListInt.add(i);
            }
        }

        Integer[] selectedDays = daysListInt.toArray(new Integer[daysListInt.size()]);

        new MaterialDialog.Builder(activityContext)
                .titleColorRes(R.color.white)
                .backgroundColorRes(R.color.black)
                .contentColorRes(R.color.white)

                .title(R.string.days_title)
                .items(R.array.day_items)
                .itemsCallbackMultiChoice(selectedDays, new MaterialDialog.ListCallbackMultiChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
                        /**
                         * If you use alwaysCallMultiChoiceCallback(), which is discussed below,
                         * returning false here won't allow the newly selected check box to actually be selected
                         * (or the newly unselected check box to be unchecked).
                         * See the limited multi choice dialog example in the sample project for details.
                         **/
                        updateAlarmDays(which);
                        return true;
                    }
                })
                .positiveText(R.string.days_choose)
                .show();
    }

    private void updateAlarmDays(Integer[] selectedDays) {
        // TODO
    }

    @Override
    public void onResume() {
        super.onResume();
        // restoring alarm picker
        // dialog todo
//        TimePickerFragment alarmPicker = (TimePickerFragment) getFragmentManager().findFragmentByTag(ALARM_PICKER_TAG);
//        if (alarmPicker != null) {
//            alarmPicker.setCallbackListener(this);
//        }
    }

    private void createNewAlarmPicker() {
        TimePickerFragment dialog = new TimePickerFragment();
        // todo
//        dialog.show(getFragmentManager(), ALARM_PICKER_TAG);
    }

    private void createEditedAlarmPicker(String time) {
        TimePickerFragment dialog = new TimePickerFragment();

        Bundle args = new Bundle();
        args.putString(ARG_ALARM_TIME, time);

        dialog.setArguments(args);
        // todo
//        dialog.show(getFragmentManager(), ALARM_PICKER_TAG);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mRecyclerViewAlarms.setAdapter(null);
        unbinder.unbind();
    }

    @OnClick({R.id.ib_add, R.id.fab_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ib_add:
                createNewAlarmPicker();
                break;
            case R.id.fab_add:
                createNewAlarmPicker();
                break;
        }
    }

    @Override
    public void onTimeSelected(@Nullable Alarm previousAlarmTime, String newAlarmTime) {
//        alarmDataSet.remove(previousAlarmTime);
        alarmDataSet.add(newAlarmTime);
        mAdapter.notifyDataSetChanged();
    }
}
