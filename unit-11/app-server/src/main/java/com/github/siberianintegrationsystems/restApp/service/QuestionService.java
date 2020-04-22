package com.github.siberianintegrationsystems.restApp.service;

import com.github.siberianintegrationsystems.restApp.controller.dto.QuestionsItemDTO;

import java.util.List;

public interface QuestionService {
    QuestionsItemDTO createQuestion(QuestionsItemDTO dto);
    QuestionsItemDTO editQuestion(QuestionsItemDTO dto);
    List<QuestionsItemDTO> getQuestionsForSession();
}
