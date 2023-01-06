package com.lunatech.leaderboards.service;

import com.lunatech.leaderboards.dto.leaderboarduser.MatchExpectedResult;
import com.lunatech.leaderboards.entity.*;
import io.quarkus.hibernate.orm.panache.Panache;

import javax.inject.Named;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.*;
import java.util.stream.Collectors;

@Named
public class LeaderboardService {

    public void evaluateMatch(Collection<User> teamA, Collection<User> teamB, Match match) {
        initializeUsersNotInLeaderboard(teamA, teamB, match);
        Collection<LeaderboardUser> teamALeaderboard = getLeaderboardUsers(match.gameMode, teamA);
        Collection<LeaderboardUser> teamBLeaderboard = getLeaderboardUsers(match.gameMode, teamB);

        MatchExpectedResult expectedResult = calculateExpectedResults(teamALeaderboard, teamBLeaderboard, match.gameMode);

        adjustPlayerRatings(match.outcome, teamALeaderboard, teamBLeaderboard, expectedResult);
    }

    public MatchExpectedResult calculateExpectedResults(Collection<LeaderboardUser> teamA, Collection<LeaderboardUser> teamB, GameMode gameMode) {
        int teamAScore = getTeamScore(gameMode, teamA);
        int teamBScore = getTeamScore(gameMode, teamB);

        Function<Integer, Integer> calculateFactor = score -> (int) Math.pow(10, (double) score / 400);
        int teamAFactor = calculateFactor.apply(teamAScore);
        int teamBFactor = calculateFactor.apply(teamBScore);

        BiFunction<Integer, Integer, Integer> calculateExpectedResult = (factor, opponentFactor) ->
                factor / (factor + opponentFactor);
        int teamAExpectedResult = calculateExpectedResult.apply(teamAFactor, teamBFactor);
        int teamBExpectedResult = calculateExpectedResult.apply(teamBFactor, teamAFactor);

        return new MatchExpectedResult(teamAExpectedResult, teamBExpectedResult);
    }

    public void initializeUser(GameMode gameMode, User user) {
        LeaderboardUser leaderboardUser = new LeaderboardUser();
        leaderboardUser.gameMode = gameMode;
        leaderboardUser.user = user;
        leaderboardUser.score = 500; // Allow admins to set initialScore and other initial settings on gamemode
        leaderboardUser.growthFactor = 40;
        leaderboardUser.persist();
    }

    private Integer getTeamScore(GameMode gameMode, Collection<LeaderboardUser> team) {
        Integer teamTotal = team.stream()
                .map(lu -> lu.score)
                .reduce(0, Integer::sum);
        int teamAverage = teamTotal / team.size();

        return teamAverage;
    }

    private void initializeUsersNotInLeaderboard(Collection<User> teamA, Collection<User> teamB, Match match) {
        GameMode gameMode = GameMode.findByIdWithLeaderboard(match.gameMode.id);
        Set<User> usersInLeaderboard = gameMode.leaderboard.stream().map(lu -> lu.user).collect(Collectors.toSet());

        Consumer<User> initializeIfNotInLeaderboard = user -> {
            if(!usersInLeaderboard.contains(user)) initializeUser(gameMode, user);
        };

        teamA.forEach(initializeIfNotInLeaderboard);
        teamB.forEach(initializeIfNotInLeaderboard);
        Panache.getEntityManager().flush();
    }

    private List<LeaderboardUser> getLeaderboardUsers(GameMode gameMode, Collection<User> users) {
        Set<Long> userIds = users.stream().map(user -> user.id).collect(Collectors.toSet());
        List<LeaderboardUser> team = LeaderboardUser.findByUsersAndGamemode(userIds, gameMode.id);
        return team;
    }

    private void adjustPlayerRatings(Match.Outcome outcome, Collection<LeaderboardUser> teamALeaderboard, Collection<LeaderboardUser> teamBLeaderboard, MatchExpectedResult expectedResult) {
        Function<MatchUser.Team, Double> outcomeFactor = team -> switch (outcome) {
            case TEAM_A -> team == MatchUser.Team.TEAM_A ? 1d : 0;
            case TEAM_B -> team == MatchUser.Team.TEAM_B ? 1d : 0;
            case DRAW -> 0.5;
            default -> throw new IllegalStateException();
        };

        double teamAGrowthFactor = outcomeFactor.apply(MatchUser.Team.TEAM_A) - expectedResult.teamAExpectedResult();
        double teamBGrowthFactor = outcomeFactor.apply(MatchUser.Team.TEAM_B) - expectedResult.teamBExpectedResult();

        BiConsumer<LeaderboardUser, Double> adjustUserRating = (leaderboardUser, growthFactor) -> {
            leaderboardUser.score += (int) (leaderboardUser.growthFactor * growthFactor);
            if(leaderboardUser.growthFactor > 20)
                leaderboardUser.growthFactor--;
        };
        teamALeaderboard.forEach(player -> adjustUserRating.accept(player, teamAGrowthFactor));
        teamBLeaderboard.forEach(player -> adjustUserRating.accept(player, teamBGrowthFactor));
    }
}
