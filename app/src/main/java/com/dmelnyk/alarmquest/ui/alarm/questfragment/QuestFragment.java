package com.dmelnyk.alarmquest.ui.alarm.questfragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.dmelnyk.alarmquest.R;
import com.dmelnyk.alarmquest.business.alarm.model.QuestionData;

import com.dmelnyk.alarmquest.utils.*;
import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import timber.log.Timber;

/**
 * Created by dmitry on 23.05.17.
 */

public class QuestFragment extends Fragment implements Contract.IQuestView {

    // Callback interface that informs about user's answer
    public interface SolvedQuestCallbackListener {
        void solvedQuest(boolean isCorrect);
    }

    private SolvedQuestCallbackListener callback;

    @BindView(R.id.question) TextView question;
    @BindView(R.id.answer1) Button answer1;
    @BindView(R.id.answer2) Button answer2;
    @BindView(R.id.answer3) Button answer3;
    @BindView(R.id.answer4) Button answer4;

//    @Inject
    Contract.IQuestPresenter questPresenter;
    private Unbinder unbinder;

    public QuestFragment() { }

    @OnClick({R.id.answer1, R.id.answer2, R.id.answer3, R.id.answer4})
    public void onViewClicked(View view) {

        switch (view.getId()) {
            case R.id.answer1:
                questPresenter.onButtonClick(1);
                break;
            case R.id.answer2:
                questPresenter.onButtonClick(2);
                break;
            case R.id.answer3:
                questPresenter.onButtonClick(3);
                break;
            case R.id.answer4:
                questPresenter.onButtonClick(4);
                break;
        }

    }

    public void setQuestion(QuestionData question) {
        questPresenter.takeQuestion(question);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // This makes sure that the host activity has implemented the callback interface
        // If not, it throws an exception
        try {
            callback = (SolvedQuestCallbackListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnImageClickListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

//        DaggerQuestComponent.create().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quest, container, false);

        unbinder = ButterKnife.bind(this, view);
        questPresenter.bindView(this);
        return view;
    }

    @Override
    public void setDataQuestion(@NonNull String question, @NonNull String[] answers) {
        this.question.setText(question);
        answer1.setText(answers[0]);
        answer2.setText(answers[1]);
        answer3.setText(answers[2]);
        answer4.setText(answers[3]);
    }

    @Override
    public void enableAllButtons(boolean isEnabled) {
        answer1.setEnabled(isEnabled);
        answer2.setEnabled(isEnabled);
        answer3.setEnabled(isEnabled);
        answer4.setEnabled(isEnabled);

        if (isEnabled) {
            answer1.setBackgroundResource(R.drawable.button);
            answer2.setBackgroundResource(R.drawable.button);
            answer3.setBackgroundResource(R.drawable.button);
            answer4.setBackgroundResource(R.drawable.button);

//            answer1.setTextColor(ContextCompat.getColor(getContext(), android.R.color.black));
//            answer2.setTextColor(ContextCompat.getColor(getContext(), android.R.color.black));
//            answer3.setTextColor(ContextCompat.getColor(getContext(), android.R.color.black));
//            answer4.setTextColor(ContextCompat.getColor(getContext(), android.R.color.black));
        }
    }


    @Override
    public void finishActivity() {

    }

    @Override
    public void callbackListen(boolean isCorrectAnswer) {
        Timber.d("isCorrectAnswer = " + isCorrectAnswer);
        callback.solvedQuest(isCorrectAnswer);
    }

    @Override
    public void showCorrectAnswer(int num) {
        final Animation scaleAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.scale_anim);
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 20);
        scaleAnimation.setInterpolator(interpolator);

        switch (num) {
            case 1:
                answer1.setBackgroundResource(R.drawable.button_correct);
                answer1.setTextColor(ContextCompat.getColor(getContext(), android.R.color.white));
                answer1.startAnimation(scaleAnimation);
                break;
            case 2:
                answer2.setBackgroundResource(R.drawable.button_correct);
                answer2.setTextColor(ContextCompat.getColor(getContext(), android.R.color.white));
                answer2.startAnimation(scaleAnimation);
                break;
            case 3:
                answer3.setBackgroundResource(R.drawable.button_correct);
                answer3.setTextColor(ContextCompat.getColor(getContext(), android.R.color.white));
                answer3.startAnimation(scaleAnimation);
                break;
            case 4:
                answer4.setBackgroundResource(R.drawable.button_correct);
                answer4.setTextColor(ContextCompat.getColor(getContext(), android.R.color.white));
                answer4.startAnimation(scaleAnimation);
                break;
        }
    }

    @Override
    public void showWrongAnswer(int num) {
        switch (num) {
            case 1:
                answer1.setBackgroundResource(R.drawable.button_wrong);
                break;
            case 2:
                answer2.setBackgroundResource(R.drawable.button_wrong);
                break;
            case 3:
                answer3.setBackgroundResource(R.drawable.button_wrong);
                break;
            case 4:
                answer4.setBackgroundResource(R.drawable.button_wrong);
                break;
        }
    }
}
