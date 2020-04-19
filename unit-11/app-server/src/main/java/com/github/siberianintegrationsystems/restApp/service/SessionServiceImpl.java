package com.github.siberianintegrationsystems.restApp.service;

import com.github.siberianintegrationsystems.restApp.controller.dto.session.AnswerSessionDTO;
import com.github.siberianintegrationsystems.restApp.controller.dto.session.QuestionSessionDTO;
import com.github.siberianintegrationsystems.restApp.controller.dto.session.SessionDTO;
import com.github.siberianintegrationsystems.restApp.data.SelectedAnswerRepository;
import com.github.siberianintegrationsystems.restApp.data.SessionEventRepository;
import com.github.siberianintegrationsystems.restApp.entity.SelectedAnswer;
import com.github.siberianintegrationsystems.restApp.entity.SessionEvent;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class SessionServiceImpl implements SessionService {

    private SessionEventRepository sessionEventRepository;
    private SelectedAnswerRepository selectedAnswerRepository;


    public SessionServiceImpl(SessionEventRepository sessionEventRepository,
                              SelectedAnswerRepository selectedAnswerRepository) {
        this.sessionEventRepository = sessionEventRepository;
        this.selectedAnswerRepository = selectedAnswerRepository;
    }

    @Override
    public String validateSession(SessionDTO sessionDTO){
        String validateResult = "99.99";

        SessionEvent sessionEvent = new SessionEvent(sessionDTO.name,
                Double.parseDouble(validateResult));

        //сохраним сессию
        sessionEventRepository.save(sessionEvent);

        //сохраним выбранные ответы
//        SelectedAnswer selectedAnswer = new SelectedAnswer();
//        selectedAnswer.setSessionEvent(sessionEvent);
//        selectedAnswer.setAnswer(sessionEvent.);

//        List<AnswerSessionDTO> answerSessionDTOList = sessionDTO.questionsList
//                .stream()
//                .map(questionSessionDTO -> questionSessionDTO.answersList
//                        .stream()
//                        .filter(answerSessionDTO -> answerSessionDTO.isSelected))
//                        .collect(Collectors.toList()));

        List<AnswerSessionDTO> answerSessionDTOS = new ArrayList<>();

for(QuestionSessionDTO q : sessionDTO.questionsList) {
    answerSessionDTOS.addAll(q
            .answersList.stream()
            .filter(answerSessionDTO -> answerSessionDTO.isSelected)
            .collect(Collectors.toList()));
}
        return validateResult;
    }

//    @Override
//    public void saveSelectedAnswers(SessionDTO sessionDTO){
//        SelectedAnswer selectedAnswer = new SelectedAnswer();
//        selectedAnswer.setSessionEvent();
//        sessionDTO.
//    }

    @Override
    public SessionEvent getSession(String id) {
        return null;
    }
}
