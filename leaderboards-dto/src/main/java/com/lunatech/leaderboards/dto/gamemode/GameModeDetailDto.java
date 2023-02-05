package com.lunatech.leaderboards.dto.gamemode;

import com.lunatech.leaderboards.dto.game.GameDto;
import com.lunatech.leaderboards.dto.leaderboard.LeaderboardUserDto;

import java.util.List;

public record GameModeDetailDto(Long id, String name, String rules, GameDto game, List<LeaderboardUserDto> leaderboard) {
}
