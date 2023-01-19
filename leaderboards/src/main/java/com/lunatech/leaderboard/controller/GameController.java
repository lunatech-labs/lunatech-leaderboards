package com.lunatech.leaderboard.controller;

import com.lunatech.leaderboard.dto.game.GameDto;
import com.lunatech.leaderboard.entity.Game;
import com.lunatech.leaderboard.mapper.game.GameDtoMapper;
import com.lunatech.leaderboard.service.GameService;
import io.quarkus.security.Authenticated;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponseSchema;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.Collection;
import java.util.List;

@Path("/games")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Authenticated
public class GameController {

    @Inject
    GameDtoMapper gameDtoMapper;

    @Inject
    GameService gameService;

    @GET
    @APIResponse(content = @Content(schema = @Schema(
            type = SchemaType.ARRAY,
            implementation = GameDto.class
    )))
    public Response list() {
        Collection<Game> games = gameService.findAll();
        List<GameDto> dtos = games.stream().map(GameDto::new).toList();
        return Response.ok(dtos).build();
    }

    @POST
    @Transactional
    @APIResponseSchema(GameDto.class)
    @RolesAllowed("admin")
    public Response add(@Valid GameDto body) {
        Game game = gameDtoMapper.toEntity(body);
        gameService.add(game);
        return Response.created(URI.create("/games/"+game.id))
                .entity(new GameDto(game))
                .build();
    }

    @DELETE
    @Path("/{gameId}")
    @Transactional
    @APIResponseSchema(GameDto.class)
    public Response delete(Long gameId) {
        Game game = gameService.delete(gameId);
        return Response.ok(game).build();
    }
}
