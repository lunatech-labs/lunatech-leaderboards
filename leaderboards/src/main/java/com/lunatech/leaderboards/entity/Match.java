package com.lunatech.leaderboards.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.*;

@Entity
@Table(name = "match")
public class Match extends PanacheEntity {

    @Enumerated(EnumType.STRING)
    public Outcome outcome;

    @ManyToOne
    @JoinColumn(name = "game_mode")
    public GameMode gameMode;

    public enum Outcome {
        TEAM_A, TEAM_B, DRAW, CANCELLED, ONGOING
    }
}
