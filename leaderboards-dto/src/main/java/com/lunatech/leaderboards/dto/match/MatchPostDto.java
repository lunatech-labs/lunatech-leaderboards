package com.lunatech.leaderboards.dto.match;

import com.lunatech.leaderboards.dto.matchuser.MatchUserPostDto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public record MatchPostDto(
        @NotNull MatchDto.Outcome outcome,
        @NotEmpty List<MatchUserPostDto> teamA,
        @NotEmpty List<MatchUserPostDto> teamB) {
}
