package com.lunatech.leaderboards.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.panache.common.Parameters;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

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

    @Data
    public static class LeaderboardUserId implements Serializable {
        private User user;
        private GameMode gameMode;
    }

    public static LeaderboardUser findByUserAndGamemode(Long userId, Long gameModeId) {
        return find("gameMode.id = :gameMode and user.id = :user",
                Map.of("gameMode", gameModeId,"user", userId))
                .singleResult();
    }

    public static List<LeaderboardUser> findByUsersAndGamemode(Collection<Long> usersIds, Long gameModeId) {
        return find("gameMode.id = :gameMode and user.id in (:users)",
                Map.of("gameMode", gameModeId,"users", usersIds))
                .list();
    }
}
