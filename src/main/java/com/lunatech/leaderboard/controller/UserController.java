package com.lunatech.leaderboard.controller;

import com.lunatech.leaderboard.dto.user.UserDto;
import com.lunatech.leaderboard.dto.user.UserPostDto;
import com.lunatech.leaderboard.entity.User;
import com.lunatech.leaderboard.mapper.user.UserDtoMapper;
import io.quarkus.security.Authenticated;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponseSchema;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.Collection;
import java.util.List;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Authenticated
public class UserController {

    @Inject
    UserDtoMapper userDtoMapper;

    @Parameter(hidden = true)
    @HeaderParam("email")
    private String email;

    @GET
    @Path("/me")
    @APIResponseSchema(UserDto.class)
    public Response me() {
        return User.<User>find("email", email).singleResultOptional()
                .map(user -> Response.ok(userDtoMapper.toDto(user)).build())
                .orElseThrow(() -> new NotFoundException("User with email " + email + " not in leaderboards"));
    }

    @GET
    @APIResponse(content = @Content(schema = @Schema(
            type = SchemaType.ARRAY,
            implementation = UserDto.class
    )))
    public Response list() {
        List<User> users = User.listAll();
        Collection<UserDto> dtos = userDtoMapper.toDtos(users);
        return Response.ok(dtos).build();
    }

    @GET
    @Path("/{userId}")
    @APIResponseSchema(UserDto.class)
    public Response get(Long userId) {
        User user = User.findById(userId);
        UserDto dto = userDtoMapper.toDto(user);
        return Response.ok(dto).build();
    }

    @POST
    @Transactional
    @RolesAllowed("admin")
    @APIResponseSchema(UserDto.class)
    public Response add(UserPostDto body) {
        User user = userDtoMapper.toEntity(body);
        user.persist();
        return Response.created(URI.create("/users/" + user.id))
                .entity(userDtoMapper.toDto(user))
                .build();
    }
}
