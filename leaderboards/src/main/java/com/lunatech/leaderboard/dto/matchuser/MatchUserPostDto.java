package com.lunatech.leaderboard.dto.matchuser;

import com.lunatech.leaderboard.entity.Match;
import com.lunatech.leaderboard.entity.MatchUser;
import com.lunatech.leaderboard.entity.User;

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
