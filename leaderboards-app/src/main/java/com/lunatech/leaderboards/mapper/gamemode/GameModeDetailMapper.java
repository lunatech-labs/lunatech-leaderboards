package com.lunatech.leaderboards.mapper.gamemode;

import com.lunatech.leaderboards.dto.game.GameDto;
import com.lunatech.leaderboards.dto.gamemode.GameModeDetailDto;
import com.lunatech.leaderboards.dto.leaderboard.LeaderboardUserDto;
import com.lunatech.leaderboards.entity.GameMode;
import com.lunatech.leaderboards.entity.LeaderboardUser;
import com.lunatech.leaderboards.mapper.DtoMapper;
import com.lunatech.leaderboards.mapper.game.GameDtoMapper;
import com.lunatech.leaderboards.mapper.user.UserDtoMapper;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

@RequestScoped
public class GameModeDetailMapper implements DtoMapper<GameMode, Void, GameModeDetailDto> {

    @Inject
    GameDtoMapper gameDtoMapper;

    @Inject
    UserDtoMapper userDtoMapper;

    @Override
    public GameMode toEntity(Void unused) {
        throw new UnsupportedOperationException();
    }

    @Override
    public GameModeDetailDto toDto(GameMode gameMode) {
        GameDto gameDto = gameDtoMapper.toDto(gameMode.game);
        return new GameModeDetailDto(gameMode.id, gameMode.name, gameMode.rules, gameDto, leaderboardToDto(gameMode.leaderboard));
    }

    private List<LeaderboardUserDto> leaderboardToDto(Collection<LeaderboardUser> leaderboard) {
        return leaderboard.stream()
                .map(this::leaderboardUserToDto)
                .sorted(Comparator.comparing(LeaderboardUserDto::score).reversed())
                .toList();
    }

    private LeaderboardUserDto leaderboardUserToDto(LeaderboardUser leaderboardUser) {
        return new LeaderboardUserDto(userDtoMapper.toDto(leaderboardUser.user), leaderboardUser.score, leaderboardUser.growthFactor);
    }
}
