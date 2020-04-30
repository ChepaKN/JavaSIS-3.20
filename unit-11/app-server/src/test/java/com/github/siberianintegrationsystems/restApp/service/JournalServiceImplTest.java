package com.github.siberianintegrationsystems.restApp.service;

import com.github.siberianintegrationsystems.restApp.controller.dto.journal.JournalItemDTO;
import com.github.siberianintegrationsystems.restApp.controller.dto.journal.JournalRequestDTO;
import com.github.siberianintegrationsystems.restApp.data.JournalRepository;
import com.github.siberianintegrationsystems.restApp.data.QuestionRepository;
import com.github.siberianintegrationsystems.restApp.entity.Journal;
import com.github.siberianintegrationsystems.restApp.entity.Question;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class JournalServiceImplTest {

    @Autowired
    private JournalRepository journalRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private JournalService journalService;

    private final String journalID = JournalServiceImpl.QUESTIONS_JOURNAL_ID;
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
    public void getJournalTest() {

        assertTrue(journalRepository.existsById(journalID));
        assertEquals(journalName, journalService.getJournal(journalID).getName());

    }

    @Test
    public void getJournalRowsTest() {

        final String questionName = "Вопрос 1";

        JournalRequestDTO journalRequestDTO = new JournalRequestDTO();
        journalRequestDTO.search = "";

        Question question = new Question();
        question.setName(questionName);
        questionRepository.save(question);

        List<? extends JournalItemDTO> rows
                = journalService.getJournalRows(journalID, journalRequestDTO);

        boolean founded = false;
        for (JournalItemDTO row : rows) {
            if (row.id.equals(String.valueOf(question.getId()))) {
                founded = true;
                break;
            }
        }
        assertTrue(founded);

        journalRequestDTO.search = questionName;
        rows = journalService.getJournalRows(journalID, journalRequestDTO);
        assertEquals(1, rows.size());
        assertEquals(rows.get(0).id,
                String.valueOf(question.getId()));
    }
}