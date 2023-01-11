package com.lunatech.leaderboards.controller;

import com.lunatech.leaderboards.dto.game.GameDto;
import com.lunatech.leaderboards.entity.Game;
import io.quarkus.security.Authenticated;

import javax.annotation.security.RolesAllowed;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

@Path("/games")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed("user")
public class GameController {

    @GET
    public Response list() {
        List<Game> games = Game.findAll().list();
        List<GameDto> dtos = games.stream().map(GameDto::new).toList();
        return Response.ok(dtos).build();
    }

    @POST
    @Transactional
    public Response add(@Valid GameDto body) {
        Game game = body.toEntity();
        game.persist();
        return Response.created(URI.create("/games/"+game.id))
                .entity(new GameDto(game))
                .build();
    }

    @DELETE
    @Path("/{gameId}")
    @Transactional
    public Response delete(Long gameId) {
        Game game = Game.findById(gameId);
        game.delete();
        return Response.ok(game).build();
    }
}
