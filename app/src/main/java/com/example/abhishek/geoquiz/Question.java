package com.example.abhishek.geoquiz;

/**
 * Created by Abhishek on 1/15/2018.
 */

class Question {
    private int reourceId;
    private boolean trueAnswer;

    public Question(int resId, boolean trueAns) {

        this.reourceId = resId;
        this.trueAnswer = trueAns;

    }

    public int getReourceId() {
        return reourceId;
    }

    public void setReourceId(int reourceId) {
        this.reourceId = reourceId;
    }

    public boolean isTrueAnswer() {
        return trueAnswer;
    }

    public void setTrueAnswer(boolean trueAnswer) {
        this.trueAnswer = trueAnswer;
    }
}
