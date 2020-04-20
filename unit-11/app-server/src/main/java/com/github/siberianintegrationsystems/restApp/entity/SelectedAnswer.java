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

    public SelectedAnswer(Answer answer, SessionEvent sessionEvent){
        this.answer = answer;
        this.sessionEvent = sessionEvent;
    }

}
