package com.lunatech.leaderboards.dto.leaderboard;

import com.lunatech.leaderboards.dto.user.UserDto;

public record LeaderboardUserDto(UserDto user, Integer score, Integer growthFactor) {
}
