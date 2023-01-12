package com.lunatech.leaderboard.controller;

import com.lunatech.leaderboard.dto.match.MatchDto;
import com.lunatech.leaderboard.dto.match.MatchPostDto;
import com.lunatech.leaderboard.entity.Match;
import com.lunatech.leaderboard.entity.User;
import com.lunatech.leaderboard.service.MatchService;
import io.quarkus.security.Authenticated;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponseSchema;

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
    MatchService matchService;

    @PathParam("gameId")
    private Long gameId;

    @PathParam("gameModeId")
    private Long gameModeId;

    @GET
    @APIResponse(content = @Content(schema = @Schema(
            type = SchemaType.ARRAY,
            implementation = MatchDto.class
    )))
    public Response list() {
        Collection<Match> matches = matchService.findByGameMode(gameModeId);
        List<MatchDto> matchesDto = matches.stream().map(MatchDto::new).toList();
        return Response.ok(matchesDto).build();
    }

    @POST
    @Transactional
    @APIResponseSchema(MatchDto.class)
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
    @APIResponseSchema(MatchDto.class)
    public Response accept(Long matchId, @Parameter(hidden = true) @HeaderParam("email") String email) {
        User user = User.find("email", email).singleResult();
        matchService.accept(matchId, user.id);

        return Response.ok().build();
    }
}
