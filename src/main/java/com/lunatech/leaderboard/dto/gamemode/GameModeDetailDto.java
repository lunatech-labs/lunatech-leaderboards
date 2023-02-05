package com.lunatech.leaderboard.dto.gamemode;

import com.lunatech.leaderboard.dto.game.GameDto;
import com.lunatech.leaderboard.model.leaderboarduser.LeaderboardUserDto;
import com.lunatech.leaderboard.entity.GameMode;
import com.lunatech.leaderboard.entity.LeaderboardUser;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

public record GameModeDetailDto(Long id, String name, String rules, GameDto game, List<LeaderboardUserDto> leaderboard) {
}
