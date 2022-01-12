package com.blooddonation.controller;

import com.blooddonation.model.Question;
import com.blooddonation.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @GetMapping("/questions")
    public ResponseEntity<List<Question>> getAllQuestions() {
        return questionService.getAllQuestions();
    }

    @GetMapping("/questions/{id}")
    public ResponseEntity<Question> getQuestionById(@PathVariable UUID id) {
        return questionService.getQuestionById(id);
    }

    @PostMapping("/questions")
    public ResponseEntity<String> addQuestion(@RequestBody Question question) {
        return questionService.addQuestion(question);
    }

    @PutMapping("/questions/{id}")
    public ResponseEntity<Question> updateQuestion(@PathVariable UUID id, @RequestBody Question question) {
        return questionService.updateQuestion(id, question);
    }

    @DeleteMapping("/questions")
    public ResponseEntity<String> deleteAllQuestions() {
        return questionService.deleteAllQuestions();
    }

    @DeleteMapping("/questions/{id}")
    public ResponseEntity<String> deleteQuestion(@PathVariable UUID id) {
        return questionService.deleteQuestion(id);
    }
}
