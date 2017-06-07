package com.dmelnyk.alarmquest.business.alarm.model;

/**
 * Created by dmitry on 23.05.17.
 */

public class QuestionData {

    private String question;
    private String[] answers = new String[4];
    private int correctAnswer;
    public int level;

    public QuestionData() {}

    public String[] getAnswers() {
        return answers;
    }

    public String getQuestion() {
        return question;
    }

    public int getCorrectAnswer() { return correctAnswer; }

    private int getLevel() { return level; }

    public NewBuilder Builder() {
        return new QuestionData().new NewBuilder();
    }

    public class NewBuilder {
        private NewBuilder() {}

        public NewBuilder setQuestion(String text) {
            question = text;
            return this;
        }

        public NewBuilder addAnswer1(String answer) {
            answers[0] = answer;
            return this;
        }

        public NewBuilder addAnswer2(String answer) {
            answers[1] = answer;
            return this;
        }

        public NewBuilder addAnswer3(String answer) {
            answers[2] = answer;
            return this;
        }

        public NewBuilder addAnswer4(String answer) {
            answers[3] = answer;
            return this;
        }

        public NewBuilder addCorrectAnswer(int answer) {
            correctAnswer = answer;
            return this;
        }

        public NewBuilder addLevel(int level) {
            QuestionData.this.level = level;
            return this;
        }

        public QuestionData build() {
            return QuestionData.this;
        }
    }
}
