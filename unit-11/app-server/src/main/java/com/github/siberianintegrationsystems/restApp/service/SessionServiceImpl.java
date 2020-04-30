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



    //Метод возвращает результат валидирования одного вопроса в диапазоне [0:1]
    private double validateOneQuestion(QuestionSessionDTO questionSessionDTO){
        Question question = questionRepository
                .findById(Long.parseLong(questionSessionDTO.id))
                .orElseThrow(() ->
                        new RuntimeException(String.format("Не найден вопрос с id: %s",
                                questionSessionDTO.id)));

        //всего ответов
        double answersCount = questionSessionDTO.answersList.size();
        //всего верных для этого вопроса
        double rightAnswersCount = answerRepository.findByQuestion(question)
                .stream()
                .filter(Answer::getCorrect)
                .count();

        //верно выбранных
        double rightSelectedCount = 0;
        //неверно выбранных
        double wrongSelectedCount = 0;
        List<AnswerSessionDTO> selectedAnswers = questionSessionDTO.answersList;

        for(AnswerSessionDTO selectedAns : selectedAnswers){
            Answer savedAns = answerRepository.findById(Long.parseLong(selectedAns.id))
                    .orElseThrow(() -> new RuntimeException(
                            String.format("Не найден ответ с id: %s", selectedAns.id)));

            if(selectedAns.isSelected){
                if(savedAns.getCorrect()){
                    rightSelectedCount++;
                }else{
                    wrongSelectedCount++;
                }
            }
        }

        double result;
        //Для избежания деления на 0, если вдруг все ответы верные
        if(answersCount == rightAnswersCount){
            result = Math.max(0, rightSelectedCount/rightAnswersCount - wrongSelectedCount);
        }else{
            result=  Math.max(0, rightSelectedCount/rightAnswersCount - wrongSelectedCount/(answersCount-rightAnswersCount));
        }
        return result;
    }

    @Override
    public String validateSession(SessionDTO sessionDTO){

        //Вдруг с клиента прилетела пустая сессия (без вопросов)
        if(sessionDTO.questionsList.isEmpty()){
            throw new RuntimeException("В сохраняемой сессии нет ни одного вопроса!");
        }

        Double result = sessionDTO.questionsList.stream()
                .map(this::validateOneQuestion).reduce(0.0, Double::sum);

        result = result / sessionDTO.questionsList.size() * 100.0;

        //сохраним сессию
        SessionEvent sessionEvent = new SessionEvent(sessionDTO.name, result);
        sessionEventRepository.save(sessionEvent);

        //сохраним выбранные ответы
        sessionDTO.questionsList
                .forEach((q -> q.answersList.stream()
                                                .filter(answerSessionDTO -> answerSessionDTO.isSelected)
                                                .map(answerSessionDTO -> answerRepository.findById(Long.parseLong(answerSessionDTO.id))
                                                    .orElseThrow(RuntimeException::new))
                                                .forEach(a -> selectedAnswerRepository
                                                        .save(new SelectedAnswer(a, sessionEvent)))));

        return String.format("%.2f", result)
                .replace(",", ".");
    }
}
