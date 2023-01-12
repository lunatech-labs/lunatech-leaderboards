package com.lunatech.leaderboard.service;

import com.lunatech.leaderboard.client.graphql.lunagraph.LunagraphApi;
import com.lunatech.leaderboard.client.graphql.lunagraph.model.LunagraphEmployeeType;
import com.lunatech.leaderboard.client.graphql.lunagraph.model.LunagraphPeopleModel;
import com.lunatech.leaderboard.entity.User;
import io.quarkus.runtime.StartupEvent;
import lombok.extern.java.Log;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.util.Set;
import java.util.stream.Collectors;

@ApplicationScoped
@Log
public class UserService {

    @Inject
    LunagraphApi lunagraphApi;

    public void startUp(@Observes StartupEvent event) {
        importAnniaUsers();
    }

    public void importAnniaUsers() {
        try {
            Set<String> usersEmails = User.<User>streamAll().map(user -> user.email).collect(Collectors.toSet());

            LunagraphPeopleModel peopleModel = lunagraphApi.people(LunagraphEmployeeType.INTERNAL);
            peopleModel.people().stream()
                    .filter(person -> !usersEmails.contains(person.emailAddress()))
                    .forEach(person -> add(person.emailAddress(), person.fullName()));
        } catch (Exception e) {
            log.severe(e.getMessage());
        }
    }

    public User add(String emailAddress, String fullName) {
        User user = new User();
        user.email = emailAddress;
        user.displayName = fullName;
        user.persist();
        return user;
    }
}
