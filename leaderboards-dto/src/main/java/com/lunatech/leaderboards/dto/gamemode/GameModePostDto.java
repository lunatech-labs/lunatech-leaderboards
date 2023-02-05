package com.lunatech.leaderboards.dto.gamemode;

import javax.validation.constraints.NotBlank;

public record GameModePostDto(
        Long id,
        @NotBlank String name,
        @NotBlank String rules
) { }
