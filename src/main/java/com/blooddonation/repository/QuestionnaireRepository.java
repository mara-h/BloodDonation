package com.blooddonation.repository;

import com.blooddonation.model.Questionnaire;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface QuestionnaireRepository extends MongoRepository<Questionnaire, UUID> {
    List<Questionnaire> findAllByUserId(UUID userId);
}
