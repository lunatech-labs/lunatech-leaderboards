package com.lunatech.leaderboard.dto.match;

import com.lunatech.leaderboard.dto.matchuser.MatchUserDto;
import com.lunatech.leaderboard.entity.Match;
import com.lunatech.leaderboard.entity.MatchUser;

import java.util.List;

public record MatchDto(Long id, Long gameModeId, Match.Outcome outcome, Boolean confirmed, List<MatchUserDto> teamA, List<MatchUserDto> teamB) {
}
