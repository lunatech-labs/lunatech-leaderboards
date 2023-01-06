package com.lunatech.leaderboards.dto.matchuser;

import com.lunatech.leaderboards.entity.Match;
import com.lunatech.leaderboards.entity.MatchUser;
import com.lunatech.leaderboards.entity.User;

public record MatchUserPostDto(Long userId) {

    public MatchUser toEntity(Match match, MatchUser.Team team) {
        MatchUser matchUser = new MatchUser();
        matchUser.match = match;
        matchUser.team = team;
        matchUser.outcomeConfirmed = false;
        matchUser.user = User.findById(userId);
        return matchUser;
    }
}
