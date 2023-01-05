package com.lunatech.leaderboards.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "leaderboard_user")
@IdClass(LeaderboardUser.LeaderboardUserId.class)
public class LeaderboardUser extends PanacheEntityBase {
    @Id
    @ManyToOne
    @JoinColumn(name = "app_user")
    public User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "game_mode")
    public GameMode gameMode;

    public Integer score;

    @Column(name = "growth_factor")
    public Integer growthFactor;

    public record LeaderboardUserId(User user, GameMode gameMode) implements Serializable {}
}
