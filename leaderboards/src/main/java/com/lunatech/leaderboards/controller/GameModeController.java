package com.lunatech.leaderboards.controller;

import com.lunatech.leaderboards.dto.gamemode.GameModeDto;
import com.lunatech.leaderboards.dto.gamemode.GameModeLeaderboardDto;
import com.lunatech.leaderboards.dto.gamemode.GameModePostDto;
import com.lunatech.leaderboards.entity.Game;
import com.lunatech.leaderboards.entity.GameMode;
import io.quarkus.security.Authenticated;

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
    public Response list() {
        Game game = Game.findById(gameId);
        List<GameMode> gameModes = game.gameModes;
        List<GameModeDto> response = gameModes.stream().map(GameModeDto::new).toList();

        return Response.ok(response).build();
    }

    @GET
    @Path("/{gameModeId}")
    public Response leaderboard(Long gameModeId) {
        GameMode gameMode = GameMode.findByIdWithLeaderboard(gameModeId);
        return Response.ok(new GameModeLeaderboardDto(gameMode)).build();
    }

    @POST
    @Transactional
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
    public Response delete(@Valid Long gameModeId) {
        GameMode gameMode = GameMode.findById(gameModeId);
        if(!Objects.equals(gameMode.game.id, gameId))
            throw new BadRequestException("GameMode game does not match path");

        gameMode.delete();

        return Response.ok(new GameModePostDto(gameMode)).build();
    }
}
