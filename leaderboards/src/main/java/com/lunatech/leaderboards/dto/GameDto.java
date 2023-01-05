package com.lunatech.leaderboards.dto;

import com.lunatech.leaderboards.entity.Game;

import javax.validation.constraints.NotBlank;

public record GameDto(
        Long id,
        @NotBlank String name,
        String imageUrl
) {
    public GameDto(Game game) {
        this(game.id, game.name, game.imageUrl);
    }

    public Game toEntity() {
        Game game = new Game();
        game.id = id;
        game.name = name;
        game.imageUrl = imageUrl;
        return game;
    }
}
