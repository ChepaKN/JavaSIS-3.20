package com.github.siberianintegrationsystems.restApp.service;

import com.github.siberianintegrationsystems.restApp.controller.dto.AnswerItemDTO;
import com.github.siberianintegrationsystems.restApp.controller.dto.QuestionsItemDTO;
import com.github.siberianintegrationsystems.restApp.data.AnswerRepository;
import com.github.siberianintegrationsystems.restApp.data.QuestionRepository;
import com.github.siberianintegrationsystems.restApp.entity.Answer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class QuestionServiceImplTest {

    @Autowired
    private QuestionService questionService;
    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private QuestionRepository questionRepository;

    @Before
    public void init(){
        QuestionsItemDTO questionsItemDTO = new QuestionsItemDTO();
        questionsItemDTO.name = "Сколько ног у Паука";

        Answer answer1 = new Answer();
        answer1.setName("8");
        answer1.setCorrect(true);
        answerRepository.save(answer1);

        Answer answer2 = new Answer();
        answer2.setName("16");
        answer2.setCorrect(false);
        answerRepository.save(answer2);

        AnswerItemDTO answerItemDTO1 = new AnswerItemDTO(answer1);
        AnswerItemDTO answerItemDTO2 = new AnswerItemDTO(answer2);

        questionsItemDTO.answers = Arrays.asList(answerItemDTO1, answerItemDTO2);

        QuestionsItemDTO createdQuestion = questionService.createQuestion(questionsItemDTO);

        Answer answer3 = new Answer();
        answer3.setName("7");
        answer3.setCorrect(false);
        answerRepository.save(answer3);

        Answer answer4 = new Answer();
        answer4.setName("8");
        answer4.setCorrect(true);
        answerRepository.save(answer4);

        AnswerItemDTO answerItemDTO3 = new AnswerItemDTO(answer4);
        AnswerItemDTO answerItemDTO4 = new AnswerItemDTO(answer4);

        QuestionsItemDTO questionsItemDTO1 = new QuestionsItemDTO();
        questionsItemDTO1.name = "Сколько бит в байте";
        questionsItemDTO1.answers = Arrays.asList(answerItemDTO3, answerItemDTO4);

        QuestionsItemDTO createdQuestion1 = questionService.createQuestion(questionsItemDTO1);
    }
    @After
    public void clear(){
        questionRepository.deleteAll();
        answerRepository.deleteAll();
    }

    @Test
    public void createAndEditQuestionTest() {
        Answer answer1 = new Answer();
        answer1.setName("8");
        answer1.setCorrect(true);
        answerRepository.save(answer1);

        Answer answer2 = new Answer();
        answer2.setName("16");
        answer2.setCorrect(false);
        answerRepository.save(answer2);

        AnswerItemDTO answerItemDTO1 = new AnswerItemDTO(answer1);
        AnswerItemDTO answerItemDTO2 = new AnswerItemDTO(answer2);

        QuestionsItemDTO questionsItemDTO = new QuestionsItemDTO();
        questionsItemDTO.name = "Сколько ног у Паука";
        questionsItemDTO.answers = Arrays.asList(answerItemDTO1, answerItemDTO2);

        QuestionsItemDTO createdQuestion = questionService.createQuestion(questionsItemDTO);

        //заодно проверим работу с репозиториями
        assertTrue(answerRepository.existsById(answer1.getId()));
        assertTrue(answerRepository.existsById(answer2.getId()));
        assertTrue(questionRepository.existsById(Long.parseLong(createdQuestion.id)));

        int answersLenBeforeEdit = createdQuestion.answers.size();
        createdQuestion.name = createdQuestion.name + " some text";
        createdQuestion.answers.remove(0);
        QuestionsItemDTO editQuestion = questionService.editQuestion(createdQuestion);

        assertTrue(editQuestion.name.contains(" some text"));
        assertEquals(answersLenBeforeEdit - 1,
                editQuestion.answers.size());

    }


    @Test
    public void getQuestionsForSession() {
        List<QuestionsItemDTO> questionsItemDTOList = questionService.getQuestionsForSession();
        assertNotNull(questionsItemDTOList);

        //все ответы к вопросу должны быть false;
        for(QuestionsItemDTO q : questionsItemDTOList){
            long size = q.answers.stream().filter(a -> a.isCorrect).count();
            assertEquals(0, size);
        }
    }
}