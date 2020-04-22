package com.github.siberianintegrationsystems.restApp.service;

import com.github.siberianintegrationsystems.restApp.data.JournalRepository;
import com.github.siberianintegrationsystems.restApp.entity.Journal;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.catchThrowable;
import static com.shazam.shazamcrest.matcher.Matchers.*;
import static org.junit.Assert.*;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class JournalServiceImplTest {

    @Autowired
    private JournalRepository journalRepository;

    @Autowired
    private JournalService journalService;

    private final String journalID = "Dunno";
    private final String journalName = "Незнайка и Java";

    @Before
    public void init(){
        Journal journal = new Journal();
        journal.setId(journalID);
        journal.setName(journalName);
        journal.setDefaultPageSize(15L);
        journalRepository.save(journal);
    }

    @Test
    public void getJournal() {

        assertTrue(journalRepository.existsById(journalID));
        assertEquals(journalName, journalService.getJournal(journalID).getName());

    }

    @Test
    public void getJournalRows() {
    }
}