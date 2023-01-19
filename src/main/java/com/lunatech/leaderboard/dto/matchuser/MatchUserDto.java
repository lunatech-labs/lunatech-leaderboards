package com.lunatech.leaderboard.dto.matchuser;

import com.lunatech.leaderboard.dto.match.MatchDto;
import com.lunatech.leaderboard.dto.user.UserDto;
import com.lunatech.leaderboard.entity.MatchUser;

public record MatchUserDto(UserDto user, MatchDto match, MatchUser.Team team, boolean outcomeConfirmed) {
    public static MatchUserDto matchDto(MatchUser matchUser) {
        return new MatchUserDto(new UserDto(matchUser.user), null, matchUser.team, matchUser.outcomeConfirmed);
    }
}
