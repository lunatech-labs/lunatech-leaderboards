package com.lunatech.leaderboards.mapper.match;

import com.lunatech.leaderboards.dto.match.MatchDto;
import com.lunatech.leaderboards.dto.match.MatchPostDto;
import com.lunatech.leaderboards.dto.matchuser.MatchUserDto;
import com.lunatech.leaderboards.dto.matchuser.MatchUserPostDto;
import com.lunatech.leaderboards.entity.GameMode;
import com.lunatech.leaderboards.entity.Match;
import com.lunatech.leaderboards.entity.MatchUser;
import com.lunatech.leaderboards.entity.User;
import com.lunatech.leaderboards.mapper.DtoMapper;
import com.lunatech.leaderboards.mapper.user.UserDtoMapper;
import com.lunatech.leaderboards.service.UserService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.util.List;

@RequestScoped
public class MatchDtoMapper implements DtoMapper<Match, MatchPostDto, MatchDto> {

    @Inject
    UserService userService;

    @Inject
    UserDtoMapper userDtoMapper;

    private Long gameModeId;

    @Override
    public Match toEntity(MatchPostDto dto) {
        Match match = new Match();
        match.gameMode = GameMode.findByIdWithMatches(gameModeId);
        match.outcome = outcomeToEntity(dto.outcome());
        match.teamA = dto.teamA().stream().map(mu -> toMatchUser(mu, match, MatchUser.Team.TEAM_A)).toList();
        match.teamB = dto.teamB().stream().map(mu -> toMatchUser(mu, match, MatchUser.Team.TEAM_B)).toList();

        match.gameMode.matches.add(match);
        return match;
    }

    @Override
    public MatchDto toDto(Match match) {
        return new MatchDto(match.id, match.gameMode.id, outcomeToDto(match.outcome), match.confirmed, teamToDto(match.teamA), teamToDto(match.teamB));
    }

    private MatchUser toMatchUser(MatchUserPostDto dto, Match match, MatchUser.Team team) {
        MatchUser matchUser = new MatchUser();
        matchUser.match = match;
        matchUser.team = team;
        matchUser.outcomeConfirmed = false;

        matchUser.user = User.findByEmail(dto.userEmail())
                .orElseGet(() -> userService.add(dto.userEmail()));
        return matchUser;
    }

    private MatchUserDto toMatchUserDto(MatchUser matchUser) {
        return new MatchUserDto(userDtoMapper.toDto(matchUser.user), null, teamToDto(matchUser.team), matchUser.outcomeConfirmed);
    }

    private List<MatchUserDto> teamToDto(List<MatchUser> team) {
        return team.stream().map(this::toMatchUserDto).toList();
    }

    public void setGameModeId(Long gameModeId) {
        this.gameModeId = gameModeId;
    }

    private MatchUserDto.Team teamToDto(MatchUser.Team team) {
        return switch (team) {
            case TEAM_A -> MatchUserDto.Team.TEAM_A;
            case TEAM_B -> MatchUserDto.Team.TEAM_B;
        };
    }

    private MatchDto.Outcome outcomeToDto(Match.Outcome outcome) {
        return switch (outcome) {
            case TEAM_A -> MatchDto.Outcome.TEAM_A;
            case TEAM_B -> MatchDto.Outcome.TEAM_B;
            case DRAW -> MatchDto.Outcome.DRAW;
            case CANCELLED -> MatchDto.Outcome.CANCELLED;
            case ONGOING -> MatchDto.Outcome.ONGOING;
        };
    }

    private Match.Outcome outcomeToEntity(MatchDto.Outcome outcome) {
        return switch (outcome) {
            case TEAM_A -> Match.Outcome.TEAM_A;
            case TEAM_B -> Match.Outcome.TEAM_B;
            case DRAW -> Match.Outcome.DRAW;
            case CANCELLED -> Match.Outcome.CANCELLED;
            case ONGOING -> Match.Outcome.ONGOING;
        };
    }
}
