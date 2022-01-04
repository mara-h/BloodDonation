package com.blooddonation.repository;

import com.blooddonation.model.Question;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface QuestionRepository extends MongoRepository<Question, UUID> {
}

