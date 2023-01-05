package com.lunatech.leaderboards.dto.gamemode;

import com.lunatech.leaderboards.dto.leaderboarduser.LeaderboardUserDto;
import com.lunatech.leaderboards.entity.GameMode;
import com.lunatech.leaderboards.entity.LeaderboardUser;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

public record GameModeLeaderboardDto(Long id, Long gameId, String name, String rules, List<LeaderboardUserDto> leaderboard) {
    public GameModeLeaderboardDto(GameMode gameMode) {
        this(gameMode.id, gameMode.game.id, gameMode.name, gameMode.rules, leaderboardToDto(gameMode.leaderboard));
    }

    private static List<LeaderboardUserDto> leaderboardToDto(Collection<LeaderboardUser> leaderboard) {
        return leaderboard.stream()
                .map(LeaderboardUserDto::new)
                .sorted(Comparator.comparing(LeaderboardUserDto::score).reversed())
                .toList();
    }
}
