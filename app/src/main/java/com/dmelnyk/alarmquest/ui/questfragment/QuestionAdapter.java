package com.dmelnyk.alarmquest.ui.questfragment;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.dmelnyk.alarmquest.R;
import com.dmelnyk.alarmquest.databinding.QuestionItemBinding;

import java.util.List;

/**
 * Created by d264 on 12/31/17.
 */

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.QuestionHolder> {
    private final List<String> mQuestionTitles;

    public QuestionAdapter(List<String> titles) {
        mQuestionTitles = titles;
    }

    @Override
    public QuestionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        QuestionItemBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.question_item, parent, false);
        return new QuestionHolder(binding);
    }

    @Override
    public void onBindViewHolder(QuestionAdapter.QuestionHolder holder, int position) {
        holder.binding.setQuestion(mQuestionTitles.get(position));
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mQuestionTitles.size();
    }

    public class QuestionHolder extends RecyclerView.ViewHolder {
        public QuestionItemBinding binding;

        public QuestionHolder(QuestionItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
