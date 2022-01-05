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
        h0840,
        h0850,
        h0900,
        h0910,
        h0920,
        h0930,
        h0940,
        h0950,
        h1000,
        h1010,
        h1020,
        h1030,
        h1040,
        h1050,
        h1100,
        h1110,
        h1120,
        h1130,
        h1140,
        h1150,
        h1200,
        h1210,
        h1220,
        h1230,
        h1240,
        h1250,
        h1300
    }
}
