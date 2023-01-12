package com.lunatech.leaderboard.client.graphql.lunagraph.model;


import java.util.List;

public record LunagraphPeopleModel(List<Person> people) {

    public record Person(String fullName, String emailAddress) {}
}
