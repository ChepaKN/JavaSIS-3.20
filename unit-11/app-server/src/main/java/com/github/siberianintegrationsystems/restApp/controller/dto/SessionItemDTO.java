package com.github.siberianintegrationsystems.restApp.controller.dto;

import com.github.siberianintegrationsystems.restApp.controller.dto.journal.JournalItemDTO;
import com.github.siberianintegrationsystems.restApp.entity.SessionEvent;

import java.time.LocalDateTime;

public class SessionItemDTO extends JournalItemDTO {

    public String   name;
    public LocalDateTime insertDate;
    public Double   result;

    public SessionItemDTO(){

    }

    public SessionItemDTO(SessionEvent sessionEvent){
        this.id = sessionEvent.getId().toString();
        this.name = sessionEvent.getName();
        this.insertDate = sessionEvent.getSessionTime();
        this.result = sessionEvent.getPercent();
    }
}
