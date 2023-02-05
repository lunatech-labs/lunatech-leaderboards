package com.lunatech.leaderboards.service;

import com.lunatech.leaderboards.entity.GameMode;
import com.lunatech.leaderboards.entity.Match;
import com.lunatech.leaderboards.entity.MatchUser;
import com.lunatech.leaderboards.entity.User;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Collection;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Singleton
public class MatchService {

    @Inject
    LeaderboardService leaderboardService;

    public Collection<Match> findByGameMode(Long gameModeId) {
        return GameMode.findByIdWithMatches(gameModeId).matches;
    }

    public void save(Match match) {
        match.persist();
    }

    public void accept(Long matchId, Long userId) {
        MatchUser matchUser = MatchUser.findByMatchAndUser(matchId, userId);
        matchUser.outcomeConfirmed = true;

        Match match = matchUser.match;
        Function<Collection<MatchUser>, Boolean> allConfirmed = (team) -> team.stream().allMatch(mu -> mu.outcomeConfirmed);
        boolean teamAConfirmed = allConfirmed.apply(match.teamA);
        boolean teamBConfirmed = allConfirmed.apply(match.teamB);

        if(teamAConfirmed && teamBConfirmed) {
            confirm(match);
        }
    }

    private void confirm(Match match) {
        match.confirmed = true;

        Function<Collection<MatchUser>, Set<User>> matchUserToUser = matchUsers ->
                matchUsers.stream().map(mu -> mu.user).collect(Collectors.toSet());

        Set<User> teamAUsers = matchUserToUser.apply(match.teamA);
        Set<User> teamBUsers = matchUserToUser.apply(match.teamB);
        leaderboardService.evaluateMatch(teamAUsers, teamBUsers, match);
    }
}
