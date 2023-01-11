package com.lunatech.leaderboards.controller;

import com.lunatech.leaderboards.dto.gamemode.GameModeDto;
import com.lunatech.leaderboards.dto.gamemode.GameModeLeaderboardDto;
import com.lunatech.leaderboards.dto.gamemode.GameModePostDto;
import com.lunatech.leaderboards.entity.Game;
import com.lunatech.leaderboards.entity.GameMode;
import io.quarkus.security.Authenticated;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponseSchema;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;
import java.util.Objects;

@Path("/games/{gameId}/gamemodes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Authenticated
public class GameModeController {

    @PathParam("gameId")
    private Long gameId;

    @GET
    @APIResponse(content = @Content(schema = @Schema(
            type = SchemaType.ARRAY,
            implementation = GameModeDto.class
    )))
    public Response list() {
        Game game = Game.findById(gameId);
        List<GameMode> gameModes = game.gameModes;
        List<GameModeDto> response = gameModes.stream().map(GameModeDto::new).toList();

        return Response.ok(response).build();
    }

    @GET
    @Path("/{gameModeId}")
    @APIResponseSchema(GameModeDto.class)
    public Response leaderboard(Long gameModeId) {
        GameMode gameMode = GameMode.findByIdWithLeaderboard(gameModeId);
        return Response.ok(new GameModeLeaderboardDto(gameMode)).build();
    }

    @POST
    @Transactional
    @APIResponseSchema(GameModeDto.class)
    public Response add(@Valid GameModePostDto body) {
        GameMode gameMode = body.toEntity();
        if(!Objects.equals(gameMode.game.id, gameId))
            throw new BadRequestException("GameMode game does not match path");

        gameMode.persist();

        return Response.created(URI.create("/games/"+gameId+"/gamemodes/"+gameMode.id))
                .entity(new GameModePostDto(gameMode))
                .build();
    }

    @DELETE
    @Path("/{gameModeId}")
    @Transactional
    @APIResponseSchema(GameModeDto.class)
    public Response delete(@Valid Long gameModeId) {
        GameMode gameMode = GameMode.findById(gameModeId);
        if(!Objects.equals(gameMode.game.id, gameId))
            throw new BadRequestException("GameMode game does not match path");

        gameMode.delete();

        return Response.ok(new GameModePostDto(gameMode)).build();
    }
}
