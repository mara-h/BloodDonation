package com.blooddonation.controller;

import com.blooddonation.model.Answer;
import com.blooddonation.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class AnswerController {
    @Autowired
    private AnswerService answerService;

    @GetMapping("/answers")
    public ResponseEntity<List<Answer>> getAllAnswers() {
        return answerService.getAllAnswers();
    }

    @GetMapping("/answers/{id}")
    public ResponseEntity<Answer> getAnswerById(@PathVariable UUID id) {
        return answerService.getAnswerById(id);
    }

    @PostMapping("/answers")
    public ResponseEntity<String> addAnswer(@RequestBody Answer answer) {
        return answerService.addAnswer(answer);
    }

    @PutMapping("/answers/{id}")
    public ResponseEntity<Answer> updateAnswer(@PathVariable UUID id, @RequestBody Answer answer) {
        return answerService.updateAnswer(id, answer);
    }

    @DeleteMapping("/answers")
    public ResponseEntity<String> deleteAllAnswers() {
        return answerService.deleteAllAnswers();
    }

    @DeleteMapping("/answers/{id}")
    public ResponseEntity<String> deleteAnswer(@PathVariable UUID id) {
        return answerService.deleteAnswer(id);
    }
}
