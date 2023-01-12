package com.lunatech.leaderboard.client.graphql.lunagraph;

import com.lunatech.leaderboard.client.graphql.lunagraph.model.LunagraphPersonModel;
import org.eclipse.microprofile.graphql.Name;
import org.eclipse.microprofile.graphql.Query;

public interface LunagraphApi {

    @Query("person")
    LunagraphPersonModel person(@Name("emailAddress") String email);
}
