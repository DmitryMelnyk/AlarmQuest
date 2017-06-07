package com.dmelnyk.alarmquest.ui.alarm;

import android.content.res.TypedArray;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.dmelnyk.alarmquest.R;
import com.dmelnyk.alarmquest.application.AlarmQuestApplication;
import com.dmelnyk.alarmquest.business.alarm.model.QuestionData;
import com.dmelnyk.alarmquest.data.DataUtil;
import com.dmelnyk.alarmquest.data.question.QuestionBlock;
import com.dmelnyk.alarmquest.ui.alarm.di.AlarmQuestModule;
import com.dmelnyk.alarmquest.ui.alarm.questfragment.QuestFragment;
import com.igalata.bubblepicker.BubblePickerListener;
import com.igalata.bubblepicker.adapter.BubblePickerAdapter;
import com.igalata.bubblepicker.model.BubbleGradient;
import com.igalata.bubblepicker.model.PickerItem;
import com.igalata.bubblepicker.rendering.BubblePicker;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import javax.inject.Inject;

public class AlarmQuestActivity extends AppCompatActivity
    implements Contract.IAlarmQuestView, QuestFragment.SolvedQuestCallbackListener {

    BubblePicker picker;
    QuestFragment questFragment;

    @Inject
    Contract.IAlarmQuestPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_quest);

        // initialize dagger2
        AlarmQuestApplication.get(this).getAppComponent()
                .add(new AlarmQuestModule()).inject(this);

        initializeViews();
        presenter.bindView(this);
    }

    @Override
    public void setBubbleTitles(String[] titles) {
        setAdapter(titles);
    }

    private void setAdapter(String[] titles) {
        final TypedArray colors = getResources().obtainTypedArray(R.array.colors);

        BubblePickerAdapter adapter = new BubblePickerAdapter() {
            @Override
            public int getTotalCount() {
                return titles.length;
            }

            @NotNull
            @Override
            public PickerItem getItem(int position) {
                PickerItem item = new PickerItem();
                item.setTitle(titles[position]);
                item.setGradient(new BubbleGradient(colors.getColor((position * 2) % 8, 0),
                        colors.getColor((position * 2)% 8 + 1, 0), BubbleGradient.VERTICAL));
                // typeFase
                item.setTextColor(ContextCompat.getColor(AlarmQuestActivity.this, android.R.color.white));
                return item;
            }
        };
        picker.setAdapter(adapter);

        BubblePickerListener listener = new BubblePickerListener() {
            @Override
            public void onBubbleSelected(@NotNull PickerItem pickerItem) {
                String title = pickerItem.getTitle();
                presenter.pickQuestion(title);
            }

            @Override
            public void onBubbleDeselected(@NotNull PickerItem pickerItem) { /* NOP */ }
        };
        picker.setListener(listener);
    }

    private void initializeViews() {
        picker = (BubblePicker) findViewById(R.id.picker);
        picker.setBubbleSize(100);
        picker.setCenterImmediately(true);

        questFragment = (QuestFragment) getSupportFragmentManager().findFragmentById(R.id.questFragment);
    }

    @Override
    protected void onResume() {
        super.onResume();
        picker.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        picker.onPause();
    }

    @Override
    public void updateLeftToAnswerQuestionsCounter(String n) {
        // TODO
    }

    @Override
    public void setQuestion(QuestionData question) {
        questFragment.setQuestion(question);
    }

    @Override
    public void success() {
        Toast.makeText(this, getString(R.string.success_message), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showHasAnsweredMessage() {
        Toast.makeText(this, getString(R.string.error_question), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void solvedQuest(boolean isCorrect) {
        presenter.isAnswerCorrect(isCorrect);
    }
}
