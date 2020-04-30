package com.github.siberianintegrationsystems.restApp.data;

import com.github.siberianintegrationsystems.restApp.entity.SessionEvent;
import org.springframework.data.repository.CrudRepository;


public interface SessionRepository
        extends CrudRepository<SessionEvent, String> {

}
