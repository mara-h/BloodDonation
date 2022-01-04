package com.blooddonation.model;


import javax.persistence.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;


public class Questionnaire {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    @org.springframework.data.annotation.Transient
    public static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private String added_at = dateFormat.format(new Date());
    private UUID userId;
    private List<Answer> userInputAnswers = new ArrayList<>();
    private List<Answer> invalidateQuestionnaireAnswers = new ArrayList<>();

    public Questionnaire(String added_at, UUID userId, List<Answer> userInputAnswers, List<Answer> invalidateQuestionnaireAnswers) {
        this.added_at = added_at; // not sure if this needs to be in the constructor
        this.userId = userId;
        this.userInputAnswers = userInputAnswers;
        this.invalidateQuestionnaireAnswers = invalidateQuestionnaireAnswers;
    }

    public Questionnaire() {
    }

    public void setId(String id) {
        this.id = id;
    }

    public static DateFormat getDateFormat() {
        return dateFormat;
    }

    public static void setDateFormat(DateFormat dateFormat) {
        Questionnaire.dateFormat = dateFormat;
    }

    public String getAdded_at() {
        return added_at;
    }

    public void setAdded_at(String added_at) {
        this.added_at = added_at;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public List<Answer> getUserInputAnswers() {
        return userInputAnswers;
    }

    public void setUserInputAnswers(List<Answer> userInputAnswers) {
        this.userInputAnswers = userInputAnswers;
    }

    public List<Answer> getInvalidateQuestionnaireAnswers() {
        return invalidateQuestionnaireAnswers;
    }

    public void setInvalidateQuestionnaireAnswers(List<Answer> invalidateQuestionnaireAnswers) {
        this.invalidateQuestionnaireAnswers = invalidateQuestionnaireAnswers;
    }

    public String getId() {
        return id;
    }
}
