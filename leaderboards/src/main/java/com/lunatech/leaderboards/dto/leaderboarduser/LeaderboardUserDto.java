package com.lunatech.leaderboards.dto.leaderboarduser;

import com.lunatech.leaderboards.dto.user.UserDto;
import com.lunatech.leaderboards.entity.LeaderboardUser;

public record LeaderboardUserDto(UserDto user, Integer score, Integer growthFactor) {

    public LeaderboardUserDto(LeaderboardUser leaderboardUser) {
        this(new UserDto(leaderboardUser.user), leaderboardUser.score, leaderboardUser.growthFactor);
    }
}
