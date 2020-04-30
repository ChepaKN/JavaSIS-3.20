package com.github.siberianintegrationsystems.restApp.controller;


import com.github.siberianintegrationsystems.restApp.controller.dto.QuestionsItemDTO;
import com.github.siberianintegrationsystems.restApp.controller.dto.session.SessionDTO;
import com.github.siberianintegrationsystems.restApp.service.QuestionService;
import com.github.siberianintegrationsystems.restApp.service.SessionService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/session")
public class SessionRestController {

    public SessionRestController(SessionService sessionService,
                                 QuestionService questionService) {
        this.sessionService = sessionService;
        this.questionService = questionService;
    }

    private SessionService sessionService;
    private QuestionService questionService;

    @GetMapping("questions-new")
    public List<QuestionsItemDTO> getQuestionsForSession(){
        return questionService.getQuestionsForSession();
    }

    //Post запрос по URL "api/session" прилетит сюда
    @PostMapping
    String saveSession(@RequestBody SessionDTO sessionDTO){
        return sessionService.validateSession(sessionDTO);
    }



}
