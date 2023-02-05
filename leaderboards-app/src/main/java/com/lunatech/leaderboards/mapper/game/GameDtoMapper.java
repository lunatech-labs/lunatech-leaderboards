package com.lunatech.leaderboards.mapper.game;

import com.lunatech.leaderboards.dto.game.GameDto;
import com.lunatech.leaderboards.entity.Game;
import com.lunatech.leaderboards.mapper.DtoMapper;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class GameDtoMapper implements DtoMapper<Game, GameDto, GameDto> {
    @Override
    public Game toEntity(GameDto dto) {
        Game game = new Game();
        game.id = dto.id();
        game.name = dto.name();
        game.imageUrl = dto.imageUrl();
        return game;
    }

    @Override
    public GameDto toDto(Game game) {
        return new GameDto(game.id, game.name, game.imageUrl);
    }
}
