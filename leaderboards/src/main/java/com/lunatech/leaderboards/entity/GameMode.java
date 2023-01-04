package com.lunatech.leaderboards.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "game_mode")
public class GameMode extends PanacheEntity {
    public String name;

    public String rules;

    @ManyToOne
    @JoinColumn(name = "game")
    public Game game;
}
