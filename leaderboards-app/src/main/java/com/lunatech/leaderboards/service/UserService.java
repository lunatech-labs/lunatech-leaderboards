package com.lunatech.leaderboards.service;

import com.lunatech.leaderboards.client.graphql.lunagraph.LunagraphApi;
import com.lunatech.leaderboards.client.graphql.lunagraph.model.LunagraphPersonModel;
import com.lunatech.leaderboards.entity.User;
import io.smallrye.graphql.client.InvalidResponseException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.InternalServerErrorException;

@ApplicationScoped
public class UserService {

    @Inject
    LunagraphApi lunagraphApi;

    public User add(String email) {
        try {
            LunagraphPersonModel person = lunagraphApi.person(email);
            if(person == null)
                throw new BadRequestException("No user found for email " + email);

            User user = new User();
            user.email = email;
            user.displayName = person.fullName();
            user.persist();

            return user;
        } catch (InvalidResponseException e) {
            throw new InternalServerErrorException("Something happened while trying to communicate with Lunagraph " + e.getMessage());
        }
    }
}
