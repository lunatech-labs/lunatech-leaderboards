package com.lunatech.leaderboard.client.graphql.lunagraph;

import io.smallrye.graphql.client.typesafe.api.TypesafeGraphQLClientBuilder;
import org.eclipse.microprofile.jwt.JsonWebToken;

import javax.enterprise.context.RequestScoped;

public class GraphQLClients {

    @RequestScoped
    public LunagraphApi lunagraphApi(JsonWebToken jwt) {
        return TypesafeGraphQLClientBuilder.newBuilder()
                .header("Authorization", "Bearer " + jwt.getRawToken())
                .configKey("lunagraph")
                .build(LunagraphApi.class);
    }
}
