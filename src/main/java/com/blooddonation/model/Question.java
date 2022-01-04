package com.blooddonation.model;

import javax.persistence.*;
import java.util.UUID;

public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    private String questionBody;
    private int questionOrder;
    public enum AnswerType {
        bool, // yes/no question
        userInput
    }

    @Enumerated(EnumType.STRING)
    private AnswerType answerType;

    public enum GenderSpecificQuestion {
        male,
        female
    }

    @Enumerated(EnumType.STRING)
    private GenderSpecificQuestion genderSpecific;

    private boolean isGoodAnswerNo; //TODO: see how to verify answer for user input


    public Question() {
    }

    public Question(String questionBody, int questionOrder, AnswerType answerType, GenderSpecificQuestion genderSpecificQuestion, boolean isGoodAnswerNo) {
        this.questionBody = questionBody;
        this.questionOrder = questionOrder;
        this.answerType = answerType;
        this.genderSpecific = genderSpecificQuestion; // if null/empty -> used for both
        this.isGoodAnswerNo = isGoodAnswerNo;
    }

    public String getId() {
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

    public GenderSpecificQuestion getGenderSpecific() {
        return genderSpecific;
    }

    public void setGenderSpecific(GenderSpecificQuestion genderSpecific) {
        this.genderSpecific = genderSpecific;
    }

    public boolean isGoodAnswerNo() {
        return isGoodAnswerNo;
    }

    public void setGoodAnswerNo(boolean goodAnswerNo) {
        isGoodAnswerNo = goodAnswerNo;
    }

    public int getQuestionOrder() {
        return questionOrder;
    }

    public void setQuestionOrder(int questionOrder) {
        this.questionOrder = questionOrder;
    }
}
