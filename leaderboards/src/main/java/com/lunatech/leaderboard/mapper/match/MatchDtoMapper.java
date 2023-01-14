package com.lunatech.leaderboard.mapper.match;

import com.lunatech.leaderboard.dto.match.MatchPostDto;
import com.lunatech.leaderboard.dto.matchuser.MatchUserPostDto;
import com.lunatech.leaderboard.entity.GameMode;
import com.lunatech.leaderboard.entity.Match;
import com.lunatech.leaderboard.entity.MatchUser;
import com.lunatech.leaderboard.entity.User;
import com.lunatech.leaderboard.mapper.DtoMapper;
import com.lunatech.leaderboard.service.UserService;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class MatchDtoMapper implements DtoMapper<MatchPostDto, Match> {

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

    private MatchUser toMatchUser(MatchUserPostDto dto, Match match, MatchUser.Team team) {
        MatchUser matchUser = new MatchUser();
        matchUser.match = match;
        matchUser.team = team;
        matchUser.outcomeConfirmed = false;

        matchUser.user = User.findByEmail(dto.userEmail())
                .orElseGet(() -> userService.add(dto.userEmail()));
        return matchUser;
    }

    public void setGameModeId(Long gameModeId) {
        this.gameModeId = gameModeId;
    }
}
