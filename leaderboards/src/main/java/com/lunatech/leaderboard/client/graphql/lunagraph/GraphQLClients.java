package com.lunatech.leaderboard.client.graphql.lunagraph;

import io.quarkus.oidc.client.Tokens;
import io.smallrye.graphql.client.typesafe.api.TypesafeGraphQLClientBuilder;

import javax.inject.Singleton;

public class GraphQLClients {

    @Singleton
    public LunagraphApi lunagraphApi(Tokens tokens) {
        return TypesafeGraphQLClientBuilder.newBuilder()
                .header("Authorization", "Bearer " + tokens.getAccessToken())
                .configKey("lunagraph")
                .build(LunagraphApi.class);
    }
}
