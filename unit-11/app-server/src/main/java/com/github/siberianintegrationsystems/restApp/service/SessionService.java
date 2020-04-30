package com.github.siberianintegrationsystems.restApp.service;

import com.github.siberianintegrationsystems.restApp.controller.dto.session.SessionDTO;

public interface SessionService {
    String validateSession(SessionDTO sessionDTO);
}
