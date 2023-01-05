package com.lunatech.leaderboards.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;

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

    @Data
    public static class MatchUserId implements Serializable {
        private User user;
        private Match match;
    }

    public enum Team {TEAM_A, TEAM_B}
}
