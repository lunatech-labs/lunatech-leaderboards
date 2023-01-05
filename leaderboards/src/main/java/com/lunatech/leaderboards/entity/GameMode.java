package com.lunatech.leaderboards.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.panache.common.Parameters;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "game_mode")
public class GameMode extends PanacheEntity {
    public String name;

    public String rules;

    @ManyToOne
    @JoinColumn(name = "game")
    public Game game;

    @OneToMany(mappedBy = "gameMode")
    public Set<Match> matches;

    @OneToMany(mappedBy = "gameMode")
    public Set<LeaderboardUser> leaderboard;

    public static GameMode findByIdWithMatches(Long id) {
        return find("""
                select distinct obj from GameMode obj 
                left join fetch obj.matches 
                where obj.id = :id
                """, Parameters.with("id", id)).firstResult();
    }

    public static GameMode findByIdWithLeaderboard(Long id) {
        return find("""
                select distinct obj from GameMode obj 
                left join fetch obj.leaderboard
                where obj.id = :id
                """, Parameters.with("id", id)).firstResult();
    }
}
