package com.lunatech.leaderboards.dto;

import com.lunatech.leaderboards.entity.Game;
import com.lunatech.leaderboards.entity.GameMode;

public record GameModeDto(Long id, String name, String rules, Long gameId) {
    public GameModeDto(GameMode gameMode) {
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
