package com.lunatech.leaderboards.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

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

    @Column(name = "outcome_confirmed")
    public boolean outcomeConfirmed;

    public record MatchUserId(User user, Match match) implements Serializable {}
}
