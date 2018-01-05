package com.dmelnyk.alarmquest.ui.main.alarm_list.view;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.dmelnyk.alarmquest.R;
import com.dmelnyk.alarmquest.databinding.FragmentAlarmListBinding;
import com.dmelnyk.alarmquest.model.Alarm;
import com.dmelnyk.alarmquest.ui.common.view.BaseFragment;
import com.dmelnyk.alarmquest.ui.main.core.TimePickerFragment;
import com.dmelnyk.alarmquest.ui.main.alarm_list.AlarmAdapter;
import com.dmelnyk.alarmquest.ui.main.alarm_list.viewmodel.AlarmListViewModel;

import javax.inject.Inject;
import static com.dmelnyk.alarmquest.ui.main.core.TimePickerFragment.ARG_ALARM;

/**
 * Created by d264 on 12/18/17.
 */

public class AlarmListFragment extends BaseFragment
        implements TimePickerFragment.OnTimeSelectedListener {

    private static final String ALARM_PICKER_TAG = "ALARM_PICKER_TAG";

    private AlarmAdapter mAlarmAdapter;

    private FragmentAlarmListBinding mBinding;

    private AlarmListViewModel viewModel;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private String tag = getClass().getSimpleName();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_alarm_list, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        viewModel = ViewModelProviders.of(this, viewModelFactory)
                        .get(AlarmListViewModel.class);

        mBinding.setViewModel(viewModel);
        mAlarmAdapter = new AlarmAdapter(viewModel);
        mBinding.rvAlarmList.setAdapter(mAlarmAdapter);
        subscribeUi(viewModel);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e(tag, "onResume()");
        // restoring dialog
        setDialogCallback(this);
    }

    private void subscribeUi(AlarmListViewModel viewModel) {
        // Update the list when the data changes
        viewModel.getAlarms().observe(this, alarmEntities ->  {
            Log.e(tag, "getAlarms()");
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
        });

        viewModel.getDialogs().doOnSubscribe(disposable -> {
                    Log.d(tag, "Subscribed for receiving dialogs messages.");
                    addSubscription(disposable);
                })
                .subscribe(dialog -> {

                Log.e(tag, "getDialog=" + dialog);
                    if (dialog == null) return;
                    switch (dialog.getType()) {
                        case TIME_PICKER_NEW:
                            showNewAlarmPicker();
                            break;
                        case TIME_PICKER_EDIT:
                            showEditAlarmPicker(dialog.getAlarm());
                            break;
                        case DATE_PICKER_EDIT:
                            showDayPicker(dialog.getAlarm(), dialog.getDays());
                            break;
                    }
        });

        viewModel.getToasts()
                .doOnSubscribe(disposable -> {
                    Log.d(tag, "Subscribed for receiving toast messages");
                    addSubscription(disposable); })
                .subscribe(msg -> {
                    Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                });
    }

    public void showDayPicker(@Nullable final Alarm alarm, Integer[] selectedDays) {
        if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
            new MaterialDialog.Builder(getContext())
                    .titleColorRes(R.color.white)
                    .backgroundColorRes(R.color.black)
                    .contentColorRes(R.color.white)
                    .title(R.string.days_title)
                    .items(R.array.day_items)
                    .itemsCallbackMultiChoice(selectedDays, new MaterialDialog.ListCallbackMultiChoice() {
                        @Override
                        public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
                            viewModel.onDaySelected(alarm, which);
                            return true;
                        }
                    })
                    .positiveText(R.string.days_choose)
                    .negativeText(R.string.days_cancel)
                    .show();
        }
    }

    public void showNewAlarmPicker() {
        if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
            TimePickerFragment dialog = new TimePickerFragment();
            dialog.setCallback(this);
            showDialog(dialog, ALARM_PICKER_TAG);
        }
    }

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
        viewModel.onTimeSelected(previousAlarm, newAlarmTime);
    }
}
