package com.blooddonation.model;


import javax.persistence.Id;
import java.util.UUID;

public class Answer {
    @Id
    private UUID id = UUID.randomUUID();

    private UUID questionnaireId;
    private String answer; // used for user input

    public Answer( UUID id, UUID questionnaireId, String answer) {
        this.id = id;
        this.questionnaireId = questionnaireId;
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
        return questionnaireId;
    }

    public void setQuestionnaireId(UUID questionnaireId) {
        this.questionnaireId = questionnaireId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}

