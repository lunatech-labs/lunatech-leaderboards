package com.lunatech.leaderboards.controller;

import com.lunatech.leaderboards.dto.match.MatchDto;
import com.lunatech.leaderboards.dto.match.MatchPostDto;
import com.lunatech.leaderboards.entity.GameMode;
import com.lunatech.leaderboards.entity.Match;
import com.lunatech.leaderboards.entity.User;
import com.lunatech.leaderboards.service.MatchService;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.Collection;
import java.util.List;

@Path("/games/{gameId}/gamemodes/{gameModeId}/matches")
public class MatchController {

    @Inject
    private MatchService matchService;

    @PathParam("gameId")
    private Long gameId;

    @PathParam("gameModeId")
    private Long gameModeId;

    @GET
    public Response list() {
        Collection<Match> matches = matchService.findByGameMode(gameModeId);
        List<MatchDto> matchesDto = matches.stream().map(MatchDto::new).toList();
        return Response.ok(matchesDto).build();
    }

    @POST
    @Transactional
    public Response add(MatchPostDto body) {
        Match match = body.toEntity();
        matchService.save(match);

        return Response.created(URI.create("/games/"+gameId+"/gamemodes/"+gameModeId+"/matches/"+match.id))
                .entity(new MatchDto(match))
                .build();
    }

    @POST
    @Path("/{matchId}/accept")
    @Transactional
    public Response accept(Long matchId, @QueryParam("user") Long userId) {
        matchService.accept(matchId, userId);

        return Response.ok().build();
    }
}
