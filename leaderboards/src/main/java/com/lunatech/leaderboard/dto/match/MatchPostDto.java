package com.lunatech.leaderboard.dto.match;

import com.lunatech.leaderboard.dto.matchuser.MatchUserPostDto;
import com.lunatech.leaderboard.entity.GameMode;
import com.lunatech.leaderboard.entity.Match;
import com.lunatech.leaderboard.entity.MatchUser;
import com.lunatech.leaderboard.service.UserService;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public record MatchPostDto(
        @NotNull Match.Outcome outcome,
        @NotEmpty List<MatchUserPostDto> teamA,
        @NotEmpty List<MatchUserPostDto> teamB) {
}
