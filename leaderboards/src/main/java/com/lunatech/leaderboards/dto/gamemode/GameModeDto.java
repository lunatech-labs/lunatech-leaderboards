package com.lunatech.leaderboards.dto.gamemode;

import com.lunatech.leaderboards.dto.match.MatchDto;
import com.lunatech.leaderboards.entity.GameMode;
import com.lunatech.leaderboards.entity.Match;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public record GameModeDto(Long id, Long gameId, String name, String rules) {
    public GameModeDto(GameMode gameMode) {
        this(gameMode.id, gameMode.game.id, gameMode.name, gameMode.rules);
    }
}
