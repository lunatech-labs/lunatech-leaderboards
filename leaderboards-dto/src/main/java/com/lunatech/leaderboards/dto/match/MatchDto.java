package com.lunatech.leaderboards.dto.match;

import com.lunatech.leaderboards.dto.matchuser.MatchUserDto;

import java.util.List;

public record MatchDto(Long id, Long gameModeId, Outcome outcome, Boolean confirmed, List<MatchUserDto> teamA, List<MatchUserDto> teamB) {
    public enum Outcome {
        TEAM_A, TEAM_B, DRAW, CANCELLED, ONGOING
    }
}
