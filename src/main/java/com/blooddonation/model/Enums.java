package com.blooddonation.model;

public class Enums {
    public enum AnswerType {
        bool, // yes/no question
        userInputAlcohol,
        userInputAlcoholQuantity,
        getUserInputAlcoholTimeSince
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

    public enum Hours {
        h0830,
        h0900,
        h0930,
        h1000,
        h1030,
        h1100,
        h1130,
        h1200,
        h1230,
        h1300
    }
}
