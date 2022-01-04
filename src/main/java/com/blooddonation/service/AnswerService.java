package com.blooddonation.service;

import com.blooddonation.model.Answer;
import com.blooddonation.repository.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AnswerService {
    @Autowired
    private AnswerRepository answerRepository;

    public ResponseEntity<List<Answer>> getAllAnswers() {
        try {
            List<Answer> answers = new ArrayList<>();
            answerRepository.findAll().forEach(answers::add);
            if (answers.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(answers, HttpStatus.OK);

        } catch (Exception e) {
            System.out.println("Error while getting all answers:" + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Answer> getAnswerById(UUID id) {
        Optional<Answer> answer = answerRepository.findById(id);
        if (answer.isPresent()) {
            return new ResponseEntity<>(answer.get(), HttpStatus.OK);
        } else {
            System.out.println("No answer found");
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<String> addAnswer(Answer answer) {
        try {
            Answer savedAnswer = answerRepository.save(new Answer(answer.getQuestionnaireId(), answer.getAnswer()));
            return new ResponseEntity<>("Answer saved successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println("The answer could not be added. Error:" + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Answer> updateAnswer(UUID id, Answer answer) {
        Optional<Answer> oldData = answerRepository.findById(id);
        if (oldData.isPresent()) {
            Answer updatedAnswer = oldData.get();
            updatedAnswer.setAnswer(answer.getAnswer());
            updatedAnswer.setQuestionnaireId(answer.getQuestionnaireId());
            return new ResponseEntity<>(answerRepository.save(updatedAnswer), HttpStatus.OK);
        } else {
            System.out.println("No such answer found");
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<String> deleteAllAnswers() {
        try {
            answerRepository.deleteAll();
            return new ResponseEntity<>("Answers successfully deleted", HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            System.out.println("Answers could not be deleted. Error: " + e.getMessage());
            return new ResponseEntity<>("Answers could not be deleted. Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> deleteAnswer(UUID id) {
        try {
            answerRepository.deleteById(id);
            return new ResponseEntity<>("Answer " + id + " successfully deleted", HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            System.out.println("Answer " + id + " could not be deleted. Error: " + e.getMessage());
            return new ResponseEntity<>("Answer " + id + " could not be deleted. Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
