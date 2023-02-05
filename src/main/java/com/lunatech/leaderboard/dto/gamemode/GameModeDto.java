package com.lunatech.leaderboard.dto.gamemode;

import com.lunatech.leaderboard.entity.GameMode;

public record GameModeDto(Long id, Long gameId, String name, String rules) {
}
