package com.example.task02.QuizModel;

public class questions {
    private int questionTextId;
    private boolean answer;

    public questions(int questionTextId, boolean answer) {
        this.questionTextId = questionTextId;
        this.answer = answer;
    }

    public int getQuestionTextId() {
        return questionTextId;
    }

    public boolean isAnswer() {
        return answer;
    }
}