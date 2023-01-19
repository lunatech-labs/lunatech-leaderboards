package com.lunatech.leaderboard.client.graphql.lunagraph.model;

import org.eclipse.microprofile.graphql.Enum;

@Enum("EmployeeType")
public enum LunagraphEmployeeType {
    EXTERNAL("external"),
    INTERNAL("internal"),
    INTERN("intern"),
    INVALID("invalid");

    private final String lunagraphValue;

    LunagraphEmployeeType(String lunagraphValue) {
        this.lunagraphValue = lunagraphValue;
    }

    public String getLunagraphValue() {
        return lunagraphValue;
    }

    @Override
    public String toString() {
        return lunagraphValue;
    }
}
