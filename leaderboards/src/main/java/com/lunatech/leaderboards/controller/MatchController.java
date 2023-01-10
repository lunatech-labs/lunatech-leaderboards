package com.lunatech.leaderboards.controller;

import com.lunatech.leaderboards.dto.match.MatchDto;
import com.lunatech.leaderboards.dto.match.MatchPostDto;
import com.lunatech.leaderboards.entity.Match;
import com.lunatech.leaderboards.entity.User;
import com.lunatech.leaderboards.service.MatchService;
import io.quarkus.oidc.runtime.OidcJwtCallerPrincipal;
import io.quarkus.security.Authenticated;
import io.quarkus.security.identity.SecurityIdentity;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.Collection;
import java.util.List;

@Path("/games/{gameId}/gamemodes/{gameModeId}/matches")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Authenticated
public class MatchController {

    @Inject
    SecurityIdentity securityIdentity;

    @Inject
    MatchService matchService;

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
    public Response accept(Long matchId) {
        User user = User.find("email", securityEmail()).singleResult();
        matchService.accept(matchId, user.id);

        return Response.ok().build();
    }

    private String securityEmail() {
        OidcJwtCallerPrincipal oidcPrincipal = (OidcJwtCallerPrincipal) securityIdentity.getPrincipal();
        return oidcPrincipal.getClaim("email");
    }
}
