package com.github.siberianintegrationsystems.restApp.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class SessionEvent extends BaseEntity {


    @Column
    private LocalDateTime date;

    @Column
    private String name;

    @Column
    private Double percent;

    public SessionEvent(){

    }

    public SessionEvent(String name, Double percent) {
        this.name = name;
        this.percent = percent;
        date = LocalDateTime.now();
    }

    public LocalDateTime getSessionTime() {
        return date;
    }

    public Double getPercent() {
        return percent;
    }

    public String getName() {
        return name;
    }
}
