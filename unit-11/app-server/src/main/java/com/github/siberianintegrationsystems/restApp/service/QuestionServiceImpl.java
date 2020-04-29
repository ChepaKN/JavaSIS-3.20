package com.github.siberianintegrationsystems.restApp.service;

import com.github.siberianintegrationsystems.restApp.controller.dto.AnswerItemDTO;
import com.github.siberianintegrationsystems.restApp.controller.dto.QuestionsItemDTO;
import com.github.siberianintegrationsystems.restApp.data.AnswerRepository;
import com.github.siberianintegrationsystems.restApp.data.QuestionRepository;
import com.github.siberianintegrationsystems.restApp.entity.Answer;
import com.github.siberianintegrationsystems.restApp.entity.Question;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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

    //Хоть клиет и не позволит так сделать, проверка лишней не будет
    private void checkAnswersForQuestion(QuestionsItemDTO dto){
        if (dto.answers == null || dto.answers.isEmpty()) {
            throw new RuntimeException("Не указан ни один ответ к вопросу!");
        } else {
            if (dto.answers.stream().noneMatch(answerItemDTO -> answerItemDTO.isCorrect)) {
                throw new RuntimeException("Не указан ни один верный ответ!");
            }
        }
    }

    @Override
    public QuestionsItemDTO createQuestion(QuestionsItemDTO dto) {

        //Указал ли клиет верный ответ
        checkAnswersForQuestion(dto);

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

        //Указал ли клиет верный ответ
        checkAnswersForQuestion(dto);

        //Найдем вопрос с таким ID
        Question question = questionRepository.findById(Long.parseLong(dto.id))
                .orElseThrow(() -> new RuntimeException(
                        String.format("Не найден вопрос с id %s", dto.id)));

        //Если тект вопроса был отредактирован - обновим запись в базе
        if(!question.getName().equals(dto.name)){
            question.setName(dto.name);
            questionRepository.save(question);
        }

        //Удалим старые ответы к этому вопросу
        answerRepository
                .findByQuestion(question)
                .forEach(answerRepository::delete);

        //Создадим и сохраним новые
        for(AnswerItemDTO a : dto.answers){
            Answer answer = new Answer();
            answer.setName(a.answerText);
            answer.setCorrect(a.isCorrect);
            answer.setQuestion(question);
            answerRepository.save(answer);
        }

        return new QuestionsItemDTO(question,
                answerRepository.findByQuestion(question));
    }

    @Override
    public List<QuestionsItemDTO> getQuestionsForSession(){


        List<QuestionsItemDTO> questionsItemDTOList =
                questionRepository.findRandQuestions()
                    .stream()
                    .map(question -> {
                                //перемешаем ответы
                                List<Answer> answers = answerRepository.findByQuestion(question);
                                Collections.shuffle(answers);
                                return new QuestionsItemDTO(question,
                                        answers);
                            }
                    )
                    .collect(Collectors.toList());

        //Скроем правильные ответы от хитрых пользователей
        questionsItemDTOList
                .forEach(questionsItemDTO -> questionsItemDTO.answers
                        .forEach(answerItemDTO -> answerItemDTO.isCorrect = false));

        return questionsItemDTOList;
    }
}
