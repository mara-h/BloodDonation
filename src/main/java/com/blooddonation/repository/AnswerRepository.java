package com.blooddonation.repository;

import com.blooddonation.model.Answer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AnswerRepository extends MongoRepository <Answer, UUID>{
}
