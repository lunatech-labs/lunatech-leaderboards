package com.lunatech.leaderboard.dto.leaderboarduser;

import com.lunatech.leaderboard.dto.user.UserDto;
import com.lunatech.leaderboard.entity.LeaderboardUser;

public record LeaderboardUserDto(UserDto user, Integer score, Integer growthFactor) {

    public LeaderboardUserDto(LeaderboardUser leaderboardUser) {
        this(new UserDto(leaderboardUser.user), leaderboardUser.score, leaderboardUser.growthFactor);
    }
}
