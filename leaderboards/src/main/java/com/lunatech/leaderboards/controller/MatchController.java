package com.lunatech.leaderboards.controller;

import com.lunatech.leaderboards.dto.match.MatchDto;
import com.lunatech.leaderboards.dto.match.MatchPostDto;
import com.lunatech.leaderboards.entity.GameMode;
import com.lunatech.leaderboards.entity.Match;

import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

@Path("/games/{gameId}/gamemodes/{gameModeId}/matches")
public class MatchController {
    @PathParam("gameId")
    private Long gameId;

    @PathParam("gameModeId")
    private Long gameModeId;

    @GET
    public Response list() {
        GameMode gameMode = GameMode.findByIdWithMatches(gameModeId);
        List<MatchDto> matches = gameMode.matches.stream().map(MatchDto::new).toList();
        return Response.ok(matches).build();
    }

    @POST
    @Transactional
    public Response add(MatchPostDto body) {
        Match match = body.toEntity();
        match.persist();
        return Response.created(URI.create("/games/"+gameId+"/gamemodes/"+gameModeId+"/matches/"+match.id))
                .entity(new MatchDto(match))
                .build();
    }
}
