package com.blooddonation.service;

import com.blooddonation.model.Question;
import com.blooddonation.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class QuestionService {
    @Autowired
    private QuestionRepository questionRepository;

    public  ResponseEntity<List<Question>> getAllQuestions(){
        try {
            List<Question> questions = new ArrayList<Question>();
            questionRepository.findAll().forEach(questions::add);
            if (questions.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(questions, HttpStatus.OK);

        } catch (Exception e) {
            System.out.println("Error while getting all questions:" + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public ResponseEntity<Question> getQuestionById(UUID id) {
        Optional<Question> question = questionRepository.findById(id);
        if (question.isPresent()) {
            return new ResponseEntity<>(question.get(), HttpStatus.OK);
        } else {
            System.out.println("No question found");
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<String> addQuestion(Question question) {
        try {
            Question savedQuestion = questionRepository.save(new Question(question.getQuestionBody(), question.getAnswerType()));
            return new ResponseEntity<>("Question saved successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println("The question could not be added. Error:" + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Question> updateQuestion(UUID id, Question question) {
        Optional<Question> oldData = questionRepository.findById(id);
        if (oldData.isPresent()) {
            Question updatedQuestion = oldData.get();
            updatedQuestion.setQuestionBody(question.getQuestionBody());
            updatedQuestion.setAnswerType(question.getAnswerType());
            return new ResponseEntity<>(questionRepository.save(updatedQuestion), HttpStatus.OK);
        } else {
            System.out.println("No such question found");
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<String> deleteAllQuestions() {
        try {
            questionRepository.deleteAll();
            return new ResponseEntity<>("Questions successfully deleted", HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            System.out.println("Questions could not be deleted. Error: " + e.getMessage());
            return new ResponseEntity<>("Questions could not be deleted. Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> deleteQuestion(UUID id) {
        try {
            questionRepository.deleteById(id);
            return new ResponseEntity<>("Question " + id + " successfully deleted", HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            System.out.println("Question " + id + " could not be deleted. Error: " + e.getMessage());
            return new ResponseEntity<>("Question " + id + " could not be deleted. Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
