package com.github.siberianintegrationsystems.restApp;

import com.github.siberianintegrationsystems.restApp.controller.JournalRestController;
import com.github.siberianintegrationsystems.restApp.controller.QuestionRestController;
import com.github.siberianintegrationsystems.restApp.controller.SessionRestController;
import com.github.siberianintegrationsystems.restApp.data.*;
import com.github.siberianintegrationsystems.restApp.service.JournalService;
import com.github.siberianintegrationsystems.restApp.service.QuestionService;
import com.github.siberianintegrationsystems.restApp.service.SessionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RestAppApplicationTests {
	@Autowired
	private JournalService journalService;
	@Autowired
	private QuestionService questionService;
	@Autowired
	private SessionService sessionService;
	@Autowired
	private JournalRestController journalRestController;
	@Autowired
	private QuestionRestController questionRestController;
	@Autowired
	private SessionRestController sessionRestController;
	@Autowired
	private AnswerRepository answerRepository;
	@Autowired
	private JournalRepository journalRepository;
	@Autowired
	private QuestionRepository questionRepository;
	@Autowired
	private SelectedAnswerRepository selectedAnswerRepository;
	@Autowired
	private SessionRepository sessionRepository;


	@Test
	public void contextLoads(){
		//Проверим что все зависимости подтянулись
		assertNotNull(journalService);
		assertNotNull(questionService);
		assertNotNull(sessionService);
		assertNotNull(journalRestController);
		assertNotNull(questionRestController);
		assertNotNull(sessionRestController);
		assertNotNull(answerRepository);
		assertNotNull(journalRepository);
		assertNotNull(questionRepository);
		assertNotNull(selectedAnswerRepository);
		assertNotNull(sessionRepository);
	}
}
