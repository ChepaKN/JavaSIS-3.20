package com.github.siberianintegrationsystems.restApp.service;

import com.github.siberianintegrationsystems.restApp.controller.dto.journal.JournalItemDTO;
import com.github.siberianintegrationsystems.restApp.controller.dto.journal.JournalRequestDTO;
import com.github.siberianintegrationsystems.restApp.entity.Journal;

import java.util.List;

public interface JournalService {
    Journal getJournal(String id);

    List<? extends JournalItemDTO> getJournalRows(String id, JournalRequestDTO req);
}
