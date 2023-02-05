package com.lunatech.leaderboards.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.panache.common.Parameters;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "game_mode")
public class GameMode extends PanacheEntityBase {

    @Id
    @SequenceGenerator(name="game_mode_id_seq", sequenceName="game_mode_id_seq", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="game_mode_id_seq")
    @Column(name = "id", updatable=false)
    public Long id;

    @Column(unique = true)
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
