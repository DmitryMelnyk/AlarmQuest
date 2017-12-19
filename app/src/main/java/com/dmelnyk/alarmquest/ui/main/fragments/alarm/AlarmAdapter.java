package com.dmelnyk.alarmquest.ui.main.fragments.alarm;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dmelnyk.alarmquest.R;
import com.suke.widget.SwitchButton;

/**
 * Created by d264 on 12/19/17.
 */

public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.ItemViewHolder> {


    public class ItemViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextViewTime;
        public TextView mTextViewDays;
        public SwitchButton mSwitcher;

        public ItemViewHolder(View itemView) {
            super(itemView);
            mTextViewTime = (TextView) itemView.findViewById(R.id.item_tv_alarm_time);
            mTextViewDays = (TextView) itemView.findViewById(R.id.item_tv_alarm_days);
            mSwitcher = (SwitchButton) itemView.findViewById(R.id.item_switch_button);
        }
    }

    public AlarmAdapter() {
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.alarm_item, parent, false);

        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        holder.mSwitcher.setChecked(true);
//        holder.mTextViewTime.setText();
//        holder.mTextViewDays.setText();
    }

    @Override
    public int getItemCount() {
        return 7;
    }
}
