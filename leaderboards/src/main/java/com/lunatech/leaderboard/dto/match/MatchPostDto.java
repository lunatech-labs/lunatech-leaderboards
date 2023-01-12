package com.lunatech.leaderboard.dto.match;

import com.lunatech.leaderboard.dto.matchuser.MatchUserPostDto;
import com.lunatech.leaderboard.entity.GameMode;
import com.lunatech.leaderboard.entity.Match;
import com.lunatech.leaderboard.entity.MatchUser;
import com.lunatech.leaderboard.service.UserService;

import java.util.List;

public record MatchPostDto(Match.Outcome outcome, Long gameModeId, List<MatchUserPostDto> teamA, List<MatchUserPostDto> teamB) {

    public Match toEntity(UserService userService) {
        GameMode gameMode = new GameMode();
        gameMode.id = gameModeId;

        Match match = new Match();
        match.gameMode = gameMode;
        match.outcome = outcome;
        match.teamA = teamA.stream().map(dto -> dto.toEntity(match, MatchUser.Team.TEAM_A, userService)).toList();
        match.teamB = teamB.stream().map(dto -> dto.toEntity(match, MatchUser.Team.TEAM_B, userService)).toList();
        return match;
    }
}
