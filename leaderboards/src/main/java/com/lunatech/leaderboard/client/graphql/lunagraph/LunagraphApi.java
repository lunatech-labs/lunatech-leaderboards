package com.lunatech.leaderboard.client.graphql.lunagraph;

import com.lunatech.leaderboard.client.graphql.lunagraph.model.LunagraphEmployeeType;
import com.lunatech.leaderboard.client.graphql.lunagraph.model.LunagraphPeopleModel;
import org.eclipse.microprofile.graphql.Name;
import org.eclipse.microprofile.graphql.Query;

public interface LunagraphApi {

    @Query("people")
    LunagraphPeopleModel people(@Name("employeeType") LunagraphEmployeeType employeeType);
}
