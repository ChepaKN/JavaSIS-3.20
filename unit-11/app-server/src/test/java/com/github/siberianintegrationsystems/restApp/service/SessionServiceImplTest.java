package com.github.siberianintegrationsystems.restApp.service;

import com.github.siberianintegrationsystems.restApp.controller.dto.session.AnswerSessionDTO;
import com.github.siberianintegrationsystems.restApp.controller.dto.session.QuestionSessionDTO;
import com.github.siberianintegrationsystems.restApp.controller.dto.session.SessionDTO;
import com.github.siberianintegrationsystems.restApp.data.AnswerRepository;
import com.github.siberianintegrationsystems.restApp.data.QuestionRepository;
import com.github.siberianintegrationsystems.restApp.entity.Answer;
import com.github.siberianintegrationsystems.restApp.entity.Question;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import java.util.Arrays;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class SessionServiceImplTest {

    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private SessionService sessionService;

    @Test
    public void validateOneQuestionTest() {

        Question question = new Question();
        question.setName("Сколько колес у авто?");
        questionRepository.save(question);

        Answer answer = new Answer();
        answer.setName("4");
        answer.setCorrect(true);
        answer.setQuestion(question);
        answerRepository.save(answer);

        Answer answer1 = new Answer();
        answer1.setName("5");
        answer1.setCorrect(false);
        answer1.setQuestion(question);
        answerRepository.save(answer1);

        AnswerSessionDTO sessionAnswer = new AnswerSessionDTO();
        sessionAnswer.id = String.valueOf(answer.getId());
        sessionAnswer.isSelected = true;

        AnswerSessionDTO sessionAnswer1 = new AnswerSessionDTO();
        sessionAnswer1.id = String.valueOf(answer1.getId());
        sessionAnswer1.isSelected = false;

        Question question1= new Question();
        question1.setName("Выберите отечественные авто?");
        questionRepository.save(question1);

        Answer answer2 = new Answer();
        answer2.setName("Ваз");
        answer2.setCorrect(true);
        answer2.setQuestion(question1);
        answerRepository.save(answer2);

        Answer answer3= new Answer();
        answer3.setName("Газ");
        answer3.setCorrect(true);
        answer3.setQuestion(question1);
        answerRepository.save(answer3);

        Answer answer4= new Answer();
        answer4.setName("Бентли");
        answer4.setCorrect(false);
        answer4.setQuestion(question1);
        answerRepository.save(answer4);

        AnswerSessionDTO sessionAnswer2 = new AnswerSessionDTO();
        sessionAnswer2.id = String.valueOf(answer2.getId());
        sessionAnswer2.isSelected = true;

        AnswerSessionDTO sessionAnswer3 = new AnswerSessionDTO();
        sessionAnswer3.id = String.valueOf(answer3.getId());
        sessionAnswer3.isSelected = false;

        AnswerSessionDTO sessionAnswer4 = new AnswerSessionDTO();
        sessionAnswer4.id = String.valueOf(answer4.getId());
        sessionAnswer4.isSelected = false;

        QuestionSessionDTO questionSessionDTO = new QuestionSessionDTO();
        questionSessionDTO.answersList = Arrays.asList(sessionAnswer, sessionAnswer1);
        questionSessionDTO.id = String.valueOf(question.getId());

        QuestionSessionDTO questionSessionDTO1 = new QuestionSessionDTO();
        questionSessionDTO1.answersList = Arrays.asList(sessionAnswer2, sessionAnswer3, sessionAnswer4);
        questionSessionDTO1.id = String.valueOf(question1.getId());

        SessionDTO sessionDTO = new SessionDTO();
        sessionDTO.name = "Чепа К.Н.";
        sessionDTO.questionsList = Arrays.asList(questionSessionDTO, questionSessionDTO1);
        String result = sessionService.validateSession(sessionDTO);

        assertEquals("75.00", result);

        sessionDTO.questionsList.forEach(
                q -> q.answersList.forEach(
                        a -> a.isSelected = false));

        result = sessionService.validateSession(sessionDTO);
        assertEquals("0.00", result);

    }
}