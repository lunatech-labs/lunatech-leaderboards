package com.lunatech.leaderboard.controller;

import com.lunatech.leaderboard.dto.user.UserDto;
import com.lunatech.leaderboard.dto.user.UserPostDto;
import com.lunatech.leaderboard.entity.User;
import io.quarkus.security.Authenticated;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponseSchema;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Authenticated
public class UserController {

    @Parameter(hidden = true)
    @HeaderParam("email")
    private String email;

    @GET
    @Path("/me")
    @APIResponseSchema(UserDto.class)
    public Response me() {
        return User.<User>find("email", email).singleResultOptional()
                .map(user -> Response.ok(new UserDto(user)).build())
                .orElseThrow(() -> new NotFoundException("User with email " + email + " not in leaderboards"));
    }

    @GET
    @APIResponse(content = @Content(schema = @Schema(
            type = SchemaType.ARRAY,
            implementation = UserDto.class
    )))
    public Response list() {
        List<User> users = User.listAll();
        List<UserDto> dtos = users.stream().map(UserDto::new).toList();
        return Response.ok(dtos).build();
    }

    @GET
    @Path("/{userId}")
    @APIResponseSchema(UserDto.class)
    public Response get(Long userId) {
        User user = User.findById(userId);
        UserDto dto = new UserDto(user);
        return Response.ok(dto).build();
    }

    @PUT
    @Transactional
    @Authenticated
    @APIResponseSchema(UserDto.class)
    //To fix, should retrieve user
    public Response add(UserPostDto body) {
        User user = body.toEntity(email);
        user.persist();
        return Response.created(URI.create("/users/" + user.id))
                .entity(new UserDto(user))
                .build();
    }
}
