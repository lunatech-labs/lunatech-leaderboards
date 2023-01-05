package com.lunatech.leaderboards.dto.matchuser;

import com.lunatech.leaderboards.dto.match.MatchDto;
import com.lunatech.leaderboards.dto.user.UserDto;
import com.lunatech.leaderboards.entity.Match;
import com.lunatech.leaderboards.entity.MatchUser;
import com.lunatech.leaderboards.entity.User;

public record MatchUserDto(UserDto user, MatchDto match, MatchUser.Team team, boolean outcomeConfirmed) {
    public static MatchUserDto matchDto(MatchUser matchUser) {
        return new MatchUserDto(new UserDto(matchUser.user), null, matchUser.team, matchUser.outcomeConfirmed);
    }
}
