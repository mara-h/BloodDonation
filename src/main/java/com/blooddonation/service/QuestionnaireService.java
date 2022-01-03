package com.blooddonation.service;

import com.blooddonation.model.Question;
import com.blooddonation.model.Questionnaire;
import com.blooddonation.repository.QuestionnaireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class QuestionnaireService {
    @Autowired
    private QuestionnaireRepository questionnaireRepository;

    public ResponseEntity<List<Questionnaire>> getAllQuestionnaires() {
        try {
            List<Questionnaire> questionnaires = new ArrayList<Questionnaire>();
            questionnaireRepository.findAll().forEach(questionnaires::add);
            if (questionnaires.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(questionnaires, HttpStatus.OK);

        } catch (Exception e) {
            System.out.println("Error while getting all questionnaires:" + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Questionnaire> getQuestionnaireById(UUID id) {
        Optional<Questionnaire> questionnaire = questionnaireRepository.findById(id);
        if (questionnaire.isPresent()) {
            return new ResponseEntity<>(questionnaire.get(), HttpStatus.OK);
        } else {
            System.out.println("No questionnaire found");
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<String> addQuestionnaire(Questionnaire questionnaire) {
        try {
            Questionnaire savedQuestionnaire = questionnaireRepository.save(new Questionnaire(questionnaire.get));
            return new ResponseEntity<>("Questionnaire saved successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println("The questionnaire could not be added. Error:" + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Questionnaire> updateQuestionnaire(UUID id, Questionnaire questionnaire) {
        Optional<Questionnaire> oldData = questionnaireRepository.findById(id);
        if (oldData.isPresent()) {
            Questionnaire updatedQuestionnaire = oldData.get();

//            TODO update data somehow
            return new ResponseEntity<>(questionnaireRepository.save(updatedQuestionnaire), HttpStatus.OK);
        } else {
            System.out.println("No such questionnaire found");
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<String> deleteAllQuestionnaires() {
        try {
            questionnaireRepository.deleteAll();
            return new ResponseEntity<>("Questionnaires successfully deleted", HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            System.out.println("Questionnaires could not be deleted. Error: " + e.getMessage());
            return new ResponseEntity<>("Questionnaires could not be deleted. Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> deleteQuestionnaire(UUID id) {
        try {
            questionnaireRepository.deleteById(id);
            return new ResponseEntity<>("Questionnaire " + id + " successfully deleted", HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            System.out.println("Questionnaire " + id + " could not be deleted. Error: " + e.getMessage());
            return new ResponseEntity<>("Questionnaire " + id + " could not be deleted. Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
