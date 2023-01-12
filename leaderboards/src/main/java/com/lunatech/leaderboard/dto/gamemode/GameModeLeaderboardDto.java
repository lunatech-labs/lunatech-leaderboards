package com.lunatech.leaderboard.dto.gamemode;

import com.lunatech.leaderboard.dto.game.GameDto;
import com.lunatech.leaderboard.dto.leaderboarduser.LeaderboardUserDto;
import com.lunatech.leaderboard.entity.GameMode;
import com.lunatech.leaderboard.entity.LeaderboardUser;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

public record GameModeLeaderboardDto(Long id, String name, String rules, GameDto game, List<LeaderboardUserDto> leaderboard) {
    public GameModeLeaderboardDto(GameMode gameMode) {
        this(gameMode.id, gameMode.name, gameMode.rules, new GameDto(gameMode.game), leaderboardToDto(gameMode.leaderboard));
    }

    private static List<LeaderboardUserDto> leaderboardToDto(Collection<LeaderboardUser> leaderboard) {
        return leaderboard.stream()
                .map(LeaderboardUserDto::new)
                .sorted(Comparator.comparing(LeaderboardUserDto::score).reversed())
                .toList();
    }
}
