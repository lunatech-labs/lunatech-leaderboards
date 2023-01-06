package com.lunatech.leaderboards.dto.match;

import com.lunatech.leaderboards.dto.matchuser.MatchUserPostDto;
import com.lunatech.leaderboards.entity.GameMode;
import com.lunatech.leaderboards.entity.Match;
import com.lunatech.leaderboards.entity.MatchUser;

import java.util.List;

public record MatchPostDto(Match.Outcome outcome, Long gameModeId, List<MatchUserPostDto> teamA, List<MatchUserPostDto> teamB) {

    public Match toEntity() {
        GameMode gameMode = new GameMode();
        gameMode.id = gameModeId;

        Match match = new Match();
        match.gameMode = gameMode;
        match.outcome = outcome;
        match.teamA = teamA.stream().map(dto -> dto.toEntity(match, MatchUser.Team.TEAM_A)).toList();
        match.teamB = teamB.stream().map(dto -> dto.toEntity(match, MatchUser.Team.TEAM_B)).toList();
        return match;
    }
}
