package com.lunatech.leaderboards.dto.game;

import javax.validation.constraints.NotBlank;

public record GameDto(
        Long id,
        @NotBlank String name,
        String imageUrl
) {
}
