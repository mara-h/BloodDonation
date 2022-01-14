package com.blooddonation.service;

import com.blooddonation.model.Answer;
import com.blooddonation.model.Questionnaire;
import com.blooddonation.model.User;
import com.blooddonation.repository.AnswerRepository;
import com.blooddonation.repository.QuestionnaireRepository;
import com.blooddonation.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class QuestionnaireService {
    @Autowired
    private QuestionnaireRepository questionnaireRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AnswerRepository answerRepository;

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

    public ResponseEntity<Questionnaire> addQuestionnaire(Questionnaire questionnaire) {
        try {
            UUID id = UUID.randomUUID();
            ResponseEntity response = this.addQuestionnaireToUser(questionnaire.getUserId(), id);
            if (response.getStatusCode().isError()) {
                // TODO: if adding questionnaire fails, what happens?)
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            } else {
                Questionnaire savedQuestionnaire = questionnaireRepository.save(new Questionnaire(id, questionnaire.getUserId(), questionnaire.isValid()));
                return new ResponseEntity<>(savedQuestionnaire, HttpStatus.CREATED);
            }

        } catch (Exception e) {
            System.out.println("The questionnaire could not be added. Error:" + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private ResponseEntity<User> addQuestionnaireToUser(UUID userId, UUID id) {
        if (userId == null) {
            System.out.println("User ID is null");
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        Optional<User> oldUserData = userRepository.findById(userId);
        if (oldUserData.isPresent()) {
            User updatedUser = oldUserData.get();
            if (updatedUser.getQuestionnairesIds() == null) {
                List<UUID> list = new ArrayList<>();
                list.add(id);
                updatedUser.setQuestionnairesIds(list);
            } else {
                List<UUID> list = updatedUser.getQuestionnairesIds();
                list.add(id);
                updatedUser.setQuestionnairesIds(list);
            }
            return new ResponseEntity<>(userRepository.save(updatedUser), HttpStatus.OK);
        } else {
            System.out.println("No such user found");
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Questionnaire> updateQuestionnaire(UUID id, Questionnaire questionnaire) {
        Optional<Questionnaire> oldData = questionnaireRepository.findById(id);
        if (oldData.isPresent()) {
            Questionnaire updatedQuestionnaire = oldData.get();
            updatedQuestionnaire.setUserId(questionnaire.getUserId());
            updatedQuestionnaire.setValid(questionnaire.isValid());
            return new ResponseEntity<>(questionnaireRepository.save(updatedQuestionnaire), HttpStatus.OK);
        } else {
            System.out.println("No such questionnaire found");
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<String> deleteAllQuestionnaires() {
        try {
            questionnaireRepository.deleteAll();
            answerRepository.deleteAll();// cascade delete answers

            List<User> users = new ArrayList<User>();

            userRepository.findAll().forEach(users::add); // remove questionnaires from every user
            for (User user : users) {
                user.setQuestionnairesIds(null);
            }

            return new ResponseEntity<>("Questionnaires successfully deleted", HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            System.out.println("Questionnaires could not be deleted. Error: " + e.getMessage());
            return new ResponseEntity<>("Questionnaires could not be deleted. Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> deleteQuestionnaire(UUID id) {
        try {
            UUID userId = null;
            Optional<Questionnaire> questionnaire = questionnaireRepository.findById(id);
            if (questionnaire.isPresent()) {
                Questionnaire questionnaireData = questionnaire.get();
                userId = questionnaireData.getUserId();
            } else {
                return new ResponseEntity<>("Questionnaire does not exist", HttpStatus.NOT_FOUND);
            }
            questionnaireRepository.deleteById(id);

            if (userId == null) {
                return new ResponseEntity<>("Error deleting questionnaire from user: user ID null", HttpStatus.INTERNAL_SERVER_ERROR);
            } else {
                ResponseEntity response = this.removeQuestionnaireFromUser(userId);
                if (response.getStatusCode().isError()) {
                    System.out.println("error:" + response.getBody());
                    return new ResponseEntity<>("Error deleting questionnaire from user", HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }

            ResponseEntity responseEntity = this.deleteQuestionnaireAnswers(id); // cascade delete answers
            if (responseEntity.getStatusCode().isError()) {
                return new ResponseEntity<>("Error cascade-deleting answers", HttpStatus.INTERNAL_SERVER_ERROR);
            }

            return new ResponseEntity<>("Questionnaire " + id + " successfully deleted", HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            System.out.println("Questionnaire " + id + " could not be deleted. Error: " + e.getMessage());
            return new ResponseEntity<>("Questionnaire " + id + " could not be deleted. Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private ResponseEntity<String> deleteQuestionnaireAnswers(UUID questionnaireId) {
        try {
            List<Answer> questionnaireAnswers = answerRepository.findAllByQuestionnaireId(questionnaireId);
            for (Answer questionnaireAnswer : questionnaireAnswers) {
                questionnaireRepository.deleteById(questionnaireAnswer.getId());
            }
            return new ResponseEntity<>("Questionnaire " + questionnaireId + " answers successfully deleted", HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            System.out.println("Questionnaire answers could not be deleted: " + e.getMessage());
            return new ResponseEntity<>("Questionnaire " + questionnaireId + "answers could not be deleted: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private ResponseEntity<String> removeQuestionnaireFromUser(UUID userId) {
        try {
            List<Questionnaire> userQuestionnaires = questionnaireRepository.findAllByUserId(userId);
            List<UUID> questionnaireIds = userQuestionnaires.stream().map(Questionnaire::getId).collect(Collectors.toList());
            Optional<User> oldUserData = userRepository.findById(userId);
            if (oldUserData.isPresent()) {
                User updatedUser = oldUserData.get();
                updatedUser.setQuestionnairesIds(questionnaireIds);
                userRepository.save(updatedUser);
            } else {
                System.out.println("No such user found - remove questionnaire from user");
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>("Removed questionnaire from user", HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Questionnaire could not be removed: " + e.getMessage());
            return new ResponseEntity<>("Questionnaire could not be removed: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
