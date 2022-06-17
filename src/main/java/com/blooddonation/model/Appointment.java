package com.blooddonation.model;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class Appointment {
    @Id
    private UUID id = UUID.randomUUID();

    @org.springframework.data.annotation.Transient
    public static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private String dayOfAppointment = dateFormat.format(new Date());

    @Enumerated(EnumType.STRING)
    private Enums.Hours hourOfAppointment;

    private UUID questionnaireId;

    private UUID userId;

    public Appointment(UUID id, String dayOfAppointment, Enums.Hours hourOfAppointment, UUID questionnaire, UUID user) {
        this.id = id;
        this.dayOfAppointment = dayOfAppointment;
        this.hourOfAppointment = hourOfAppointment;
        this.questionnaireId = questionnaire;
        this.userId = user;
    }

    public Appointment() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getDayOfAppointment() {
        return dayOfAppointment;
    }

    public void setDayOfAppointment(String dayOfAppointment) {
        this.dayOfAppointment = dayOfAppointment;
    }

    public Enums.Hours getHourOfAppointment() {
        return hourOfAppointment;
    }

    public void setHourOfAppointment(Enums.Hours hourOfAppointment) {
        this.hourOfAppointment = hourOfAppointment;
    }

    public UUID getQuestionnaireId() {
        return questionnaireId;
    }

    public void setQuestionnaireId(UUID questionnaireId) {
        this.questionnaireId = questionnaireId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }
}
