package com.lunatech.leaderboard.controller;

import com.lunatech.leaderboard.dto.gamemode.GameModeDto;
import com.lunatech.leaderboard.dto.gamemode.GameModeDetailDto;
import com.lunatech.leaderboard.dto.gamemode.GameModePostDto;
import com.lunatech.leaderboard.entity.Game;
import com.lunatech.leaderboard.entity.GameMode;
import com.lunatech.leaderboard.mapper.gamemode.GameModeDtoMapper;
import com.lunatech.leaderboard.service.GameModeService;
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
import java.util.List;
import java.util.Objects;

@Path("/games/{gameId}/gamemodes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Authenticated
public class GameModeController {

    @Inject
    GameModeDtoMapper gameModeDtoMapper;

    @Inject
    GameModeService gameModeService;

    @PathParam("gameId")
    private Long gameId;

    @GET
    @APIResponse(content = @Content(schema = @Schema(
            type = SchemaType.ARRAY,
            implementation = GameModeDto.class
    )))
    public Response list() {
        List<GameMode> gameModes = gameModeService.findAllByGame(gameId);
        List<GameModeDto> response = gameModes.stream().map(GameModeDto::new).toList();

        return Response.ok(response).build();
    }

    @GET
    @Path("/{gameModeId}")
    @APIResponseSchema(GameModeDto.class)
    public Response leaderboard(Long gameModeId) {
        GameMode gameMode = GameMode.findByIdWithLeaderboard(gameModeId);
        return Response.ok(new GameModeDetailDto(gameMode)).build();
    }

    @POST
    @Transactional
    @APIResponseSchema(GameModeDto.class)
    @RolesAllowed("admin")
    public Response add(@Valid GameModePostDto body) {
        gameModeDtoMapper.setGameId(gameId);
        GameMode gameMode = gameModeDtoMapper.toEntity(body);

        gameModeService.add(gameMode);

        return Response.created(URI.create("/games/"+gameId+"/gamemodes/"+gameMode.id))
                .entity(new GameModeDto(gameMode))
                .build();
    }

    @DELETE
    @Path("/{gameModeId}")
    @Transactional
    @APIResponseSchema(GameModeDto.class)
    public Response delete(Long gameModeId) {
        GameMode gameMode = GameMode.findById(gameModeId);
        if(!Objects.equals(gameMode.game.id, gameId))
            throw new BadRequestException("GameMode game does not match path");

        gameModeService.delete(gameMode);

        return Response.ok(new GameModeDto(gameMode)).build();
    }
}
