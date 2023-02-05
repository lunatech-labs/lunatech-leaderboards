package com.lunatech.leaderboards.service;

import com.lunatech.leaderboards.model.leaderboarduser.MatchExpectedResult;
import com.lunatech.leaderboards.entity.*;
import io.quarkus.hibernate.orm.panache.Panache;

import javax.inject.Singleton;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

@Singleton
public class LeaderboardService {

    public void evaluateMatch(Collection<User> teamA, Collection<User> teamB, Match match) {
        initializeUsersNotInLeaderboard(teamA, teamB, match);
        Collection<LeaderboardUser> teamALeaderboard = getLeaderboardUsers(match.gameMode, teamA);
        Collection<LeaderboardUser> teamBLeaderboard = getLeaderboardUsers(match.gameMode, teamB);

        MatchExpectedResult expectedResult = calculateExpectedResults(teamALeaderboard, teamBLeaderboard);

        adjustPlayerRatings(match.outcome, teamALeaderboard, teamBLeaderboard, expectedResult);
    }

    public MatchExpectedResult calculateExpectedResults(Collection<LeaderboardUser> teamA, Collection<LeaderboardUser> teamB) {
        int teamAScore = getTeamScore(teamA);
        int teamBScore = getTeamScore(teamB);

        Function<Integer, Double> calculateFactor = score -> Math.pow(10, score / 400d);
        double teamAFactor = calculateFactor.apply(teamAScore);
        double teamBFactor = calculateFactor.apply(teamBScore);

        BiFunction<Double, Double, Double> calculateExpectedResult = (factor, opponentFactor) ->
                factor / (factor + opponentFactor);
        double teamAExpectedResult = calculateExpectedResult.apply(teamAFactor, teamBFactor);
        double teamBExpectedResult = calculateExpectedResult.apply(teamBFactor, teamAFactor);

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

    private Integer getTeamScore(Collection<LeaderboardUser> team) {
        Integer teamTotal = team.stream()
                .map(lu -> lu.score)
                .reduce(0, Integer::sum);

        return teamTotal / team.size();
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
