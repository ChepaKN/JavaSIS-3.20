package com.github.siberianintegrationsystems.restApp.data;

import com.github.siberianintegrationsystems.restApp.entity.Question;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface QuestionRepository
        extends CrudRepository<Question, Long> {

    List<Question> findByNameContainingIgnoreCase(String search);

    int questionsPerSession = 5;
    @Query(value = "SELECT * FROM QUESTION ORDER BY RAND() LIMIT " +
            questionsPerSession, nativeQuery = true)
    List<Question> findRandQuestions();
}
