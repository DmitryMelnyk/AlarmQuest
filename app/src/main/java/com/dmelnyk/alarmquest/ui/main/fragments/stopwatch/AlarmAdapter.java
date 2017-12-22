package com.dmelnyk.alarmquest.ui.main.fragments.stopwatch;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.dmelnyk.alarmquest.R;
import com.dmelnyk.alarmquest.databinding.AlarmItemBindBinding;
import com.dmelnyk.alarmquest.model.Alarm;

import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Created by d264 on 12/19/17.
 */

public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.AlarmViewHolder> {

    List<? extends Alarm> mAlarmList;

    @Nullable
    private OnAlarmClickCallback mAlarmClickCallback;

    public AlarmAdapter(@Nullable OnAlarmClickCallback clickCallback) {
        mAlarmClickCallback = clickCallback;
    }

    public void setAlarmList(final List<? extends Alarm> alarmList) {
        if (mAlarmList == null) {
            mAlarmList = alarmList;
            notifyItemRangeInserted(0, alarmList.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mAlarmList.size();
                }

                @Override
                public int getNewListSize() {
                    return alarmList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return mAlarmList.get(oldItemPosition).getId() ==
                            alarmList.get(newItemPosition).getId();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    Alarm newAlarm = alarmList.get(newItemPosition);
                    Alarm oldAlarm = mAlarmList.get(oldItemPosition);
                    return newAlarm.getId() == oldAlarm.getId()
                            && newAlarm.isEnable() == oldAlarm.isEnable()
                            && newAlarm.getTime().equals(oldAlarm.getTime())
                            && newAlarm.getDays().equals(oldAlarm.getDays());
                }
            });
            mAlarmList = alarmList;
            result.dispatchUpdatesTo(this);
        }
    }

    @Override
    public AlarmViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        AlarmItemBindBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.alarm_item_bind, parent, false);
        binding.setCallback(mAlarmClickCallback);
        return new AlarmViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(AlarmViewHolder holder, int position) {
        holder.binding.setAlarm(mAlarmList.get(position));
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mAlarmList == null ? 0 : mAlarmList.size();
    }

    public class AlarmViewHolder extends RecyclerView.ViewHolder {

        final AlarmItemBindBinding binding;

        public AlarmViewHolder(AlarmItemBindBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
