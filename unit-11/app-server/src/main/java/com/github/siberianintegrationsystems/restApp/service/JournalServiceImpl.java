package com.github.siberianintegrationsystems.restApp.service;

import com.github.siberianintegrationsystems.restApp.controller.dto.JournalItemDTO;
import com.github.siberianintegrationsystems.restApp.controller.dto.JournalRequestDTO;
import com.github.siberianintegrationsystems.restApp.controller.dto.QuestionsItemDTO;
import com.github.siberianintegrationsystems.restApp.controller.dto.SessionItemDTO;
import com.github.siberianintegrationsystems.restApp.data.AnswerRepository;
import com.github.siberianintegrationsystems.restApp.data.JournalRepository;
import com.github.siberianintegrationsystems.restApp.data.QuestionRepository;
import com.github.siberianintegrationsystems.restApp.data.SessionRepository;
import com.github.siberianintegrationsystems.restApp.entity.BaseEntity;
import com.github.siberianintegrationsystems.restApp.entity.Journal;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional
public class JournalServiceImpl implements JournalService {

    public static final String QUESTIONS_JOURNAL_ID = "questions";
    public static final String SESSIONS_JOURNAL_ID = "sessions";

    private final JournalRepository journalRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final SessionRepository sessionRepository;

    public JournalServiceImpl(JournalRepository journalRepository,
                              QuestionRepository questionRepository,
                              AnswerRepository answerRepository,
                              SessionRepository sessionRepository) {
        this.journalRepository = journalRepository;
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
        this.sessionRepository = sessionRepository;
    }

    @Override
    public Journal getJournal(String id) {
        return journalRepository.findById(id)
                .orElseThrow(RuntimeException::new);
    }

    @Override
    public List<? extends JournalItemDTO> getJournalRows(
            String id, JournalRequestDTO req) {
        List<? extends JournalItemDTO> collection;
        switch (id) {
            case QUESTIONS_JOURNAL_ID:
                collection = getCollection(
                        req.search,
                        questionRepository::findByNameContainingIgnoreCase,
                        q -> new QuestionsItemDTO(
                                q,
                                answerRepository.findByQuestion(q)));
                break;

            case SESSIONS_JOURNAL_ID:

                collection = null;
                break;
            default:
                throw new RuntimeException();
        }

        return collection;
    }

    private <T extends BaseEntity, U extends JournalItemDTO> List<U> getCollection(
            String search,
            Function<String, List<T>> finder,
            Function<T, U> mapper
            ) {
        return finder.apply(search)
                .stream()
                .map(mapper)
                .collect(Collectors.toList());
    }
}
