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
    private List<UUID> userInputAnswerIds;
    private Boolean valid; // if it's not valid, the user will not be able to do another questionnaire another 12 h at least

    public Questionnaire(UUID id, UUID userId,Boolean valid) {
        this.id = id;
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

    public List<UUID> getUserInputAnswerIds() {
        return userInputAnswerIds;
    }

    public void setUserInputAnswerIds(List<UUID> userInputAnswerIds) {
        this.userInputAnswerIds = userInputAnswerIds;
    }

    public UUID getId() {
        return id;
    }

    public Boolean isValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }
}
