package com.dmelnyk.alarmquest.ui.questfragment.repository;

/**
 * Created by dmitry on 22.05.17.
 */

public class QuestionBlock {

    private int level;
    private String question;
    private String answer0;
    private String answer1;
    private String answer2;
    private String answer3;
    private int correctAnswer;

    public String getAnswer0() {
        return answer0;
    }

    public String getAnswer1() {
        return answer1;
    }

    public String getAnswer2() {
        return answer2;
    }

    public String getAnswer3() {
        return answer3;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }

    public int getLevel() {
        return level;
    }

    public String getQuestion() {
        return question;
    }

    @Override
    public String toString() {
        return "QuestionBlock{" +
                "answer0='" + answer0 + '\'' +
                ", level=" + level +
                ", question='" + question + '\'' +
                ", answer1='" + answer1 + '\'' +
                ", answer2='" + answer2 + '\'' +
                ", answer3='" + answer3 + '\'' +
                ", correctAnswer=" + correctAnswer +
                '}';
    }
}
