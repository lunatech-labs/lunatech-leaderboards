package com.lunatech.leaderboard.mapper.gamemode;

import com.lunatech.leaderboard.dto.game.GameDto;
import com.lunatech.leaderboard.dto.gamemode.GameModeDetailDto;
import com.lunatech.leaderboard.entity.GameMode;
import com.lunatech.leaderboard.entity.LeaderboardUser;
import com.lunatech.leaderboard.mapper.DtoMapper;
import com.lunatech.leaderboard.mapper.game.GameDtoMapper;
import com.lunatech.leaderboard.model.leaderboarduser.LeaderboardUserDto;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

@RequestScoped
public class GameModeDetailMapper implements DtoMapper<GameMode, Void, GameModeDetailDto> {

    @Inject
    GameDtoMapper gameDtoMapper;

    @Override
    public GameMode toEntity(Void unused) {
        throw new UnsupportedOperationException();
    }

    @Override
    public GameModeDetailDto toDto(GameMode gameMode) {
        GameDto gameDto = gameDtoMapper.toDto(gameMode.game);
        return new GameModeDetailDto(gameMode.id, gameMode.name, gameMode.rules, gameDto, leaderboardToDto(gameMode.leaderboard));
    }

    private static List<LeaderboardUserDto> leaderboardToDto(Collection<LeaderboardUser> leaderboard) {
        return leaderboard.stream()
                .map(LeaderboardUserDto::new)
                .sorted(Comparator.comparing(LeaderboardUserDto::score).reversed())
                .toList();
    }
}
