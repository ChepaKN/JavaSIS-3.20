package com.github.siberianintegrationsystems.restApp.data;

import com.github.siberianintegrationsystems.restApp.entity.Question;
import com.github.siberianintegrationsystems.restApp.entity.SessionEvent;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SessionEventRepository
        extends CrudRepository<SessionEvent, String> {

    List<SessionEvent> findByNameContainingIgnoreCase(String search);
}
