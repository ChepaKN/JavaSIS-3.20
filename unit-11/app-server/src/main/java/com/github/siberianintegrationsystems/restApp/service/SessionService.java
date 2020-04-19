package com.github.siberianintegrationsystems.restApp.service;

import com.github.siberianintegrationsystems.restApp.controller.dto.session.SessionDTO;
import com.github.siberianintegrationsystems.restApp.entity.SessionEvent;

public interface SessionService {
    SessionEvent getSession(String id);
    String validateSession(SessionDTO sessionDTO);
//    void saveSelectedAnswers(SessionDTO sessionDTO);
}
