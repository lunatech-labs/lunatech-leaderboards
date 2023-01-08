package com.lunatech.leaderboards.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;

@Entity
@Table(name = "app_user")
public class User extends PanacheEntityBase {

    @Id
    @SequenceGenerator(name="app_user_id_seq", sequenceName="app_user_id_seq", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="app_user_id_seq")
    @Column(name = "id", updatable=false)
    public Long id;

    public String email;

    @Column(name = "displayname")
    public String displayName;

    @Column(name = "profilepicurl")
    public String profilePicUrl;
}
