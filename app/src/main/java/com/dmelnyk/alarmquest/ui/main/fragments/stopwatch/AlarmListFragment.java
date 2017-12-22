package com.dmelnyk.alarmquest.ui.main.fragments.stopwatch;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.MaterialDialog;
import com.dmelnyk.alarmquest.R;
import com.dmelnyk.alarmquest.application.AlarmQuestApplication;
import com.dmelnyk.alarmquest.databinding.FragmentAlarmListBinding;
import com.dmelnyk.alarmquest.db.entity.AlarmEntity;
import com.dmelnyk.alarmquest.model.Alarm;
import com.dmelnyk.alarmquest.ui.main.core.DayConverter;
import com.dmelnyk.alarmquest.ui.main.core.TimePickerFragment;
import com.dmelnyk.alarmquest.viewmodel.AlarmListViewModel;

import java.util.List;

import static com.dmelnyk.alarmquest.ui.main.core.TimePickerFragment.ARG_ALARM;

/**
 * Created by d264 on 12/18/17.
 */

public class AlarmListFragment extends Fragment implements TimePickerFragment.OnTimeSelectedListener {

    private static final String ALARM_PICKER_TAG = "ALARM_PICKER_TAG";
    private static final String TOMORROW = "6"; // Sunday

    private AlarmAdapter mAlarmAdapter;

    private FragmentAlarmListBinding mBinding;
//    private DayConverter mDayConverter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_alarm_list, container, false);
        mBinding.setAddCallback(mAddCallback);

        mAlarmAdapter = new AlarmAdapter(mAlarmClickCallback);
        mBinding.rvAlarmList.setAdapter(mAlarmAdapter);

        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        final AlarmListViewModel viewModel =
                ViewModelProviders.of(this).get(AlarmListViewModel.class);

        subscribeUi(viewModel);
    }

    @Override
    public void onResume() {
        super.onResume();
        // restoring alarm picker
        // dialog
        TimePickerFragment alarmPicker = (TimePickerFragment) getFragmentManager().findFragmentByTag(ALARM_PICKER_TAG);
        if (alarmPicker != null) {
            alarmPicker.setCallbackListener(this);
        }
    }


    private void subscribeUi(AlarmListViewModel viewModel) {
        // Update the list when the data changes
        viewModel.getAlarms().observe(this, new Observer<List<AlarmEntity>>() {
            @Override
            public void onChanged(@Nullable List<AlarmEntity> alarmEntities) {
                if (alarmEntities != null) {
                    if (alarmEntities.isEmpty()) {
                        mBinding.setIsEmpty(true);
                    } else {
                        mBinding.setIsEmpty(false);
                    }
                    mAlarmAdapter.setAlarmList(alarmEntities);
                } else {
                    mBinding.setIsEmpty(true);
                }
                // espresso does not know how to wait for data binding's loop so we execute changes
                // sync.
                mBinding.executePendingBindings();
            }
        });
    }

    private void createEditDayPicker(@Nullable final Alarm alarm, Integer[] selectedDays) {
        Alarm fAlarm = alarm;
        new MaterialDialog.Builder(getContext())
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
                        updateAlarmDays(fAlarm, which);
                        return true;
                    }
                })
                .positiveText(R.string.days_choose)
                .show();
    }

    private void updateAlarmDays(final Alarm alarm, Integer[] selectedDays) {
        /*todo: here in presenter remove Alarm,*/
        /*todo: then calc time to alarm (off, today, tomorrow, in a 3 day... and start Alarm,*/
        /*todo: write to db correct days. If selected Days is empty -> tomorrow or */
        String days = "";
        if (selectedDays != null) {
            for (Integer day : selectedDays) {
                days += day + " ";
            }
            if (days.length() > 1) {
                days = days.substring(0, days.length() - 1);
            }
        } else {
            days = TOMORROW;
        }

        Alarm newAlarm = new AlarmEntity(alarm);
        newAlarm.setDays(days);

        ((AlarmQuestApplication) getActivity().getApplication()).getRepository()
                .updateAlarmDays(newAlarm);
    }

    private void createNewAlarmPicker() {
        TimePickerFragment dialog = new TimePickerFragment();
        dialog.setCallbackListener(this);
        dialog.show(getFragmentManager(), ALARM_PICKER_TAG);
    }

    private void createEditedAlarmPicker(final Alarm alarm) {
        TimePickerFragment dialog = new TimePickerFragment();
        dialog.setCallbackListener(this);

        Bundle args = new Bundle();
        args.putParcelable(ARG_ALARM, alarm);

        dialog.setArguments(args);
        dialog.show(getFragmentManager(), ALARM_PICKER_TAG);
    }

    private void removeAlarm(Alarm alarm) {
        ((AlarmQuestApplication) getActivity().getApplication())
                .getRepository().removeAlarm(alarm);
    }

    @Override
    public void onTimeSelected(@Nullable Alarm previousAlarm, String newAlarmTime) {
        AlarmEntity newAlarm = null;

        if (previousAlarm == null) {
            newAlarm = new AlarmEntity();
            newAlarm.setEnable(true);
            newAlarm.setDays(TOMORROW);
            newAlarm.setTime(newAlarmTime);
            ((AlarmQuestApplication) getActivity().getApplication())
                    .getRepository().addAlarm(newAlarm);
        } else {
            previousAlarm.setTime(newAlarmTime);
            ((AlarmQuestApplication) getActivity().getApplication())
                    .getRepository().updateAlarm(previousAlarm);
        }

    }

    private OnAlarmClickCallback mAlarmClickCallback = new OnAlarmClickCallback() {
        @Override
        public void editedAlarm(Alarm alarm) {
            Alarm rawAlarm = DayConverter.convertToDbLook(alarm,
                    getContext().getApplicationContext().getResources().getStringArray(R.array.days));
            if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED))
                createEditedAlarmPicker(rawAlarm);
        }

        @Override
        public boolean removedAlarm(View view, Alarm alarm) {
            if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
                removeAlarm(alarm);
            }
            return true;
        }

        @Override
        public void onEditedDays(Alarm alarm) {
            if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
                Integer[] numericDays = DayConverter.convertDayToNumberView(
                        getContext().getApplicationContext().getResources().getStringArray(R.array.days),
                        alarm.getDays()); // String days
                createEditDayPicker(alarm, numericDays);
            }
        }

        @Override
        public void onCheckedChanged(Alarm alarm) {
            Alarm nAlarm = new AlarmEntity(alarm);
            nAlarm.setEnable(!nAlarm.isEnable());
            Log.e("!!!", "onEnable="+ nAlarm.isEnable());
            Alarm rawAlarm = DayConverter.convertToDbLook(nAlarm,
                    getContext().getApplicationContext().getResources().getStringArray(R.array.days));

            if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
                ((AlarmQuestApplication) getActivity().getApplication()).getRepository()
                        .updateAlarm(rawAlarm);
            }
        }
    };

    private OnAddClickCallback mAddCallback = () -> createNewAlarmPicker();
}
