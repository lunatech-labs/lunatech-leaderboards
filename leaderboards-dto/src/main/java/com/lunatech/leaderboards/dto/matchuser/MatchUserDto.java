package com.lunatech.leaderboards.dto.matchuser;

import com.lunatech.leaderboards.dto.match.MatchDto;
import com.lunatech.leaderboards.dto.user.UserDto;

public record MatchUserDto(UserDto user, MatchDto match, Team team, boolean outcomeConfirmed) {
    public enum Team {TEAM_A, TEAM_B}
}
