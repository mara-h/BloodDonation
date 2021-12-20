package com.blooddonation.model;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "questions")
public class Question {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotNull
    @Column(name = "questionBody")
    private String questionBody;

    @NotNull
    @Column(name = "questionOrder")
    private int questionOrder;

    public enum AnswerType {
        bool, // yes/no question
        userInput
    }

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "answerType")
    private AnswerType answerType;


    public enum GenderSpecificQuestion {
        male,
        female
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "genderSpecific") // if null then it is user for both genders
    private GenderSpecificQuestion genderSpecific;

    @Column(name = "isGoodAnswerNo")
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
