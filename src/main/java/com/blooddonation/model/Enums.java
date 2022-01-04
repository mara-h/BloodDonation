package com.blooddonation.model;

public class Enums {
    public enum AnswerType {
        bool, // yes/no question
        userInput
    }

    public enum Sex {
        male,
        female
    }

    public enum BloodGroups {
        Aplus,
        Aminus,
        Bplus,
        Bminus,
        ABplus,
        ABminus,
        Oplus,
        Ominus,
        UNKNOWN,
    }
}
