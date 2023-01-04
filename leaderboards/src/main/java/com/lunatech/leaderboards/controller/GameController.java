package com.lunatech.leaderboards.controller;

import com.lunatech.leaderboards.dto.GameDto;
import com.lunatech.leaderboards.entity.Game;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.extern.java.Log;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Log
@Path("/games")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GameController {

    @GET
    public Response list() {
        List<Game> games = Game.findAll().list();
        List<GameDto> dtos = games.stream().map(GameDto::new).toList();
        return Response.ok(dtos).build();
    }

    @POST
    public Response add(GameDto body) {
        Game game = body.toEntity();
        game.persist();
        return Response.ok(game).build();
    }
}
