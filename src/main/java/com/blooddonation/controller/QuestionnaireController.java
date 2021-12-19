package com.blooddonation.controller;

import com.blooddonation.model.Questionnaire;
import com.blooddonation.service.QuestionnaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
public class QuestionnaireController {
    @Autowired
    private QuestionnaireService questionnaireService;
    @GetMapping("/questionnaires")
    public ResponseEntity<List<Questionnaire>> getAllQuestionnaires() {
        return questionnaireService.getAllQuestionnaires();
    }

    @GetMapping("/questionnaires/{id}")
    public ResponseEntity<Questionnaire> getQuestionnaireById(@PathVariable UUID id) {
        return questionnaireService.getQuestionnaireById(id);
    }

    @PostMapping("/questionnaires")
    public ResponseEntity<String> addQuestionnaire() {
        return questionnaireService.addQuestionnaire();
    }

    @PutMapping("/questionnaires/{id}")
    public ResponseEntity<Questionnaire> updateQuestionnaire(@PathVariable UUID id, @RequestBody Questionnaire questionnaire) {
        return questionnaireService.updateQuestionnaire(id, questionnaire);
    }

    @DeleteMapping("/questionnaires")
    public ResponseEntity<String> deleteAllQuestionnaires() {
        return questionnaireService.deleteAllQuestionnaires();
    }

    @DeleteMapping("/questionnaires/{id}")
    public ResponseEntity<String> deleteQuestionnaire(@PathVariable UUID id) {
        return questionnaireService.deleteQuestionnaire(id);
    }
}
