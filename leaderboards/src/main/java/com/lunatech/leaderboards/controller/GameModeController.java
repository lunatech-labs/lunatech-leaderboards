package com.lunatech.leaderboards.controller;

import com.lunatech.leaderboards.dto.GameDto;
import com.lunatech.leaderboards.dto.GameModeDto;
import com.lunatech.leaderboards.entity.Game;
import com.lunatech.leaderboards.entity.GameMode;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;
import java.util.Objects;

@Path("/games/{gameId}/gamemodes")
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

    @POST
    @Transactional
    public Response add(@Valid GameModeDto body) {
        GameMode gameMode = body.toEntity();
        if(!Objects.equals(gameMode.game.id, gameId))
            throw new BadRequestException("GameMode game does not match path");

        gameMode.persist();

        return Response.created(URI.create("/games/"+gameId+"/gamemodes/"+gameMode.id))
                .entity(new GameModeDto(gameMode))
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

        return Response.ok(new GameModeDto(gameMode)).build();
    }
}
