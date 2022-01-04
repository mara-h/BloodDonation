package com.blooddonation.model;


import javax.persistence.Id;
import java.util.UUID;

public class Answer {
    @Id
    private UUID id = UUID.randomUUID();

    private UUID QuestionnaireId;
    private String answer; // used for user input

    public Answer(UUID questionId, String answer) {
        this.QuestionnaireId = questionId;
        this.answer = answer;
    }

    public Answer(){

    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

