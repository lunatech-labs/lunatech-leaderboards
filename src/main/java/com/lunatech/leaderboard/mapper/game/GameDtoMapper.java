package com.lunatech.leaderboard.mapper.game;

import com.lunatech.leaderboard.dto.game.GameDto;
import com.lunatech.leaderboard.entity.Game;
import com.lunatech.leaderboard.mapper.DtoMapper;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class GameDtoMapper implements DtoMapper<GameDto, Game> {
    @Override
    public Game toEntity(GameDto dto) {
        Game game = new Game();
        game.id = dto.id();
        game.name = dto.name();
        game.imageUrl = dto.imageUrl();
        return game;
    }
}
