package com.dmelnyk.alarmquest.ui.main.fragments.alarm;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.dmelnyk.alarmquest.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by d264 on 12/18/17.
 */

public class AlarmFragment extends Fragment {

    @BindView(R.id.fab_add)
    FloatingActionButton mFabAdd;
    @BindView(R.id.rv_alarm_list)
    RecyclerView mRecyclerViewAlarms;
    Unbinder unbinder;
    @BindView(R.id.ib_add)
    ImageView mImageViewAdd;

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
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerViewAlarms.setLayoutManager(linearLayoutManager);

        AlarmAdapter mAdapter = new AlarmAdapter();
        mRecyclerViewAlarms.setAdapter(mAdapter);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.ib_add, R.id.fab_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ib_add:
                onAddClicked();
                break;
            case R.id.fab_add:
                onAddClicked();
                break;
        }
    }

    private void onAddClicked() {
        Toast.makeText(getContext(), "Add", Toast.LENGTH_SHORT).show();
    }
}
