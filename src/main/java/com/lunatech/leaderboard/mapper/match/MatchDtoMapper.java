package com.lunatech.leaderboard.mapper.match;

import com.lunatech.leaderboard.dto.match.MatchDto;
import com.lunatech.leaderboard.dto.match.MatchPostDto;
import com.lunatech.leaderboard.dto.matchuser.MatchUserDto;
import com.lunatech.leaderboard.dto.matchuser.MatchUserPostDto;
import com.lunatech.leaderboard.dto.user.UserDto;
import com.lunatech.leaderboard.entity.GameMode;
import com.lunatech.leaderboard.entity.Match;
import com.lunatech.leaderboard.entity.MatchUser;
import com.lunatech.leaderboard.entity.User;
import com.lunatech.leaderboard.mapper.DtoMapper;
import com.lunatech.leaderboard.service.UserService;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.util.List;

@RequestScoped
public class MatchDtoMapper implements DtoMapper<Match, MatchPostDto, MatchDto> {

    @Inject
    UserService userService;

    private Long gameModeId;

    @Override
    public Match toEntity(MatchPostDto dto) {
        Match match = new Match();
        match.gameMode = GameMode.findByIdWithMatches(gameModeId);
        match.outcome = dto.outcome();
        match.teamA = dto.teamA().stream().map(mu -> toMatchUser(mu, match, MatchUser.Team.TEAM_A)).toList();
        match.teamB = dto.teamB().stream().map(mu -> toMatchUser(mu, match, MatchUser.Team.TEAM_B)).toList();

        match.gameMode.matches.add(match);
        return match;
    }

    @Override
    public MatchDto toDto(Match match) {
        return new MatchDto(match.id, match.gameMode.id, match.outcome, match.confirmed, teamToDto(match.teamA), teamToDto(match.teamB));
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
        return new MatchUserDto(new UserDto(matchUser.user), null, matchUser.team, matchUser.outcomeConfirmed);
    }

    private List<MatchUserDto> teamToDto(List<MatchUser> team) {
        return team.stream().map(this::toMatchUserDto).toList();
    }

    public void setGameModeId(Long gameModeId) {
        this.gameModeId = gameModeId;
    }
}
