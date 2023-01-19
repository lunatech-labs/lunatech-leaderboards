package com.lunatech.leaderboard.client.graphql.lunagraph;

import com.lunatech.leaderboard.client.graphql.lunagraph.model.LunagraphPersonModel;
import io.quarkus.arc.DefaultBean;
import io.quarkus.arc.profile.IfBuildProfile;
import io.smallrye.graphql.client.typesafe.api.TypesafeGraphQLClientBuilder;
import org.eclipse.microprofile.jwt.JsonWebToken;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;

@RequestScoped
public class GraphQLClients {

    @Produces
    @IfBuildProfile("prod")
    public LunagraphApi lunagraphApi(JsonWebToken jwt) {
        return TypesafeGraphQLClientBuilder.newBuilder()
                .header("Authorization", "Bearer " + jwt.getRawToken())
                .configKey("lunagraph")
                .build(LunagraphApi.class);
    }

    @RequestScoped
    @DefaultBean
    public LunagraphApi mockLunagraphApi() {
        return email -> new LunagraphPersonModel("Mock user: " + email, email);
    }
}
