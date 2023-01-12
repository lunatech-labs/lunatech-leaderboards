package com.lunatech.leaderboard.dto.match;

import com.lunatech.leaderboard.dto.matchuser.MatchUserDto;
import com.lunatech.leaderboard.entity.Match;
import com.lunatech.leaderboard.entity.MatchUser;

import java.util.List;

public record MatchDto(Long id, Long gameModeId, Match.Outcome outcome, Boolean confirmed, List<MatchUserDto> teamA, List<MatchUserDto> teamB) {
    public MatchDto(Match match) {
        this(match.id, match.gameMode.id, match.outcome, match.confirmed, teamToDto(match.teamA), teamToDto(match.teamB));
    }

    private static List<MatchUserDto> teamToDto(List<MatchUser> team) {
        return team.stream().map(MatchUserDto::matchDto).toList();
    }
}
