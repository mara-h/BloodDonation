package com.blooddonation.model;

import com.sun.istack.NotNull;

import java.util.UUID;

public class Answer {

    @NotNull
    private UUID questionId;
    private String answer; // used for user input

    public Answer(UUID questionId, String answer) {
        this.questionId = questionId;
        this.answer = answer;
    }

    public Answer(){

    }

    public UUID getQuestionId() {
        return questionId;
    }

    public void setQuestionId(UUID questionId) {
        this.questionId = questionId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}

