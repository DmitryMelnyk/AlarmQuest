package com.dmelnyk.alarmquest.data;

import android.app.Application;
import android.content.Context;

import com.dmelnyk.alarmquest.ui.questfragment.repository.QuestionBlock;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

import javax.inject.Inject;

/**
 * Created by dmitry on 23.05.17.
 */

public class DataUtil {

    private QuestionBlock[] questions;

    @Inject
    public DataUtil(Application context) {
        String jsonQuestions = getJsonQuestions(context);
        questions = getQuestions(jsonQuestions);
    }

    private QuestionBlock[] getQuestions(String jsonQuestions) {
        Gson gson = new Gson();
        QuestionBlock[] questions = gson.fromJson(jsonQuestions, QuestionBlock[].class);
        return questions;
    }

    private String getJsonQuestions(Context context) {
        StringBuilder text = new StringBuilder();

        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(context.getAssets().open("questions/questions.json")));
            String line;

            while ((line = reader.readLine()) != null) {
                text.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return text.toString();
    }

    public QuestionBlock getRandomQuestion() {
        int randNumber = new Random().nextInt(questions.length);
        return questions[randNumber];
    }

    public QuestionBlock[] getQuestions(int count) {
        ArrayList<QuestionBlock> questions = new ArrayList<>();
        while (questions.size() < count) {
            QuestionBlock question = getRandomQuestion();
            if (!questions.contains(question)) questions.add(question);
        }

        return questions.toArray(new QuestionBlock[questions.size()]);
    }
}
