package com.github.siberianintegrationsystems.restApp.service;

import com.github.siberianintegrationsystems.restApp.controller.dto.session.AnswerSessionDTO;
import com.github.siberianintegrationsystems.restApp.controller.dto.session.QuestionSessionDTO;
import com.github.siberianintegrationsystems.restApp.controller.dto.session.SessionDTO;
import com.github.siberianintegrationsystems.restApp.data.AnswerRepository;
import com.github.siberianintegrationsystems.restApp.data.QuestionRepository;
import com.github.siberianintegrationsystems.restApp.data.SelectedAnswerRepository;
import com.github.siberianintegrationsystems.restApp.data.SessionEventRepository;
import com.github.siberianintegrationsystems.restApp.entity.Answer;
import com.github.siberianintegrationsystems.restApp.entity.Question;
import com.github.siberianintegrationsystems.restApp.entity.SelectedAnswer;
import com.github.siberianintegrationsystems.restApp.entity.SessionEvent;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
@Transactional
public class SessionServiceImpl implements SessionService {

    private SessionEventRepository sessionEventRepository;
    private QuestionRepository questionRepository;
    private AnswerRepository answerRepository;
    private SelectedAnswerRepository selectedAnswerRepository;


    public SessionServiceImpl(SessionEventRepository sessionEventRepository,
                              QuestionRepository questionRepository,
                              AnswerRepository answerRepository,
                              SelectedAnswerRepository selectedAnswerRepository) {
        this.sessionEventRepository = sessionEventRepository;
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
        this.selectedAnswerRepository = selectedAnswerRepository;
    }



    private double validateOneQuestion(QuestionSessionDTO questionSessionDTO){
        //Результат в диапазоне [0:1]
        Question question = questionRepository
                .findById(Long.parseLong(questionSessionDTO.id))
                .orElseThrow(() ->
                        new RuntimeException(String.format("Не найдем вопрос с id%s",
                                questionSessionDTO.id)));

        //всего ответов
        double n = questionSessionDTO.answersList.size();
        //всего верных для этого вопроса
        double m = answerRepository.findByQuestion(question)
                .stream()
                .filter(Answer::getCorrect)
                .count();

        if(m == 0) {
            throw new RuntimeException(
                    String.format("Не найден правильный ответ для вопроса с id: %d",
                            question.getId()));
        }else if(n == m){
            throw new RuntimeException(
                    String.format("Все ответы на вопрос с id: %d верные",
                            question.getId()));
        }

        //верно выбранных
        double k = 0;
        //неверно выбранных
        double w = 0;
        List<AnswerSessionDTO> selectedAnswers = questionSessionDTO.answersList;

        for(AnswerSessionDTO selectedAns : selectedAnswers){
            Answer savedAns = answerRepository.findById(Long.parseLong(selectedAns.id))
                    .orElseThrow(() -> new RuntimeException(
                            String.format("Не найден ответ с id: %s", selectedAns.id)));

            if(selectedAns.isSelected){
                if(savedAns.getCorrect()){
                    k++;
                }else{
                    w++;
                }
            }
            //todo: как быть с невыбранными верными ответами?
        }
        return Math.max(0, k/m - w/(n-m));
    }

    @Override
    public String validateSession(SessionDTO sessionDTO){

        Double result = sessionDTO.questionsList.stream()
                .map(this::validateOneQuestion).reduce(0.0, Double::sum);

        result = result / sessionDTO.questionsList.size() * 100.0;

        //сохраним сессию
        SessionEvent sessionEvent = new SessionEvent(sessionDTO.name, result);
        sessionEventRepository.save(sessionEvent);

        //сохраним выбранные ответы
        List<Answer> answers = new ArrayList<>();
        for(QuestionSessionDTO q : sessionDTO.questionsList){
            answers.addAll(q.answersList.stream()
                    .filter(answerSessionDTO -> answerSessionDTO.isSelected)
                    .map(answerSessionDTO -> answerRepository.findById(Long.parseLong(answerSessionDTO.id))
                            .orElseThrow(RuntimeException::new))
                    .collect(Collectors.toList()));
        }

        for (Answer a : answers){
            selectedAnswerRepository.save(new SelectedAnswer(a, sessionEvent));
        }

        //Locale.US для того чтобы при преобразовании в строку разделитель был точкой а не запятой, иначе клиент ругается
        return String.format(Locale.US, "%.2f", result);
    }
}
