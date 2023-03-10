package com.lunatech.leaderboards.controller;

import com.lunatech.leaderboards.dto.match.MatchDto;
import com.lunatech.leaderboards.dto.match.MatchPostDto;
import com.lunatech.leaderboards.entity.Match;
import com.lunatech.leaderboards.entity.User;
import com.lunatech.leaderboards.mapper.match.MatchDtoMapper;
import com.lunatech.leaderboards.service.MatchService;
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

@Path("/games/{gameId}/gamemodes/{gameModeId}/matches")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Authenticated
public class MatchController {

    @Inject
    MatchService matchService;

    @Inject
    MatchDtoMapper matchDtoMapper;

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
        Collection<MatchDto> matchesDto = matchDtoMapper.toDtos(matches);
        return Response.ok(matchesDto).build();
    }

    @POST
    @Transactional
    @APIResponseSchema(MatchDto.class)
    public Response add(MatchPostDto body) {
        matchDtoMapper.setGameModeId(gameModeId);
        Match match = matchDtoMapper.toEntity(body);

        matchService.save(match);

        return Response.created(URI.create("/games/"+gameId+"/gamemodes/"+gameModeId+"/matches/"+match.id))
                .entity(matchDtoMapper.toDto(match))
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
