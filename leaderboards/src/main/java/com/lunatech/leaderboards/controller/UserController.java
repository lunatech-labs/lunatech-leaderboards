package com.lunatech.leaderboards.controller;

import com.lunatech.leaderboards.dto.user.UserDto;
import com.lunatech.leaderboards.dto.user.UserPostDto;
import com.lunatech.leaderboards.entity.User;
import io.quarkus.oidc.runtime.OidcJwtCallerPrincipal;
import io.quarkus.security.Authenticated;
import io.quarkus.security.identity.SecurityIdentity;

import javax.inject.Inject;
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

    @Inject
    SecurityIdentity securityIdentity;

    @GET
    @Path("/me")
    public Response me() {
        String email = securityEmail();
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

    @POST
    @Transactional
    public Response add(UserPostDto body) {
        String email = securityEmail();
        User user = body.toEntity(email);
        user.persist();
        return Response.created(URI.create("/users/" + user.id))
                .entity(new UserDto(user))
                .build();
    }

    private String securityEmail() {
        OidcJwtCallerPrincipal oidcPrincipal = (OidcJwtCallerPrincipal) securityIdentity.getPrincipal();
        return oidcPrincipal.getClaim("email");
    }
}
