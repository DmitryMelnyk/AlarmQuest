package com.dmelnyk.alarmquest.ui.alarm;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.dmelnyk.alarmquest.R;
import com.dmelnyk.alarmquest.application.AlarmQuestApplication;
import com.dmelnyk.alarmquest.business.alarm.model.QuestionData;
import com.dmelnyk.alarmquest.ui.alarm.di.AlarmQuestModule;
import com.dmelnyk.alarmquest.ui.alarm.questfragment.QuestFragment;
import com.dmelnyk.alarmquest.utils.MyBounceInterpolator;
import com.dmelnyk.alarmquest.utils.AudioService;
import com.igalata.bubblepicker.BubblePickerListener;
import com.igalata.bubblepicker.adapter.BubblePickerAdapter;
import com.igalata.bubblepicker.model.BubbleGradient;
import com.igalata.bubblepicker.model.PickerItem;
import com.igalata.bubblepicker.rendering.BubblePicker;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class AlarmQuestActivity extends AppCompatActivity
    implements Contract.IAlarmQuestView, QuestFragment.SolvedQuestCallbackListener {

    public static final String EXTRA_QUESTION_COUNT = "Questions_toSolve_count";

    BubblePicker picker;
    QuestFragment questFragment;
    TextView leftToSolveQuestion;

    Animation scaleAnimation;

    private PickerItem mSelectedItem;

    @Inject
    Contract.IAlarmQuestPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_quest);

        // Default number of questions to solve is 2
        int questionToSolveCount = getIntent().getIntExtra(EXTRA_QUESTION_COUNT, 2);

        // initialize dagger2
        AlarmQuestApplication.get(this).getAppComponent()
                .add(new AlarmQuestModule()).inject(this);

        initializeViews();
        presenter.bindView(this, questionToSolveCount);
    }

    private void setAdapter(String[] titles) {

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
                item.setGradient(new BubbleGradient(
                        ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary),
                        ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark),
                        BubbleGradient.VERTICAL));

                item.setTextColor(ContextCompat.getColor(AlarmQuestActivity.this, R.color.white));
                return item;
            }
        };
        picker.setAdapter(adapter);

        BubblePickerListener listener = new BubblePickerListener() {
            @Override
            public void onBubbleSelected(@NotNull PickerItem pickerItem) {
                // deselect previousSelectedItem
                if (mSelectedItem != null) {
                    mSelectedItem.setSelected(false);
                }
                mSelectedItem = pickerItem;
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
//        picker.setCenterImmediately(true);

        questFragment = (QuestFragment) getSupportFragmentManager().findFragmentById(R.id.questFragment);

        leftToSolveQuestion = (TextView) findViewById(R.id.left_to_solve_questions);

        // initialize animations;
        scaleAnimation = AnimationUtils.loadAnimation(this, R.anim.scale_anim);
        MyBounceInterpolator interpolator = new MyBounceInterpolator(1.0, 20);
        scaleAnimation.setInterpolator(interpolator);
        leftToSolveQuestion.setAnimation(scaleAnimation);
    }

    public void showAlertDialog() {
        SweetAlertDialog dialog = new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText(getString(R.string.alarm_clock_off))
                .setContentText(getString(R.string.success_message));
        dialog.setOnDismissListener(view -> AlarmQuestActivity.this.finish());
        dialog.show();
    }
    @Override
    protected void onResume() {
        super.onResume();
        picker.onResume();
        //TODO
//        Intent startIntent = new Intent(this, AudioService.class);
//        startIntent.setAction(AudioService.DECREASE_VOLUME);
//        startService(startIntent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        picker.onPause();
    }

    @Override
    public void updateLeftToAnswerQuestionsCounter(String n) {
        leftToSolveQuestion.setText(n);
        leftToSolveQuestion.startAnimation(scaleAnimation);
    }

    @Override
    public void setBubbleTitles(String[] titles) {
        setAdapter(titles);
        picker.invalidate();
    }

    @Override
    public void setQuestion(QuestionData question) {
        questFragment.setQuestion(question);
    }

    @Override
    public void success() {
        showAlertDialog();
        Intent startIntent = new Intent(getApplicationContext(), AudioService.class);
        startIntent.setAction(AudioService.STOP);
        startService(startIntent);
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
