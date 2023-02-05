package com.lunatech.leaderboards.mapper.gamemode;

import com.lunatech.leaderboards.dto.gamemode.GameModeDto;
import com.lunatech.leaderboards.dto.gamemode.GameModePostDto;
import com.lunatech.leaderboards.entity.Game;
import com.lunatech.leaderboards.entity.GameMode;
import com.lunatech.leaderboards.mapper.DtoMapper;

import javax.enterprise.context.RequestScoped;

@RequestScoped
public class GameModeDtoMapper implements DtoMapper<GameMode, GameModePostDto, GameModeDto> {

    private Long gameId;

    @Override
    public GameMode toEntity(GameModePostDto dto) {
        GameMode gameMode = new GameMode();
        gameMode.id = dto.id();
        gameMode.name = dto.name();
        gameMode.rules = dto.rules();

        gameMode.game = Game.findByIdWithGameModes(gameId);
        gameMode.game.gameModes.add(gameMode);
        return gameMode;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    @Override
    public GameModeDto toDto(GameMode gameMode) {
        return new GameModeDto(gameMode.id, gameMode.game.id, gameMode.name, gameMode.rules);
    }
}
