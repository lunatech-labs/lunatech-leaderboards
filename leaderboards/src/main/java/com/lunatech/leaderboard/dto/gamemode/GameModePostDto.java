package com.lunatech.leaderboard.dto.gamemode;

import com.lunatech.leaderboard.entity.Game;
import com.lunatech.leaderboard.entity.GameMode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record GameModePostDto(
        Long id,
        @NotBlank String name,
        @NotBlank String rules,
        @NotNull Long gameId
) {
    public GameModePostDto(GameMode gameMode) {
        this(gameMode.id, gameMode.name, gameMode.rules, gameMode.game.id);
    }

    public GameMode toFetchedEntity() {
        GameMode gameMode = toEntity();
        gameMode.game = Game.findById(gameMode.game.id);
        return gameMode;
    }

    public GameMode toEntity() {
        GameMode gameMode = new GameMode();
        gameMode.id = id;
        gameMode.name = name;
        gameMode.rules = rules;

        gameMode.game = new Game();
        gameMode.game.id = gameId;
        return gameMode;
    }
}
