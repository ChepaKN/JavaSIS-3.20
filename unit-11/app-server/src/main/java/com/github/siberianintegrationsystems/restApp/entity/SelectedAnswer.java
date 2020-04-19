package com.github.siberianintegrationsystems.restApp.entity;

import javax.persistence.*;

@Entity
public class SelectedAnswer extends BaseEntity {

    @JoinColumn(name = "answer_id")
    @OneToOne(fetch = FetchType.LAZY)
    private Answer answer;

    @JoinColumn(name = "session_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private SessionEvent sessionEvent;

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }

    public SessionEvent getSessionEvent() {
        return sessionEvent;
    }

    public void setSessionEvent(SessionEvent sessionEvent) {
        this.sessionEvent = sessionEvent;
    }
}
