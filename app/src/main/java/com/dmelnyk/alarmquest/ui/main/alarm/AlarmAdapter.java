package com.dmelnyk.alarmquest.ui.main.alarm;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dmelnyk.alarmquest.R;
import com.kyleduo.switchbutton.SwitchButton;

import java.util.List;

/**
 * Created by d264 on 12/19/17.
 */

public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.ItemViewHolder> {


    private final List<String> mDataSet;
    private OnAlarmChangeListener mCallbackListener;

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        public ConstraintLayout mItemLayout;
        public TextView mTextViewTime;
        public TextView mTextViewDays;
        public SwitchButton mSwitcher;

        public ItemViewHolder(View itemView) {
            super(itemView);
            mItemLayout = (ConstraintLayout) itemView.findViewById(R.id.alarm_item_layout);
            mTextViewTime = (TextView) itemView.findViewById(R.id.item_tv_alarm_time);
            mTextViewDays = (TextView) itemView.findViewById(R.id.item_tv_alarm_days);
            mSwitcher = (SwitchButton) itemView.findViewById(R.id.item_switch_button);
        }
    }

    public AlarmAdapter(List<String> dataSet) {
        mDataSet = dataSet;
    }

    public void setAlarmChangeListener(OnAlarmChangeListener listener) {
        this.mCallbackListener = listener;
    }

    public interface OnAlarmChangeListener {
        void editedAlarm(String alarm);
        void removedAlarm(String time);
        void onEditDays(String days);
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
        holder.mTextViewTime.setText(mDataSet.get(position));
//        holder.mTextViewDays.setText();


        holder.mItemLayout.setOnLongClickListener(view -> {
            if (isCallbackListenerImplemented()) {
                mCallbackListener.removedAlarm(mDataSet.get(position));
            }

            return true;
        });

        holder.mTextViewTime.setOnClickListener(view -> {
            if (isCallbackListenerImplemented()) {
                mCallbackListener.editedAlarm(mDataSet.get(position));
            }
        });

        holder.mTextViewDays.setOnClickListener(view -> {
            if (isCallbackListenerImplemented()) {
                mCallbackListener.onEditDays(holder.mTextViewDays.getText().toString());
            }
        });
    }

    private boolean isCallbackListenerImplemented() {
        if (mCallbackListener == null) {
            throw new ClassCastException(AlarmAdapter.OnAlarmChangeListener.class + " not implemented");
        } else return true;
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}
