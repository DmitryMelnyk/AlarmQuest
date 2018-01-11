package com.dmelnyk.alarmquest.ui.alarm;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.dmelnyk.alarmquest.R;
import com.dmelnyk.alarmquest.model.QuestionData;
import com.dmelnyk.alarmquest.ui.questfragment.QuestFragment;
import com.dmelnyk.alarmquest.ui.questfragment.QuestionAdapter;
import com.dmelnyk.alarmquest.utils.AudioService;
import com.dmelnyk.alarmquest.utils.MyBounceInterpolator;

import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import me.yuqirong.cardswipelayout.CardConfig;
import me.yuqirong.cardswipelayout.CardItemTouchHelperCallback;
import me.yuqirong.cardswipelayout.CardLayoutManager;
import me.yuqirong.cardswipelayout.OnSwipeListener;

public class AlarmQuestActivity extends AppCompatActivity
    implements Contract.IAlarmQuestView,
        QuestFragment.SolvedQuestCallbackListener,
        HasSupportFragmentInjector {

    public static final String EXTRA_QUESTION_COUNT = "Questions_toSolve_count";

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentInjector;

    RecyclerView picker;
    QuestFragment questFragment;
    TextView leftToSolveQuestion;

    Animation scaleAnimation;

    @Inject
    Contract.IAlarmQuestPresenter presenter;
    private int mQuestionToSolveCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidInjection.inject(this);
        setContentView(R.layout.activity_alarm_quest);

        wakeScreen();
        // Default number of questions to solve is 2
        mQuestionToSolveCount = getIntent().getIntExtra(EXTRA_QUESTION_COUNT, 2);

        initializeViews();
        presenter.bindView(this, mQuestionToSolveCount);
    }

    private void wakeScreen() {
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);

        PowerManager.WakeLock screenWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "ScreenLock tag from AlarmListener");

        screenWakeLock.acquire();
    }

    @Override
    public void setQuestions(List<String> titles, QuestionData[] questions) {
        picker = (RecyclerView) findViewById(R.id.picker);
        picker.setItemAnimator(new DefaultItemAnimator());
        QuestionAdapter adapter = new QuestionAdapter(titles);
        picker.setAdapter(adapter);
        CardItemTouchHelperCallback cardCallback = new CardItemTouchHelperCallback(
                adapter, titles);
        cardCallback.setOnSwipedListener(new OnSwipeListener<String>() {
            @Override
            public void onSwiping(RecyclerView.ViewHolder viewHolder, float ratio, int direction) {
                QuestionAdapter.QuestionHolder myHolder = (QuestionAdapter.QuestionHolder) viewHolder;
                viewHolder.itemView.setAlpha(1 - Math.abs(ratio) * 0.2f);
                if (direction == CardConfig.SWIPING_LEFT) {
                    myHolder.binding.getRoot().setAlpha(Math.abs(ratio));
                } else if (direction == CardConfig.SWIPING_RIGHT) {
                    myHolder.binding.getRoot().setAlpha(Math.abs(ratio));
                }
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, String s, int direction) {
                presenter.questionSwiped();
            }

            @Override
            public void onSwipedClear() {
                // todo show dialog or load another data
                showFinishDialog();
            }
        });

        final ItemTouchHelper touchHelper = new ItemTouchHelper(cardCallback);
        final CardLayoutManager layoutManager = new CardLayoutManager(picker, touchHelper);
        picker.setLayoutManager(layoutManager);
        touchHelper.attachToRecyclerView(picker);

        showStartingDialog();
    }

    private void showStartingDialog() {
        String body = String.format(getString(R.string.dialog_quest), mQuestionToSolveCount);
        new MaterialDialog.Builder(this)
                .title(R.string.app_name)
                .content(body)
                .positiveText(R.string.dialog_go)
                .backgroundColorRes(R.color.black)
                .titleColorRes(R.color.white)
                .contentColorRes(R.color.white)
                .dismissListener(dismiss -> {
                    // reduce the volume to 30% of initial value
                    Intent startIntent = new Intent(this, AudioService.class);
                    startIntent.setAction(AudioService.DECREASE_VOLUME);
                    startService(startIntent);
                })
                .show();
    }

    private void initializeViews() {
        questFragment = (QuestFragment) getSupportFragmentManager().findFragmentById(R.id.questFragment);

        leftToSolveQuestion = (TextView) findViewById(R.id.left_to_solve_questions);

        // initialize animations;
        scaleAnimation = AnimationUtils.loadAnimation(this, R.anim.scale_anim);
        MyBounceInterpolator interpolator = new MyBounceInterpolator(1.0, 20);
        scaleAnimation.setInterpolator(interpolator);
        leftToSolveQuestion.setAnimation(scaleAnimation);
    }

    public void showFinishDialog() {
        new MaterialDialog.Builder(this)
                .title(R.string.alarm_clock_off)
                .content(R.string.success_message)
                .positiveText(R.string.dialog_ok)
                .backgroundColorRes(R.color.black)
                .titleColorRes(R.color.white)
                .contentColorRes(R.color.white)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        AlarmQuestActivity.this.finish();
                    }
                })
                .show();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void updateLeftToAnswerQuestionsCounter(String n) {
        leftToSolveQuestion.setText(n);
        leftToSolveQuestion.startAnimation(scaleAnimation);
    }

    @Override
    public void setQuestion(QuestionData question) {
        questFragment.setQuestion(question);
    }

    @Override
    public void success() {
        showFinishDialog();
        Intent startIntent = new Intent(getApplicationContext(), AudioService.class);
        startIntent.setAction(AudioService.STOP);
        startService(startIntent);
    }

    @Override
    public void solvedQuestion(boolean isCorrect) {
        presenter.isAnswerCorrect(isCorrect);
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentInjector;
    }
}
