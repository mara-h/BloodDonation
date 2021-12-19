package com.blooddonation.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Entity
@Table(name = "questionnaire")
@SecondaryTable(name = "questions")
public class Questionnaire {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @OneToMany
    @JoinColumn(name = "fk_questionnaire")
    private List<Question> questions = new ArrayList<Question>();

    public Questionnaire() {
    }

    public UUID getId() {
        return id;
    }
}
