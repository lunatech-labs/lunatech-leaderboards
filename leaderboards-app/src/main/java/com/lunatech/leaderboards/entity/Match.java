package com.lunatech.leaderboards.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "match")
public class Match extends PanacheEntityBase {

    @Id
    @SequenceGenerator(name="match_id_seq", sequenceName="match_id_seq", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="match_id_seq")
    @Column(name = "id", updatable=false)
    public Long id;

    @Enumerated(EnumType.STRING)
    public Outcome outcome;

    @ManyToOne
    @JoinColumn(name = "game_mode")
    public GameMode gameMode;

    @OneToMany(mappedBy = "match", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Where(clause = "team = 'TEAM_A'")
    public List<MatchUser> teamA;

    @OneToMany(mappedBy = "match", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Where(clause = "team = 'TEAM_B'")
    public List<MatchUser> teamB;

    @Column
    public boolean confirmed;

    public enum Outcome {
        TEAM_A, TEAM_B, DRAW, CANCELLED, ONGOING
    }
}
