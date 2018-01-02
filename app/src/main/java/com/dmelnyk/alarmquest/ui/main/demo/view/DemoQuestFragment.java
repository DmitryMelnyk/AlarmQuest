package com.dmelnyk.alarmquest.ui.main.demo.view;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.dmelnyk.alarmquest.R;
import com.dmelnyk.alarmquest.ui.questfragment.QuestFragment;
import com.dmelnyk.alarmquest.ui.common.view.BaseFragment;
import com.dmelnyk.alarmquest.ui.main.demo.viewmodel.DemoQuestViewModel;
import com.dmelnyk.alarmquest.ui.questfragment.QuestionAdapter;
import com.dmelnyk.alarmquest.utils.MyBounceInterpolator;

import java.util.List;

import javax.inject.Inject;

import me.yuqirong.cardswipelayout.CardConfig;
import me.yuqirong.cardswipelayout.CardItemTouchHelperCallback;
import me.yuqirong.cardswipelayout.CardLayoutManager;
import me.yuqirong.cardswipelayout.OnSwipeListener;

/**
 * Created by d264 on 1/2/18.
 */

public class DemoQuestFragment extends BaseFragment
    implements QuestFragment.SolvedQuestCallbackListener {

    private int SOLVE_QUESTIONS = 2; // default

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private RecyclerView mQuestionsRecyclerView;
    private QuestFragment mQuestFragment;
    private TextView leftToSolveQuestion;

    private Animation scaleAnimation;
    private DemoQuestViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_demo_quest, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeViews();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(DemoQuestViewModel.class);

        viewModel.getQuestions().observe(this, questions -> {
            initializeQuestionRecyclerView(questions);
        });

        viewModel.getAnswers().observe(this, answer -> {
            mQuestFragment.setQuestion(answer);
        });

        viewModel.getStillQuestionsToSlove().observe(this, questionToSolve -> {
            leftToSolveQuestion.setText("" + questionToSolve);
        });

        viewModel.isSuccess().observe(this, isQuestSolved -> {
            if (isQuestSolved) {
                showSuccessAlertDialog();
            } else {
                showLostAlertDialog();
            }
        });

        showStartingDialog();
    }

    private void initializeQuestionRecyclerView(List<String> questions) {
        QuestionAdapter adapter = new QuestionAdapter(questions);
        mQuestionsRecyclerView.setAdapter(adapter);
        CardItemTouchHelperCallback cardCallback = new CardItemTouchHelperCallback(
                adapter, questions);
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
                viewModel.onQuestionSwiped();
            }

            @Override
            public void onSwipedClear() {
                // todo show dialog or load another data
            }
        });

        final ItemTouchHelper touchHelper = new ItemTouchHelper(cardCallback);
        final CardLayoutManager layoutManager = new CardLayoutManager(mQuestionsRecyclerView, touchHelper);
        mQuestionsRecyclerView.setLayoutManager(layoutManager);
        touchHelper.attachToRecyclerView(mQuestionsRecyclerView);
    }

    private void initializeViews() {
        mQuestionsRecyclerView = (RecyclerView) getView().findViewById(R.id.picker);

        mQuestFragment = (QuestFragment) childFragmentManager.findFragmentById(R.id.questFragment);
        mQuestFragment.setCallback(this);
        leftToSolveQuestion = (TextView) getView().findViewById(R.id.left_to_solve_questions);

        // initialize animations;
        scaleAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.scale_anim);
        MyBounceInterpolator interpolator = new MyBounceInterpolator(1.0, 20);
        scaleAnimation.setInterpolator(interpolator);
        leftToSolveQuestion.setAnimation(scaleAnimation);
        mQuestionsRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }


    private void showStartingDialog() {
        String body = String.format(getString(R.string.dialog_quest), SOLVE_QUESTIONS);
        new MaterialDialog.Builder(getContext())
                .title(R.string.app_name)
                .content(body)
                .positiveText(R.string.dialog_go)
                .backgroundColorRes(R.color.black)
                .titleColorRes(R.color.white)
                .contentColorRes(R.color.white)
                .show();
    }

    private void showSuccessAlertDialog() {
        new MaterialDialog.Builder(getContext())
                .title(R.string.alarm_clock_off)
                .content(R.string.success_message)
                .positiveText(R.string.dialog_ok)
                .backgroundColorRes(R.color.black)
                .titleColorRes(R.color.white)
                .contentColorRes(R.color.white)
                .show();
    }

    private void showLostAlertDialog() {
        new MaterialDialog.Builder(getContext())
                .title(R.string.alarm_clock_off)
                .content(R.string.success_message)
                .positiveText(R.string.dialog_ok)
                .backgroundColorRes(R.color.black)
                .titleColorRes(R.color.white)
                .contentColorRes(R.color.white)
                .show();
    }

    @Override
    public void solvedQuestion(boolean isCorrect) {
        viewModel.isAnswerCorrect(isCorrect);
    }
}
