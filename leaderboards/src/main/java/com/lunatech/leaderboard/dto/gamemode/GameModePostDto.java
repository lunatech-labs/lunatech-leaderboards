package com.lunatech.leaderboard.dto.gamemode;

import com.lunatech.leaderboard.entity.Game;
import com.lunatech.leaderboard.entity.GameMode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record GameModePostDto(
        Long id,
        @NotBlank String name,
        @NotBlank String rules
) { }
