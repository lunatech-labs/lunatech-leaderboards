package com.lunatech.leaderboards.service;

import com.lunatech.leaderboards.entity.Game;
import com.lunatech.leaderboards.entity.GameMode;
import io.quarkus.cache.CacheInvalidate;
import io.quarkus.cache.CacheResult;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class GameModeService {

    @CacheResult(cacheName = "game_mode")
    public List<GameMode> findAllByGame(Long gameId) {
        Game game = Game.findByIdWithGameModes(gameId);
        return game.gameModes;
    }

    @CacheInvalidate(cacheName = "game_mode")
    public GameMode add(GameMode gameMode) {
        gameMode.persist();
        return gameMode;
    }

    @CacheInvalidate(cacheName = "games")
    public GameMode delete(GameMode gameMode) {
        gameMode.delete();
        return gameMode;
    }
}
