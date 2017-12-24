package com.dmelnyk.alarmquest.ui.main.stopwatch.view;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.MaterialDialog;
import com.dmelnyk.alarmquest.R;
import com.dmelnyk.alarmquest.databinding.FragmentAlarmListBinding;
import com.dmelnyk.alarmquest.db.entity.AlarmEntity;
import com.dmelnyk.alarmquest.model.Alarm;
import com.dmelnyk.alarmquest.ui.common.view.BaseViewFragment;
import com.dmelnyk.alarmquest.ui.main.core.TimePickerFragment;
import com.dmelnyk.alarmquest.ui.main.stopwatch.AlarmAdapter;
import com.dmelnyk.alarmquest.ui.main.stopwatch.presenter.AlarmListPresenter;
import com.dmelnyk.alarmquest.viewmodel.AlarmListViewModel;

import java.util.List;

import static com.dmelnyk.alarmquest.ui.main.core.TimePickerFragment.ARG_ALARM;

/**
 * Created by d264 on 12/18/17.
 */

public class AlarmListFragment extends BaseViewFragment<AlarmListPresenter>
        implements AlarmListView, TimePickerFragment.OnTimeSelectedListener {

    private static final String ALARM_PICKER_TAG = "ALARM_PICKER_TAG";

    private AlarmAdapter mAlarmAdapter;

    private FragmentAlarmListBinding mBinding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_alarm_list, container, false);
        mBinding.setPresenter(presenter);

        mAlarmAdapter = new AlarmAdapter(presenter);
        mBinding.rvAlarmList.setAdapter(mAlarmAdapter);

        return mBinding.getRoot();
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
        // restoring dialog
        setDialogCallback(this);
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
//                mBinding.executePendingBindings();
            }
        });
    }

    @Override
    public void showDayPicker(@Nullable final Alarm alarm, Integer[] selectedDays) {
        if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
            new MaterialDialog.Builder(activityContext)
                    .titleColorRes(R.color.white)
                    .backgroundColorRes(R.color.black)
                    .contentColorRes(R.color.white)
                    .title(R.string.days_title)
                    .items(R.array.day_items)
                    .itemsCallbackMultiChoice(selectedDays, new MaterialDialog.ListCallbackMultiChoice() {
                        @Override
                        public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
                            presenter.onDaySelected(alarm, which);
                            return true;
                        }
                    })
                    .positiveText(R.string.days_choose)
                    .show();
        }
    }

    @Override
    public void showNewAlarmPicker() {
        if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
            TimePickerFragment dialog = new TimePickerFragment();
            dialog.setCallback(this);
            showDialog(dialog, ALARM_PICKER_TAG);
        }
    }

    @Override
    public void showEditAlarmPicker(final Alarm alarm) {
        if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
            TimePickerFragment dialog = new TimePickerFragment();
            Bundle args = new Bundle();
            args.putParcelable(ARG_ALARM, alarm);
            dialog.setArguments(args);
            dialog.setCallback(this);
            showDialog(dialog, ALARM_PICKER_TAG);
        }
    }

    @Override
    public void onTimeSelected(@Nullable Alarm previousAlarm, String newAlarmTime) {
        presenter.onTimeSelected(previousAlarm, newAlarmTime);
    }
}
