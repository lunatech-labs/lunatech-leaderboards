package com.lunatech.leaderboard.service;

import com.lunatech.leaderboard.entity.Game;
import io.quarkus.cache.CacheInvalidate;
import io.quarkus.cache.CacheResult;

import javax.enterprise.context.ApplicationScoped;
import java.util.Collection;

@ApplicationScoped
public class GameService {

    @CacheResult(cacheName = "games")
    public Collection<Game> findAll() {
        return Game.<Game>findAll().list();
    }

    @CacheInvalidate(cacheName = "games")
    public Game add(Game game) {
        game.persist();
        return game;
    }

    @CacheInvalidate(cacheName = "games")
    public Game delete(Long gameId) {
        Game game = Game.findById(gameId);
        game.delete();
        return game;
    }
}
