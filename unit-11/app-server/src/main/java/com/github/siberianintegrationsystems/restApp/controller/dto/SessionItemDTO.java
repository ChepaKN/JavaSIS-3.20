package com.github.siberianintegrationsystems.restApp.controller.dto;

import java.util.List;

public class SessionItemDTO extends JournalItemDTO {

    //ФИО
    public String name;
    //Список вопросов с выбранными(!) ответами
    public List<QuestionsItemDTO> questionsItemDTOList;

    public SessionItemDTO(String name, List<QuestionsItemDTO> questionsItemDTOList) {
        this.name = name;
        this.questionsItemDTOList = questionsItemDTOList;
    }


}
