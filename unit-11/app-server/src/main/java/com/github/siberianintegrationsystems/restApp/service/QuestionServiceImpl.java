package com.github.siberianintegrationsystems.restApp.service;

import com.github.siberianintegrationsystems.restApp.controller.dto.AnswerItemDTO;
import com.github.siberianintegrationsystems.restApp.controller.dto.QuestionsItemDTO;
import com.github.siberianintegrationsystems.restApp.data.AnswerRepository;
import com.github.siberianintegrationsystems.restApp.data.QuestionRepository;
import com.github.siberianintegrationsystems.restApp.entity.Answer;
import com.github.siberianintegrationsystems.restApp.entity.Question;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    public QuestionServiceImpl(QuestionRepository questionRepository,
                               AnswerRepository answerRepository) {
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
    }

    @Override
    public QuestionsItemDTO createQuestion(QuestionsItemDTO dto) {
        Question question = new Question();
        question.setName(dto.name);
        questionRepository.save(question);

        for (AnswerItemDTO answerDTO : dto.answers) {
            Answer answer = new Answer();
            answer.setName(answerDTO.answerText);
            answer.setCorrect(answerDTO.isCorrect);
            answer.setQuestion(question);

            answerRepository.save(answer);
        }

        return new QuestionsItemDTO(question,
                answerRepository.findByQuestion(question));
    }

    @Override
    public QuestionsItemDTO editQuestion(QuestionsItemDTO dto){

        //Удалим из базы вопрос с таким ID и ответы к нему
        Question question = questionRepository.findById(Long.parseLong(dto.id))
                .orElseThrow(() -> new RuntimeException(
                        String.format("Не найден вопрос с id %s", dto.id)));

        questionRepository.deleteById(Long.parseLong(dto.id));
        List<Answer> answers = answerRepository.findByQuestion(question);
        for(Answer a : answers){
            answerRepository.delete(a);
        }
        //Созданим новый вопрос и сохраним в базу
        return createQuestion(dto);
    }
}
