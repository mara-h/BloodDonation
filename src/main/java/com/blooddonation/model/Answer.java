package com.blooddonation.model;


import java.util.UUID;

public class Answer {

    private UUID QuestionnaireId;
    private String answer; // used for user input

    public Answer(UUID questionId, String answer) {
        this.QuestionnaireId = questionId;
        this.answer = answer;
    }

    public Answer(){

    }

    public UUID getQuestionnaireId() {
        return QuestionnaireId;
    }

    public void setQuestionnaireId(UUID questionnaireId) {
        QuestionnaireId = questionnaireId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}

