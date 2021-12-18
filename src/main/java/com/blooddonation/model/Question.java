package com.blooddonation.model;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "questions")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotNull
    @Column(name = "questionBody")
    private String questionBody;

    public enum AnswerType {
        bool, // yes/no question
        userInput
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "answerType")
    private AnswerType answerType;

    public Question() {
    }

    public Question(String questionBody, AnswerType answerType) {
        this.questionBody = questionBody;
        this.answerType = answerType;
    }

    public UUID getId() {
        return id;
    }

    public AnswerType getAnswerType() {
        return answerType;
    }

    public void setAnswerType(AnswerType answerType) {
        this.answerType = answerType;
    }

    public String getQuestionBody() {
        return questionBody;
    }

    public void setQuestionBody(String questionBody) {
        this.questionBody = questionBody;
    }
}
