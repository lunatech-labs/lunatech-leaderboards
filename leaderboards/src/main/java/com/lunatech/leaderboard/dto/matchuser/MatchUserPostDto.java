package com.lunatech.leaderboard.dto.matchuser;

import com.lunatech.leaderboard.entity.Match;
import com.lunatech.leaderboard.entity.MatchUser;
import com.lunatech.leaderboard.entity.User;
import com.lunatech.leaderboard.service.UserService;

public record MatchUserPostDto(String userEmail) {

    public MatchUser toEntity(Match match, MatchUser.Team team, UserService userService) {
        MatchUser matchUser = new MatchUser();
        matchUser.match = match;
        matchUser.team = team;
        matchUser.outcomeConfirmed = false;

        matchUser.user = User.findByEmail(userEmail)
                .orElseGet(() -> userService.add(userEmail));
        return matchUser;
    }
}
