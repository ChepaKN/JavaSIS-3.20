package com.github.siberianintegrationsystems.restApp.controller;


import com.github.siberianintegrationsystems.restApp.controller.dto.QuestionsItemDTO;
import com.github.siberianintegrationsystems.restApp.controller.dto.session.SessionDTO;
import com.github.siberianintegrationsystems.restApp.data.AnswerRepository;
import com.github.siberianintegrationsystems.restApp.data.QuestionRepository;
import com.github.siberianintegrationsystems.restApp.entity.Answer;
import com.github.siberianintegrationsystems.restApp.entity.Question;
import com.github.siberianintegrationsystems.restApp.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api")
public class SessionRestController {

//    public SessionRestController(QuestionRepository questionRepository,
//                                 AnswerRepository answerRepository,
//                                 SessionEventRepository sessionEventRepository) {
//
//        this.questionRepository = questionRepository;
//        this.answerRepository = answerRepository;
//    }

    public SessionRestController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    private SessionService sessionService;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;



    @GetMapping("session/questions-new")
    public List<QuestionsItemDTO> getQuestionsForSession(){


        //todo: перенести логику в сервис
        return questionRepository.findRandQuestions()
                .stream()
                .map(question -> {
                    //перемешаем ответы
                    // todo: (как то не очень, переделать бы...)
                    List<Answer> answers = answerRepository.findByQuestion(question);
                    Collections.shuffle(answers);
                    //Сотрем правильные ответы
                    answers.forEach(answer -> answer.setCorrect(false));
                    return new QuestionsItemDTO(question,
                            answers);
                    }
                )
                .collect(Collectors.toList());
    }

    @PostMapping("session")
    String saveSession(@RequestBody SessionDTO sessionDTO){

        return sessionService.validateSession(sessionDTO);

    }



}
