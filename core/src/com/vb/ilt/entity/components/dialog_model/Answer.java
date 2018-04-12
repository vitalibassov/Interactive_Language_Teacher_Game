package com.vb.ilt.entity.components.dialog_model;

public class Answer {

    private String answer;
    private int destinationId;
    private int score;

    public Answer(String answer, int destinationId, int score) {
        this.answer = answer;
        this.destinationId = destinationId;
        this.score = score;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(int destinationId) {
        this.destinationId = destinationId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
