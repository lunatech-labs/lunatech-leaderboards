package com.lunatech.leaderboards.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "app_user")
public class User extends PanacheEntity {
    public String email;

    @Column(name = "displayname")
    public String displayName;

    @Column(name = "profilepicurl")
    public String profilepicurl;
}
