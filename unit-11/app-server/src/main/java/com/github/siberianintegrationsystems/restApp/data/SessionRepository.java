package com.github.siberianintegrationsystems.restApp.data;

import com.github.siberianintegrationsystems.restApp.entity.Question;
import com.github.siberianintegrationsystems.restApp.entity.QuestionSession;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SessionRepository
        extends CrudRepository<QuestionSession, String> {

    List<QuestionSession> findByNameContainingIgnoreCase(String search);
}
