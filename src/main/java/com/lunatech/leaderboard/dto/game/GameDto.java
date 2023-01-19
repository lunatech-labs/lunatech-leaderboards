package com.lunatech.leaderboard.dto.game;

import com.lunatech.leaderboard.entity.Game;

import javax.validation.constraints.NotBlank;

public record GameDto(
        Long id,
        @NotBlank String name,
        String imageUrl
) {
    public GameDto(Game game) {
        this(game.id, game.name, game.imageUrl);
    }
}
