package com.lunatech.leaderboards.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Map;

@Entity
@Table(name = "match_user")
@IdClass(MatchUser.MatchUserId.class)
public class MatchUser extends PanacheEntityBase {

    @Id
    @ManyToOne
    @JoinColumn(name = "app_user")
    public User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "match")
    public Match match;

    @Column(name = "team")
    @Enumerated(EnumType.STRING)
    public Team team;

    @Column(name = "outcome_confirmed")
    public boolean outcomeConfirmed;

    public static MatchUser findByMatchAndUser(Long match, Long user) {
        return find("match.id = :match and user.id = :user",
                Map.of("match", match, "user", user))
                .singleResult();
    }

    @Data
    public static class MatchUserId implements Serializable {
        private User user;
        private Match match;
    }

    public enum Team {TEAM_A, TEAM_B}
}
