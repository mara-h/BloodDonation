package com.blooddonation.model;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;


// a questionnaire will not be valid if any answer is "wrong"
public class Questionnaire {

    @Id
    private UUID id = UUID.randomUUID();

    @org.springframework.data.annotation.Transient
    public static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private String added_at = dateFormat.format(new Date());
    private UUID userId;
    private List<Answer> userInputAnswers;
    private boolean valid; // if it's not valid, the user will not be able to do another questionnaire another 12 h at least

    public Questionnaire(UUID userId,boolean valid) {
        this.userId = userId;
        this.valid = valid;
    }

    public Questionnaire() {
    }

    public void setId(UUID id) {
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

    public UUID getId() {
        return id;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }
}
