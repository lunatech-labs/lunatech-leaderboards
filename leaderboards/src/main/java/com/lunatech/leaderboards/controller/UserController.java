package com.lunatech.leaderboards.controller;

import com.lunatech.leaderboards.dto.user.UserDto;
import com.lunatech.leaderboards.dto.user.UserPostDto;
import com.lunatech.leaderboards.entity.User;
import io.quarkus.security.Authenticated;

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

    @HeaderParam("email")
    private String email;

    @GET
    @Path("/me")
    public Response me() {
        return User.<User>find("email", email).singleResultOptional()
                .map(user -> Response.ok(new UserDto(user)).build())
                .orElseThrow(() -> new NotFoundException("User with email " + email + " not in leaderboards"));
    }

    @GET
    public Response list() {
        List<User> users = User.listAll();
        List<UserDto> dtos = users.stream().map(UserDto::new).toList();
        return Response.ok(dtos).build();
    }

    @GET
    @Path("/{userId}")
    public Response get(Long userId) {
        User user = User.findById(userId);
        UserDto dto = new UserDto(user);
        return Response.ok(dto).build();
    }

    @PUT
    @Transactional
    @Authenticated
    //To fix, should retrieve user
    public Response add(UserPostDto body) {
        User user = body.toEntity(email);
        user.persist();
        return Response.created(URI.create("/users/" + user.id))
                .entity(new UserDto(user))
                .build();
    }
}
